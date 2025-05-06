package com.hansheung.quiz_app.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.hansheung.quiz_app.data.repo.UsersRepo
import com.hansheung.csnoteapp.data.repo.UsersRepoFireStoreImpl
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.core.service.AuthServiceImpl
import com.hansheung.quiz_app.core.service.ProcessCSV
import com.hansheung.quiz_app.data.repo.QuizRepo
import com.hansheung.quiz_app.data.repo.QuizRepoFireStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideQuizRepo(authService: AuthService): QuizRepo {
        return QuizRepoFireStoreImpl(authService)
    }

    @Provides
    @Singleton
    fun provideStorageService(
        @ApplicationContext context: Context
    ): ProcessCSV = ProcessCSV(context)
}