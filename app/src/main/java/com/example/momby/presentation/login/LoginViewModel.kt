package com.example.momby.presentation.login

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.momby.data.entity.OneTapSignInResponse
import com.example.momby.model.User
import com.example.momby.presentation.register.RegisterState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val datastore: DataStore<Preferences>,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val googleSignInClient: GoogleSignInClient
):ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState:StateFlow<LoginState> = _loginState

    private val _isStayLogin = MutableStateFlow<Boolean>(false)
    val isStayLogin:StateFlow<Boolean> = _isStayLogin

    private val UID = stringPreferencesKey("uid")

    fun loginWithEmail(email:String,pass: String){
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{task->
                if(task.isSuccessful){
                    _loginState.value = LoginState.Success
                }else{
                    task.exception?.let {
                        _loginState.value = LoginState.Error(it.localizedMessage ?: "Login Failed")
                    }
                }
            }
        }
    }

    suspend fun saveUID(){
        datastore.edit {
            it[UID] = auth.currentUser?.uid.toString()
        }
    }

    fun setStayLogin(stayLogin: Boolean) {
        _isStayLogin.value = stayLogin
    }

    fun resetLoginState(){
        _loginState.value = LoginState.Idle
    }

    fun handleSignInResult(data: Intent?) {
        _loginState.value = LoginState.Loading
        if (data == null) {
            _loginState.value = LoginState.Error("Sign-In cancelled")
            return
        }

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                checkIfUserExists(user.uid){exists ->
                                    if (exists){
                                        _loginState.value = LoginState.Success
                                        println("User exists, sign-in successful")
                                    } else{
                                        val user = User()
                                        saveUserData(user)
                                        _loginState.value = LoginState.Success
                                    }
                                }
                            }

                        } else {
                            println("Task Gagal")
                            _loginState.value = LoginState.Error("Login With Google Failed: ${signInTask.exception?.localizedMessage}")
                        }
                    }
            }
        } catch (e: ApiException) {
            println("Catch API EXCEPTION")
            _loginState.value = LoginState.Error("Login With Google Failed: ${e.localizedMessage}")
        }
    }
    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }
    private fun checkIfUserExists(uid: String, callback: (Boolean) -> Unit) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                callback(document.exists())
            }
            .addOnFailureListener {
                callback(false)
            }
    }
    fun saveUserData(
        user: User
    ) {
        val currentUID = auth.currentUser?.uid
        currentUID?.let {
            db.collection("users")
                .document(it)
                .set(user)
                .addOnSuccessListener {
                    println("User data berhasil ditambah!")
                }
                .addOnFailureListener { e ->
                    println("Error dalam menambah data: $e")
                }
        }
    }
}

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val message:String):LoginState()
}