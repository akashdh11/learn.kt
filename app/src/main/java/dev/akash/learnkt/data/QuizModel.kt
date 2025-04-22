package dev.akash.learnkt.data

import com.google.firebase.firestore.PropertyName

sealed class QuizQuestion {
    abstract val id: String
    abstract val text: String
    abstract val correctAnswer: Int

    data class MultipleChoice(
        @PropertyName("id") override val id: String = "",
        @PropertyName("text") override val text: String,
        @PropertyName("options") val options: List<String>,
        @PropertyName("correctAnswer") override val correctAnswer: Int
    ) : QuizQuestion()

    data class TrueFalse(
        @PropertyName("id") override val id: String = "",
        @PropertyName("text") override val text: String,
        @PropertyName("correctAnswer") override val correctAnswer: Int
    ) : QuizQuestion()
}

data class Quiz(
    val id: String = "",
    val topic: String,
    val questions: List<QuizQuestion>
)