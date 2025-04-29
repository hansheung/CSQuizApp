package com.hansheung.quiz_app.data.model

data class Quiz(
    val quizId : String? = null,
    val title: String = "",
    val desc: String = "",
    val timeLimit: String = "",
    val questions:List<Question> = emptyList(),
    val teacherId: String? = null,
    val studentList: List<Student> = emptyList()
)
