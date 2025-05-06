package com.hansheung.mob21firebase.core.service

import android.content.Context
import com.google.firebase.auth.FirebaseUser

interface AuthService {
    suspend fun login(email: String, password: String): Boolean
    suspend fun login(context: Context): Boolean
    suspend fun register(email: String, password: String): Boolean
    fun logout()
    fun getLoggedInUser(): FirebaseUser?
    fun getUid():String?
}