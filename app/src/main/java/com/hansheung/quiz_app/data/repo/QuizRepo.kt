package com.hansheung.quiz_app.data.repo

import com.hansheung.quiz_app.data.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepo {
    suspend fun getAllQuiz(): Flow<List<Quiz>>
    suspend fun createQuiz(quiz: Quiz)
    suspend fun getQuizById(id:String): Quiz?
    suspend fun deleteQuiz(id: String)
    suspend fun updateQuiz(quiz:Quiz)

}