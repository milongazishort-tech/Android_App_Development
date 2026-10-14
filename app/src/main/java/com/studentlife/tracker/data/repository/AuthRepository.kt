package com.studentlife.tracker.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.studentlife.tracker.data.model.Result
import com.studentlife.tracker.data.model.UserProfile
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

/**
 * Repository handling Firebase Authentication and user profile operations.
 */
class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    /**
     * Register a new user and create their profile in Firestore.
     */
    suspend fun register(
        name: String,
        email: String,
        password: String,
        university: String,
        department: String
    ): Result<UserProfile> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.Error("Registration failed — user not created.")

            val startDate = Date()
            val calendar = Calendar.getInstance().apply {
                time = startDate
                add(Calendar.DAY_OF_YEAR, 29) // 30 days inclusive
            }
            val endDate = calendar.time

            val profile = UserProfile(
                userId = user.uid,
                name = name,
                email = email,
                university = university,
                department = department,
                startDate = startDate,
                endDate = endDate
            )

            firestore.collection("users")
                .document(user.uid)
                .set(profile)
                .await()

            Result.Success(profile)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Registration failed.", e)
        }
    }

    /**
     * Sign in with email and password.
     */
    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.Error("Login failed.")
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Login failed.", e)
        }
    }

    /**
     * Send password reset email.
     */
    suspend fun sendPasswordReset(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to send reset email.", e)
        }
    }

    /**
     * Sign out current user.
     */
    fun logout() {
        auth.signOut()
    }

    /**
     * Fetch the current user's profile from Firestore.
     */
    suspend fun getUserProfile(): Result<UserProfile> {
        val uid = currentUser?.uid ?: return Result.Error("User not authenticated.")
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            val profile = doc.toObject(UserProfile::class.java)
                ?: return Result.Error("Profile not found.")
            Result.Success(profile)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to load profile.", e)
        }
    }

    /**
     * Update user profile fields.
     */
    suspend fun updateProfile(
        name: String,
        university: String,
        department: String
    ): Result<Unit> {
        val uid = currentUser?.uid ?: return Result.Error("User not authenticated.")
        return try {
            firestore.collection("users").document(uid)
                .update(
                    mapOf(
                        "name" to name,
                        "university" to university,
                        "department" to department
                    )
                ).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to update profile.", e)
        }
    }

    /**
     * Update notification settings in Firestore.
     */
    suspend fun updateSettings(
        notificationsEnabled: Boolean,
        targetWakeTime: String,
        targetSleepTime: String,
        reminderHour: Int,
        reminderMinute: Int
    ): Result<Unit> {
        val uid = currentUser?.uid ?: return Result.Error("User not authenticated.")
        return try {
            firestore.collection("users").document(uid)
                .update(
                    mapOf(
                        "notificationsEnabled" to notificationsEnabled,
                        "targetWakeTime" to targetWakeTime,
                        "targetSleepTime" to targetSleepTime,
                        "reminderHour" to reminderHour,
                        "reminderMinute" to reminderMinute
                    )
                ).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to update settings.", e)
        }
    }

    /**
     * Change password for current user.
     */
    suspend fun changePassword(newPassword: String): Result<Unit> {
        val user = currentUser ?: return Result.Error("User not authenticated.")
        return try {
            user.updatePassword(newPassword).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Failed to change password.", e)
        }
    }
}
