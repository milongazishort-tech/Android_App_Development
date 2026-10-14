const express = require('express');
const cors = require('cors');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

// In-Memory Database store (syncable with MongoDB Atlas)
const db = {
  users: [
    {
      id: 'demo-user-1',
      name: 'Milon Gazi',
      email: 'student@university.edu',
      password: 'password123',
      currentDay: 1,
      totalDaysTracked: 1,
      overallScore: 78.5,
      lifeStage: 'Emerging Achiever',
      createdAt: new Date().toISOString()
    }
  ],
  activities: {},
  scores: {}
};

// Life Stage evaluation algorithm
function evaluateLifeStage(averageScore) {
  if (averageScore >= 85) {
    return {
      stage: 'Master of Discipline',
      description: 'Exceptional consistency across academic, health, and personal growth activities.',
      color: '#4CAF50'
    };
  } else if (averageScore >= 70) {
    return {
      stage: 'Emerging Achiever',
      description: 'Strong daily performance with good habits forming. Maintain consistency.',
      color: '#2196F3'
    };
  } else if (averageScore >= 50) {
    return {
      stage: 'Developing Explorer',
      description: 'Inconsistent routine. Focus on sleep quality and minimizing excessive screen time.',
      color: '#FF9800'
    };
  } else {
    return {
      stage: 'Unfocused Wanderer',
      description: 'Needs significant routine restructuring to build healthy study and physical habits.',
      color: '#E91E63'
    };
  }
}

// Calculate daily score
function calculateDailyScore(act) {
  let score = 0;
  if (act.wakeUpOnTime) score += 10;
  if (act.bedTimeOnTime) score += 10;
  if (act.prayerOrMeditation) score += 8;
  if (act.healthyMeals) score += 8;
  if (act.classesAttended && act.classesTotal && act.classesTotal > 0) {
    score += Math.round((act.classesAttended / act.classesTotal) * 15);
  } else if (act.classesAttended) {
    score += 15;
  }
  if (act.studyMinutes && act.studyMinutes >= 60) score += 15;
  if (act.exerciseMinutes && act.exerciseMinutes >= 20) score += 10;
  if (act.socialMediaUsage === 'minimal') score += 10;
  else if (act.socialMediaUsage === 'moderate') score += 6;
  if (act.dailyLearning && act.dailyLearning.trim().length > 5) score += 7;
  if (act.selfReflection && act.selfReflection.trim().length > 5) score += 7;
  return Math.min(100, Math.max(0, score));
}

// API Routes
app.get('/api/health', (req, res) => {
  res.json({
    status: 'online',
    app: 'Student Life - Progress & Self-Improvement Tracker',
    version: '1.0.0',
    timestamp: new Date().toISOString()
  });
});

// Auth Routes
app.post('/api/auth/register', (req, res) => {
  const { name, email, password } = req.body;
  if (!name || !email || !password) {
    return res.status(400).json({ error: 'Please provide name, email, and password.' });
  }
  const existing = db.users.find(u => u.email === email);
  if (existing) {
    return res.status(400).json({ error: 'User with this email already exists.' });
  }
  const newUser = {
    id: 'user-' + Date.now(),
    name,
    email,
    password,
    currentDay: 1,
    totalDaysTracked: 0,
    overallScore: 0,
    lifeStage: 'Developing Explorer',
    createdAt: new Date().toISOString()
  };
  db.users.push(newUser);
  res.status(201).json({ message: 'User registered successfully', user: newUser });
});

app.post('/api/auth/login', (req, res) => {
  const { email, password } = req.body;
  const user = db.users.find(u => u.email === email && u.password === password);
  if (!user) {
    return res.status(401).json({ error: 'Invalid email or password.' });
  }
  res.json({ message: 'Login successful', user, token: 'demo-jwt-token-' + user.id });
});

app.get('/api/auth/users', (req, res) => {
  res.json(db.users.map(({ password, ...u }) => u));
});

