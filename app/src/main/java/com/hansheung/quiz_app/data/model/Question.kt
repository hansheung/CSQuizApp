package com.hansheung.quiz_app.data.model

data class Question(
    val questionText: String = "",
    val options: List<String> = emptyList(),
    val correctAnswer: String = "",
)
