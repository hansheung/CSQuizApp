package com.hansheung.quiz_app.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.quiz_app.data.model.Quiz
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepoFireStoreImpl(
    private val authService: AuthService

): QuizRepo
{
    private fun getCollection(): CollectionReference {
        return Firebase.firestore.collection("quizzes")
    }

    override suspend fun getAllQuiz()= callbackFlow {
        val listener = getCollection().addSnapshotListener{ value, error ->
            if(error!=null){
                trySend(emptyList())
                return@addSnapshotListener
            }

            val quizzes = mutableListOf<Quiz>()

            value?.documents?.forEach { doc ->
                val quiz = doc.toObject(Quiz::class.java)

                if(quiz!=null && quiz.teacherId == authService.getUid()){
                    quizzes.add(quiz.copy(quizId = doc.id))
                }
            }
            trySend(quizzes)
        }
        awaitClose{ listener.remove() }
    }

    override suspend fun createQuiz(quiz: Quiz) {
        val ref = getCollection().document()
        ref.set(quiz.copy(quizId = ref.id)).await()
    }

    override suspend fun updateQuiz(quiz: Quiz) {
        getCollection().document(quiz.quizId!!).set(quiz).await()
    }

    override suspend fun getQuizById(id: String): Quiz? {
        val snapshot = getCollection().document(id).get().await()
        return snapshot.toObject(Quiz::class.java)
    }

    override suspend fun deleteQuiz(id: String) {
        getCollection().document(id).delete().await()
    }
}