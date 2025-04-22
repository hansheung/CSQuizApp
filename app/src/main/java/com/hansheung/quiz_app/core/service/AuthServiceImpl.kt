package com.hansheung.mob21firebase.core.service

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthServiceImpl(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
): AuthService {
    override suspend fun login(email: String, password: String): Boolean {
        val authResult = firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).await()
        return authResult.user != null
    }

    override suspend fun login(context: Context) : Boolean {
        return withContext(Dispatchers.IO){
            try {
                val idToken = getGoogleCredential(context)
                Log.d("debugging", idToken.toString())
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                Log.d("debugging", credential.toString())
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                Log.d("debugging", authResult.toString())

                authResult.user != null
            }catch (e: GetCredentialException){
                Log.d("AuthService", "Google Sign-In failed", e)
                false
            }catch (e:Exception){
                Log.d("AuthService", "Something went wrong", e)
                false
            }
        }
    }

    override suspend fun register(email: String, password: String): Boolean {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user != null
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getLoggedInUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun getUid(): String? {
        return firebaseAuth.uid
    }
    private suspend fun getGoogleCredential(context: Context): String? {
        val clientId = "231378653447-gef5jtbmdtpolok99tfg30sisbvep5g1.apps.googleusercontent.com" // Replace with your actual server client ID

        val credentialManager = CredentialManager.create(context)
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            Log.d("debugging", result.credential.data.toString())
            result.credential.data.getString("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID_TOKEN")
        } catch (e: Exception) {
            Log.e("authService", "Error fetching credential", e)
            null
        }
    }

}