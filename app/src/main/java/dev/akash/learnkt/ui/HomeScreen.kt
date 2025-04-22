package dev.akash.learnkt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import dev.akash.learnkt.data.AuthManager
import dev.akash.learnkt.data.Quiz
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    authManager: AuthManager,
    onSignOut: () -> Unit,
    onStartQuiz: (String) -> Unit,
    onReviewQuiz: (String) -> Unit,
    onLeaderboardClick: (String, String) -> Unit
) {
    val user = authManager.getCurrentUser()
    var userName by remember { mutableStateOf("User") }
    var scores by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    var quizzes by remember { mutableStateOf<List<Quiz>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(user) {
        user?.uid?.let { uid ->
            val document = FirebaseFirestore.getInstance().collection("users").document(uid).get().await()
            userName = document.getString("name") ?: "User"
            val rawScores = document.get("scores") as? Map<String, Any> ?: emptyMap()
            scores = rawScores.mapValues { (_, value) ->
                when (value) {
                    is Long -> value.toInt()
                    is Int -> value
                    else -> 0
                }
            }

            val result = authManager.getAvailableQuizzes()
            if (result.isSuccess) {
                quizzes = result.getOrNull() ?: emptyList()
            } else {
                errorMessage = result.exceptionOrNull()?.message ?: "Failed to load quizzes"
            }
        }
    }

    Scaffold {
        padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Welcome, $userName!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { authManager.signOut(); onSignOut() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Sign Out")
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            if (quizzes.isEmpty() && errorMessage == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    items(quizzes.size) { index ->
                        val quiz = quizzes[index]
                        QuizCard(
                            quiz = quiz,
                            score = scores[quiz.id],
                            totalQuestions = quiz.questions.size,
                            onStartQuiz = { onStartQuiz(quiz.id) },
                            onReviewQuiz = { onReviewQuiz(quiz.id) },
                            onLeaderboardClick = { onLeaderboardClick(quiz.id, quiz.topic) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun QuizCard(
    quiz: Quiz,
    score: Int?,
    totalQuestions: Int,
    onStartQuiz: () -> Unit,
    onReviewQuiz: () -> Unit,
    onLeaderboardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = quiz.topic,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (score != null) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Score: $score / $totalQuestions",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            FilledTonalButton(onClick = onReviewQuiz) {
                                Text("Review Answers")
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = { score.toFloat() / totalQuestions },
                            modifier = Modifier
                                .height(20.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp)),
                            gapSize = (-16).dp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onLeaderboardClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Leaderboard")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onStartQuiz,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (score == null) "Start Quiz" else "Restart Quiz")
                    }
                }
            }
        }
    }
}