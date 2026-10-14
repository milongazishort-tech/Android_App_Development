package com.studentlife.tracker.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.studentlife.tracker.R
import com.studentlife.tracker.databinding.ActivityMainBinding
import com.studentlife.tracker.ui.task.AddEditTaskActivity

/**
 * Main application host activity with a 5-dock bottom navigation and center elevated FAB.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        // Center Elevated FAB (+) opens Add Custom Task screen
        binding.fabAddTask.setOnClickListener {
            startActivity(Intent(this, AddEditTaskActivity::class.java))
        }

        // Avoid selecting the disabled placeholder item
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.placeholderFab -> false
                R.id.homeFragment, R.id.calendarFragment,
                R.id.progressFragment, R.id.profileFragment -> {
                    navController.navigate(item.itemId)
                    true
                }
                else -> false
            }
        }
    }
}