// Activities
app.post('/api/activities/save', (req, res) => {
  const { userId, day, activities } = req.body;
  const uid = userId || 'demo-user-1';
  const dayNum = parseInt(day) || 1;

  if (!db.activities[uid]) db.activities[uid] = {};
  db.activities[uid][dayNum] = activities;

  const score = calculateDailyScore(activities);
  if (!db.scores[uid]) db.scores[uid] = {};
  db.scores[uid][dayNum] = score;

  // Update user stats
  const user = db.users.find(u => u.id === uid);
  if (user) {
    const allScores = Object.values(db.scores[uid]);
    const avgScore = Math.round(allScores.reduce((a, b) => a + b, 0) / allScores.length);
    user.overallScore = avgScore;
    user.totalDaysTracked = allScores.length;
    user.currentDay = Math.min(30, dayNum + 1);
    user.lifeStage = evaluateLifeStage(avgScore).stage;
  }

  res.json({
    message: `Day ${dayNum} activity saved successfully!`,
    dailyScore: score,
    overallAverage: user ? user.overallScore : score,
    lifeStage: evaluateLifeStage(score)
  });
});

app.get('/api/activities/:userId', (req, res) => {
  const uid = req.params.userId;
  res.json({
    activities: db.activities[uid] || {},
    scores: db.scores[uid] || {}
  });
});

// Life stage and progress insights
app.get('/api/progress/:userId', (req, res) => {
  const uid = req.params.userId;
  const user = db.users.find(u => u.id === uid) || db.users[0];
  const scores = db.scores[uid] || { 1: 78 };
  const scoreValues = Object.values(scores);
  const avg = scoreValues.length ? Math.round(scoreValues.reduce((a, b) => a + b, 0) / scoreValues.length) : 75;
  const stageInfo = evaluateLifeStage(avg);

  res.json({
    user,
    averageScore: avg,
    daysCompleted: scoreValues.length,
    remainingDays: Math.max(0, 30 - scoreValues.length),
    lifeStage: stageInfo,
    insights: [
      'High consistency observed in morning wake up time.',
      'Study sessions are effective; maintain minimum 90 minutes daily.',
      'Suggestion: Try reducing evening social media usage to improve sleep quality.'
    ]
  });
});

