package com.hansheung.quiz_app.data.repo

import com.hansheung.quiz_app.data.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepo {
    suspend fun getUser(id: String): User?
    suspend fun setUserRole(user: User)
}