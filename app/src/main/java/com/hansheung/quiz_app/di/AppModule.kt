package com.hansheung.quiz_app.di

import com.google.firebase.firestore.FirebaseFirestore
import com.hansheung.quiz_app.data.repo.UsersRepo
import com.hansheung.csnoteapp.data.repo.UsersRepoFireStoreImpl
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.core.service.AuthServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUsersRepo(
        authService: AuthService
    ): UsersRepo {
        return UsersRepoFireStoreImpl(
            authService = authService
        )
    }

    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }

}