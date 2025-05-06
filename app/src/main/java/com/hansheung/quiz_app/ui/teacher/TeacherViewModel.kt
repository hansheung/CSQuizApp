package com.hansheung.quiz_app.ui.teacher

import android.content.res.loader.ResourcesProvider
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.data.repo.QuizRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(

    authService: AuthService,
    private val quizRepo: QuizRepo

): BaseViewModel(authService){

    private val _quizzes = MutableStateFlow<List<Quiz>>(emptyList())
    val quizzes: StateFlow<List<Quiz>> = _quizzes

    private val _empty = MutableStateFlow(true)
    val empty: StateFlow<Boolean> = _empty

    init {
        getAllQuiz()
    }

    private fun getAllQuiz() {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepo.getAllQuiz().collect { quizzes ->
                _quizzes.value = quizzes
                _empty.value = quizzes.isEmpty()
            }
        }
    }

    fun deleteQuiz(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            quizRepo.deleteQuiz(id)
        }
    }
}