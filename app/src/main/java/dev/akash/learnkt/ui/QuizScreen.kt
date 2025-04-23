package dev.akash.learnkt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.akash.learnkt.data.AuthManager
import dev.akash.learnkt.data.Quiz
import dev.akash.learnkt.data.QuizQuestion
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    authManager: AuthManager,
    quizId: String,
    readOnly: Boolean = false,
    restart: Boolean = false,
    onQuizComplete: () -> Unit
) {
    var quiz by remember { mutableStateOf<Quiz?>(null) }
    val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }
    var previousAnswers by remember { mutableStateOf<List<Int>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showResults by remember { mutableStateOf(readOnly) }
    var score by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(quizId) {
        val result = authManager.getQuiz(quizId)
        if (result.isSuccess) {
            quiz = result.getOrNull()
        } else {
            errorMessage = result.exceptionOrNull()?.message ?: "Failed to load quiz"
        }

        if (!restart && !readOnly) {
            authManager.getCurrentUser()?.uid?.let { uid ->
                previousAnswers = authManager.getUserAnswers(uid, quizId)
                previousAnswers.forEachIndexed { index, answer ->
                    selectedAnswers[index] = answer
                }
            }
        }

        if (readOnly) {
            authManager.getCurrentUser()?.uid?.let { uid ->
                previousAnswers = authManager.getUserAnswers(uid, quizId)
                previousAnswers.forEachIndexed { index, answer ->
                    selectedAnswers[index] = answer
                }
            }
        }

        if (readOnly) {
            quiz?.let { q ->
                score = q.questions.count { question ->
                    selectedAnswers[q.questions.indexOf(question)] == question.correctAnswer
                }
            }
        }
    }

    quiz?.let { q ->
        Scaffold(
            bottomBar = {
                if (!showResults && !readOnly) {
                    Button(
                        onClick = {
                            score = q.questions.count { question ->
                                selectedAnswers[q.questions.indexOf(question)] == question.correctAnswer
                            }
                            scope.launch {
                                authManager.getCurrentUser()?.uid?.let { uid ->
                                    authManager.updateUserQuizData(
                                        uid = uid,
                                        quizId = quizId,
                                        score = score,
                                        answers = q.questions.indices.map { selectedAnswers[it] ?: -1 }
                                    )
                                }
                            }
                            showResults = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Submit Quiz")
                    }
                } else {
                    Button(
                        onClick = { onQuizComplete() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Back to Home")
                    }
                }
            }
        ) { padding ->
            if (showResults || readOnly) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (readOnly) "Review: ${q.topic}" else "Quiz Results: ${q.topic}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Score: $score / ${q.questions.size}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        itemsIndexed(q.questions) { index, question ->
                            ResultItem(
                                question = question,
                                selectedAnswer = selectedAnswers[index],
                                correctAnswer = question.correctAnswer
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Quiz: ${q.topic}", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        itemsIndexed(q.questions) { index, question ->
                            QuestionItem(
                                question = question,
                                selectedAnswer = selectedAnswers[index],
                                onAnswerSelected = { selectedAnswers[index] = it }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun QuestionItem(
    question: QuizQuestion,
    selectedAnswer: Int?,
    onAnswerSelected: (Int) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.text, style = MaterialTheme.typography.bodyLarge)

            when (question) {
                is QuizQuestion.MultipleChoice -> {
                    question.options.forEachIndexed { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAnswerSelected(index) }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedAnswer == index,
                                onClick = { onAnswerSelected(index) }
                            )
                            Text(text = option)
                        }
                    }
                }
                is QuizQuestion.TrueFalse -> {
                    listOf("False", "True").forEachIndexed { index, option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAnswerSelected(index) }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedAnswer == index,
                                onClick = { onAnswerSelected(index) }
                            )
                            Text(text = option)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultItem(
    question: QuizQuestion,
    selectedAnswer: Int?,
    correctAnswer: Int
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.text, style = MaterialTheme.typography.bodyLarge)

            when (question) {
                is QuizQuestion.MultipleChoice -> {
                    question.options.forEachIndexed { index, option ->
                        val borderColor = when {
                            index == correctAnswer -> Color.Green
                            index == selectedAnswer && selectedAnswer != correctAnswer -> Color.Red
                            else -> Color.Transparent
                        }
                        val iconBackground = when {
                            index == correctAnswer -> Color.Green
                            index == selectedAnswer && selectedAnswer != correctAnswer -> Color.Red
                            else -> Color.Transparent
                        }
                        val trailingIcon = when {
                            index == correctAnswer -> Icons.Default.Check
                            index == selectedAnswer && selectedAnswer != correctAnswer -> Icons.Default.Close
                            else -> null
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(
                                    width = 2.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedAnswer == index,
                                    onClick = {},
                                    enabled = false
                                )
                                Text(text = option)
                            }
                            if (trailingIcon != null) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(iconBackground, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = trailingIcon,
                                        contentDescription = if (index == correctAnswer) "Correct" else "Incorrect",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                is QuizQuestion.TrueFalse -> {
                    listOf("False", "True").forEachIndexed { index, option ->
                        val borderColor = when {
                            index == correctAnswer -> Color.Green
                            index == selectedAnswer && selectedAnswer != correctAnswer -> Color.Red
                            else -> Color.Transparent
                        }
                        val iconBackground = when {
                            index == correctAnswer -> Color.Green
                            index == selectedAnswer && selectedAnswer != correctAnswer -> Color.Red
                            else -> Color.Transparent
                        }
                        val trailingIcon = when {
                            index == correctAnswer -> Icons.Default.Check
                            index == selectedAnswer && selectedAnswer != correctAnswer -> Icons.Default.Close
                            else -> null
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(
                                    width = 2.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedAnswer == index,
                                    onClick = {},
                                    enabled = false
                                )
                                Text(text = option)
                            }
                            if (trailingIcon != null) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(iconBackground, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = trailingIcon,
                                        contentDescription = if (index == correctAnswer) "Correct" else "Incorrect",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}