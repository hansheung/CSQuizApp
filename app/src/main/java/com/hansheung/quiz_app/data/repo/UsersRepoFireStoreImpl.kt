package com.hansheung.csnoteapp.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.quiz_app.core.CustomException
import com.hansheung.quiz_app.data.model.User
import com.hansheung.quiz_app.data.repo.UsersRepo
import kotlinx.coroutines.tasks.await

class UsersRepoFireStoreImpl(

    private val db: FirebaseFirestore = Firebase.firestore,
    private val authService: AuthService

): UsersRepo {

    private fun getCollectionRef(): CollectionReference {
        val uid = authService.getUid() ?: throw CustomException("No valid user found")
        return db.collection("users")
    }

    override suspend fun getUser(id: String): User? {
        val snapshot = getCollectionRef().document(id).get().await()
        return snapshot.toObject(User::class.java)
    }

    override suspend fun setUserRole(user: User) {
        getCollectionRef().document(user.id).set(user).await()
    }


}