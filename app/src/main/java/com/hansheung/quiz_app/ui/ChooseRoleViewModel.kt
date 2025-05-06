package com.hansheung.quiz_app.ui

import androidx.lifecycle.viewModelScope
import com.hansheung.quiz_app.data.repo.UsersRepo
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import com.hansheung.quiz_app.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChooseRoleViewModel @Inject constructor(
    private val repo: UsersRepo,
    authService: AuthService
) : BaseViewModel(authService) {

    private val _roleSaved = MutableSharedFlow<String>()
    val roleSaved = _roleSaved.asSharedFlow()

    fun saveUserRole(role: String) {
        val uid = authService.getUid() ?: return
        val email = authService.getLoggedInUser()?.email ?: ""

        val user = User(id = uid, email = email, role = role)

        viewModelScope.launch {
            repo.setUserRole(user)
            _roleSaved.emit(role)
        }
    }
}
