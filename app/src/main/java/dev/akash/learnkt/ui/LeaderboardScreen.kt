package dev.akash.learnkt.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class LeaderboardEntry(
    val userName: String,
    val score: Int
)

@Composable
fun LeaderboardScreen(
    quizId: String,
    quizTopic: String,
    onBack: () -> Unit
) {
    var leaderboard by remember { mutableStateOf<List<LeaderboardEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(quizId) {
        scope.launch {
            try {
                val firestore = FirebaseFirestore.getInstance()
                val usersSnapshot = firestore.collection("users").get().await()
                val entries = usersSnapshot.documents.mapNotNull { doc ->
                    val name = doc.getString("name") ?: return@mapNotNull null
                    val scores = doc.get("scores") as? Map<String, Any> ?: return@mapNotNull null
                    val score = scores[quizId]?.let { value ->
                        when (value) {
                            is Long -> value.toInt()
                            is Int -> value
                            else -> 0
                        }
                    } ?: return@mapNotNull null
                    LeaderboardEntry(userName = name, score = score)
                }.sortedByDescending { it.score }
                leaderboard = entries
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Failed to load leaderboard: ${e.message}"
                isLoading = false
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Leaderboard: $quizTopic", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else if (leaderboard.isEmpty()) {
                Text(
                    text = "No users have taken this quiz yet.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(leaderboard) { entry ->
                        LeaderboardItem(entry)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LeaderboardItem(entry: LeaderboardEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = entry.userName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Score: ${entry.score}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}