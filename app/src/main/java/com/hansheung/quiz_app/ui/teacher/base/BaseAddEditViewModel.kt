package com.hansheung.quiz_app.ui.teacher.base

import android.net.Uri
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.core.service.ProcessCSV
import com.hansheung.quiz_app.data.model.Question

abstract class BaseAddEditViewModel(
    private val processCSV: ProcessCSV,
    authService: AuthService
) : BaseViewModel(authService) {
    protected var questions: List<Question> = emptyList()

    abstract fun submit(
        title: String,
        desc: String,
        timeLimit: String,
        selectedCsvFile: Uri?
    )

    fun getQuestionsFromCSV(uri: Uri) {
        questions = processCSV.getQuestionsFromCSV(uri)
    }
}