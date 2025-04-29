package com.hansheung.mob21firebase.ui.base

import androidx.lifecycle.ViewModel
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.quiz_app.core.CustomException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel(
    val authService: AuthService
): ViewModel() {

    //One regardless of other receive, Mutable State flow
    protected val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    protected val _finish = MutableSharedFlow<Unit>()
    val finish = _finish.asSharedFlow()

    //First T is a generic type
    suspend fun <T>errorHandler(func: suspend()->T?):T?{
        return try{
            func.invoke()
        } catch(e: CustomException) {
            _error.emit(e.message.toString())
            null
        } catch(e: IllegalArgumentException) {
            _error.emit(e.message.toString())
            null
        } catch (e:Exception) {
            _error.emit("Something went wrong")
            throw e
            null //There is a return type T? So I need to return null
        }
    }

    fun logout(){
        authService.logout()
    }
}