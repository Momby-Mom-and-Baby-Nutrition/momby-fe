package com.example.momby.presentation.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val datastore: DataStore<Preferences>,
    private val auth: FirebaseAuth
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
                    _loginState.value = LoginState.Succes
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

}

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    object Succes: LoginState()
    data class Error(val message:String):LoginState()
}