package com.hansheung.quiz_app.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.hansheung.quiz_app.data.repo.UsersRepo
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    authService: AuthService,
    private val repo: UsersRepo,
) : BaseViewModel(authService) {

    private val _shouldChooseRole = MutableSharedFlow<Boolean>()
    val shouldChooseRole = _shouldChooseRole.asSharedFlow()

    private val _navigateToRole = MutableSharedFlow<String>() // role: "teacher" or "student"
    val navigateToRole = _navigateToRole.asSharedFlow()

    fun loginWithGoogle(context: Context) {
        viewModelScope.launch {
            errorHandler {
                val result = authService.login(context)
                if (result) {
                    val user = authService.getLoggedInUser()
                    val uid = authService.getUid()
                    if (user != null && uid != null) {

                        val doc = repo.getUser(uid)
                        val role = doc?.role

                        if (role == null) {
                            _shouldChooseRole.emit(true)
                        } else {
                            _navigateToRole.emit(role)
                        }
                    } else {
                        _error.emit("User not found.")
                    }
                }
            }
        }
    }
}
