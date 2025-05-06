package com.hansheung.quiz_app.ui.student

import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    authService: AuthService
): BaseViewModel(authService){

}