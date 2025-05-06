package com.hansheung.quiz_app.ui.teacher.add

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.quiz_app.core.service.ProcessCSV
import com.hansheung.quiz_app.data.model.Question
import com.hansheung.quiz_app.data.model.Quiz
import com.hansheung.quiz_app.data.repo.QuizRepo
import com.hansheung.quiz_app.ui.teacher.base.BaseAddEditViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repo: QuizRepo,
    authService: AuthService,
    processCSV: ProcessCSV,
) : BaseAddEditViewModel(processCSV,authService) {

    override fun submit(
        title: String,
        desc: String,
        timeLimit: String,
        selectedCsvFile: Uri?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotEmpty() && desc.isNotEmpty() && timeLimit.isNotEmpty()) {
                errorHandler {
                    val id = authService.getUid() ?: throw Exception("User not found")
                    repo.createQuiz(
                        Quiz(
                            teacherId = id,
                            title = title,
                            desc = desc,
                            timeLimit = timeLimit,
                            questions = getCurrentQuestions()
                        )
                    )
                    _finish.emit(Unit)
                }
            } else _error.emit("Details cannot be empty!")
        }
    }

    private fun getCurrentQuestions(): List<Question> {
        return questions
    }
}