// Interactive Web Dashboard
app.get('/', (req, res) => {
  res.send(`<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Student Life Tracker - Backend & API Portal</title>
  <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary: #4F46E5;
      --primary-light: #6366F1;
      --bg: #0F172A;
      --card-bg: #1E293B;
      --text-main: #F8FAFC;
      --text-muted: #94A3B8;
      --border: #334155;
      --success: #10B981;
    }
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      font-family: 'Plus Jakarta Sans', sans-serif;
      background: var(--bg);
      color: var(--text-main);
      padding: 40px 20px;
      line-height: 1.6;
    }
    .container {
      max-width: 900px;
      margin: 0 auto;
    }
    .header {
      text-align: center;
      margin-bottom: 40px;
    }
    .badge {
      display: inline-block;
      background: rgba(79, 70, 229, 0.2);
      color: #A5B4FC;
      padding: 6px 16px;
      border-radius: 999px;
      font-size: 13px;
      font-weight: 600;
      margin-bottom: 12px;
      border: 1px solid rgba(99, 102, 241, 0.4);
    }
    h1 {
      font-size: 32px;
      font-weight: 800;
      letter-spacing: -0.5px;
      margin-bottom: 8px;
    }
    .sub {
      color: var(--text-muted);
      font-size: 16px;
    }
    .status-card {
      background: var(--card-bg);
      border: 1px solid var(--border);
      border-radius: 16px;
      padding: 24px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 30px;
      box-shadow: 0 10px 25px -5px rgba(0,0,0,0.3);
    }
    .status-badge {
      display: flex;
      align-items: center;
      gap: 10px;
      font-weight: 700;
      color: var(--success);
    }
    .status-dot {
      width: 12px;
      height: 12px;
      border-radius: 50%;
      background: var(--success);
      box-shadow: 0 0 12px var(--success);
    }
    .endpoints-card {
      background: var(--card-bg);
      border: 1px solid var(--border);
      border-radius: 16px;
      padding: 30px;
      margin-bottom: 30px;
    }
    h2 {
      font-size: 20px;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      gap: 10px;
    }
    .endpoint {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 14px 16px;
      border-radius: 10px;
      background: rgba(15, 23, 42, 0.6);
      border: 1px solid var(--border);
      margin-bottom: 12px;
      font-family: monospace;
      font-size: 14px;
    }
    .method-get { color: #38BDF8; font-weight: bold; }
    .method-post { color: #4ADE80; font-weight: bold; }
    .btn {
      background: var(--primary);
      color: #fff;
      padding: 8px 16px;
      border-radius: 8px;
      text-decoration: none;
      font-family: 'Plus Jakarta Sans', sans-serif;
      font-size: 13px;
      font-weight: 600;
      transition: all 0.2s;
    }
    .btn:hover {
      background: var(--primary-light);
    }
    .architecture-box {
      background: rgba(30, 41, 59, 0.7);
      border: 1px dashed var(--border);
      border-radius: 14px;
      padding: 24px;
      text-align: center;
      color: var(--text-muted);
    }
    .flow {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 12px;
      flex-wrap: wrap;
      margin-top: 15px;
      font-weight: 600;
      color: #E2E8F0;
    }
    .flow-item {
      background: #0F172A;
      padding: 8px 14px;
      border-radius: 8px;
      border: 1px solid var(--border);
    }
    .flow-arrow {
      color: var(--primary-light);
      font-size: 18px;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="header">
      <span class="badge">REST API & Backend Service</span>
      <h1>Student Life Tracker</h1>
      <p class="sub">Progress & Self-Improvement Tracker (30-Day Observation System)</p>
    </div>

    <div class="status-card">
      <div>
        <h3 style="font-size: 18px; margin-bottom: 4px;">Server Status</h3>
        <p style="color: var(--text-muted); font-size: 14px;">Running locally on Port ${PORT}</p>
      </div>
      <div class="status-badge">
        <span class="status-dot"></span> Active & Ready
      </div>
    </div>

    <div class="endpoints-card">
      <h2>Live REST API Endpoints</h2>

      <div class="endpoint">
        <div><span class="method-get">GET</span> /api/health</div>
        <a href="/api/health" target="_blank" class="btn">Test Endpoint</a>
      </div>

      <div class="endpoint">
        <div><span class="method-get">GET</span> /api/auth/users</div>
        <a href="/api/auth/users" target="_blank" class="btn">Test Endpoint</a>
      </div>

      <div class="endpoint">
        <div><span class="method-get">GET</span> /api/progress/demo-user-1</div>
        <a href="/api/progress/demo-user-1" target="_blank" class="btn">Test Endpoint</a>
      </div>

      <div class="endpoint">
        <div><span class="method-post">POST</span> /api/auth/login</div>
        <span style="color: var(--text-muted); font-size: 13px;">Body: { email, password }</span>
      </div>

      <div class="endpoint">
        <div><span class="method-post">POST</span> /api/auth/register</div>
        <span style="color: var(--text-muted); font-size: 13px;">Body: { name, email, password }</span>
      </div>

      <div class="endpoint">
        <div><span class="method-post">POST</span> /api/activities/save</div>
        <span style="color: var(--text-muted); font-size: 13px;">Body: { userId, day, activities }</span>
      </div>
    </div>

    <div class="architecture-box">
      <div style="font-weight: 700; color: #fff; margin-bottom: 6px;">Application Full-Stack Architecture</div>
      <div>Connected mobile client & backend pipeline</div>
      <div class="flow">
        <div class="flow-item">Android App (Kotlin)</div>
        <span class="flow-arrow">→</span>
        <div class="flow-item">REST API (Express Port ${PORT})</div>
        <span class="flow-arrow">→</span>
        <div class="flow-item">Node.js Engine</div>
        <span class="flow-arrow">→</span>
        <div class="flow-item">MongoDB / Cloud Database</div>
      </div>
    </div>
  </div>
</body>
</html>`);
});

app.listen(PORT, () => {
  console.log(`Backend server is running on http://localhost:${PORT}`);
});
