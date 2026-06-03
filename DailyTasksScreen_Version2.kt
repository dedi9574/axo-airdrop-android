package com.axofoundation.airdrop.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DailyTasksScreen(navController: NavHostController) {
    val tasks = listOf(
        TaskItem(
            id = 1,
            title = "Follow Twitter",
            description = "Follow @AXOFoundation on Twitter",
            points = 100,
            icon = "🐦",
            isCompleted = true,
            isLocked = false
        ),
        TaskItem(
            id = 2,
            title = "Join Discord",
            description = "Join our Discord community server",
            points = 150,
            icon = "💬",
            isCompleted = true,
            isLocked = false
        ),
        TaskItem(
            id = 3,
            title = "Daily Check-in",
            description = "Complete daily check-in task",
            points = 50,
            icon = "✅",
            isCompleted = false,
            isLocked = false
        ),
        TaskItem(
            id = 4,
            title = "Referral Bonus",
            description = "Invite 3 friends to earn bonus",
            points = 500,
            icon = "👥",
            isCompleted = false,
            isLocked = false
        ),
        TaskItem(
            id = 5,
            title = "Share on Social",
            description = "Share airdrop link on social media",
            points = 200,
            icon = "📢",
            isCompleted = false,
            isLocked = true
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Daily Tasks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFFD4AF37))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0a0a0a),
                    titleContentColor = Color(0xFFFFFFFF)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFF080808)
                )
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF151515)),
                    border = BorderStroke(1.dp, Color(0xFF2a2a2a))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Today's Tasks Progress",
                            fontSize = 14.sp,
                            color = Color(0xFFb0b0b0)
                        )
                        Text(
                            text = "3 of 5 Completed",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD4AF37),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        LinearProgressIndicator(
                            progress = 0.6f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .padding(top = 12.dp),
                            color = Color(0xFFD4AF37),
                            trackColor = Color(0xFF2a2a2a)
                        )
                    }
                }
            }

            items(tasks) { task ->
                TaskCard(task = task)
            }
        }
    }
}

@Composable
private fun TaskCard(task: TaskItem) {
    var showDetails by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) Color(0xFF1a2a1a) else Color(0xFF151515)
        ),
        border = BorderStroke(
            1.dp,
            when {
                task.isCompleted -> Color(0xFF4a7c4a)
                task.isLocked -> Color(0xFF8B4513)
                else -> Color(0xFF2a2a2a)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = task.icon, fontSize = 32.sp)
                    Column {
                        Text(
                            text = task.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF)
                        )
                        Text(
                            text = task.description,
                            fontSize = 12.sp,
                            color = Color(0xFFb0b0b0)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when {
                        task.isCompleted -> {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Completed",
                                tint = Color(0xFF4caf50),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        task.isLocked -> {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Color(0xFFb0b0b0),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        else -> {
                            Text(
                                text = "+${task.points}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD4AF37)
                            )
                        }
                    }
                }
            }

            if (!task.isCompleted && !task.isLocked) {
                Button(
                    onClick = { showDetails = !showDetails },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4AF37)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Complete Task",
                        color = Color(0xFF080808),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

data class TaskItem(
    val id: Int,
    val title: String,
    val description: String,
    val points: Int,
    val icon: String,
    val isCompleted: Boolean,
    val isLocked: Boolean
)