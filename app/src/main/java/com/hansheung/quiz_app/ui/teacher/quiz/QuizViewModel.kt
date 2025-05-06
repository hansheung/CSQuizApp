package com.hansheung.quiz_app.ui.teacher.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.data.repo.QuizRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: QuizRepo,
    authService: AuthService
): BaseViewModel(authService) {

    protected val _quiz = MutableSharedFlow<Quiz>()
    val quiz: SharedFlow<Quiz> = _quiz

    init {
        state.get<String>("quizId")?.let { getQuizById(it) }
    }
    private fun getQuizById(id:String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                val quiz = repo.getQuizById(id) ?: throw Exception("Quiz doesn't exist")
                _quiz.emit(quiz)
            }
        }
    }
}