package dev.akash.learnkt.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthManager(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signIn(email: String, password: String): Result<FirebaseUser?> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String, name: String): Result<FirebaseUser?> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                val userData = hashMapOf(
                    "name" to name,
                    "scores" to emptyMap<String, Int>(),
                    "answers" to emptyMap<String, List<Int>>()
                )
                firestore.collection("users").document(user.uid).set(userData).await()
            }
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun getUserName(uid: String): String? {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            document.getString("name")
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getQuiz(quizId: String): Result<Quiz> {
        return try {
            val document = firestore.collection("quizzes").document(quizId).get().await()
            val topic = document.getString("topic") ?: ""
            val questionsData = document.get("questions") as? List<Map<String, Any>> ?: emptyList()

            val questions = questionsData.map { data ->
                when (data["type"] as String) {
                    "multiple_choice" -> QuizQuestion.MultipleChoice(
                        id = data["id"] as? String ?: "",
                        text = data["text"] as String,
                        options = data["options"] as List<String>,
                        correctAnswer = (data["correctAnswer"] as Long).toInt()
                    )
                    "true_false" -> QuizQuestion.TrueFalse(
                        id = data["id"] as? String ?: "",
                        text = data["text"] as String,
                        correctAnswer = (data["correctAnswer"] as Long).toInt()
                    )
                    else -> throw IllegalArgumentException("Unknown question type")
                }
            }
            Result.success(Quiz(id = quizId, topic = topic, questions = questions))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAvailableQuizzes(): Result<List<Quiz>> {
        return try {
            val snapshot = firestore.collection("quizzes").get().await()
            val quizzes = snapshot.documents.mapNotNull { document ->
                val topic = document.getString("topic") ?: ""
                val questionsData = document.get("questions") as? List<Map<String, Any>> ?: emptyList()
                val quizId = document.id

                val questions = questionsData.map { data ->
                    when (data["type"] as String) {
                        "multiple_choice" -> QuizQuestion.MultipleChoice(
                            id = data["id"] as? String ?: "",
                            text = data["text"] as String,
                            options = data["options"] as List<String>,
                            correctAnswer = (data["correctAnswer"] as Long).toInt()
                        )
                        "true_false" -> QuizQuestion.TrueFalse(
                            id = data["id"] as? String ?: "",
                            text = data["text"] as String,
                            correctAnswer = (data["correctAnswer"] as Long).toInt()
                        )
                        else -> throw IllegalArgumentException("Unknown question type")
                    }
                }
                Quiz(id = quizId, topic = topic, questions = questions)
            }
            Result.success(quizzes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserQuizData(
        uid: String,
        quizId: String,
        score: Int,
        answers: List<Int>
    ) {
            val userDoc = firestore.collection("users").document(uid)
            val currentData = userDoc.get().await()
            val currentScores = currentData.get("scores") as? Map<String, Int> ?: emptyMap()
            val currentAnswers = currentData.get("answers") as? Map<String, List<Int>> ?: emptyMap()

            val newScores = currentScores.toMutableMap().apply {
                put(quizId, score)
            }
            val newAnswers = currentAnswers.toMutableMap().apply {
                put(quizId, answers)
            }

            userDoc.update(
                mapOf(
                    "scores" to newScores,
                    "answers" to newAnswers
                )
            ).await()
    }

    suspend fun getUserAnswers(uid: String, quizId: String): List<Int> {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            val answersMap = document.get("answers") as? Map<String, List<Int>> ?: emptyMap()
            answersMap[quizId] ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}