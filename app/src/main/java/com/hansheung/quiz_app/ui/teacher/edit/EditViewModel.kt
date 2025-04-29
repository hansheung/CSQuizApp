package com.hansheung.quiz_app.ui.teacher.edit

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.quiz_app.core.service.ProcessCSV
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.data.repo.QuizRepo
import com.hansheung.quiz_app.ui.teacher.base.BaseAddEditViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: QuizRepo,
    authService: AuthService,
    processCSV: ProcessCSV
) : BaseAddEditViewModel(processCSV, authService) {

    private val quizId = state.get<String>("quizId")

    protected val _quiz = MutableSharedFlow<Quiz>()
    val quiz: SharedFlow<Quiz> = _quiz

    init {
        viewModelScope.launch(Dispatchers.IO) {
            quizId?.let {
                errorHandler {
                    val fetchedQuiz = repo.getQuizById(it)
                    if (fetchedQuiz != null) {
                        questions = fetchedQuiz.questions
                        _quiz.emit(fetchedQuiz)
                    }
                }
            }
        }
    }

    override fun submit(title: String, desc: String, timeLimit: String, selectedCsvFile: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotEmpty() && desc.isNotEmpty() && timeLimit.isNotEmpty()) {
                val quiz = repo.getQuizById(quizId!!)
                quiz?.let {
                    errorHandler {
                        repo.updateQuiz(
                            it.copy(
                                title = title,
                                desc = desc,
                                timeLimit = timeLimit,
                                questions = questions
                            )
                        )
                        _finish.emit(Unit)
                    }
                }
            } else _error.emit("Details cannot be empty and TimeLimit must be greater than 0")
        }
    }
}