package com.example.momby.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.momby.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db:FirebaseFirestore
):ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState:StateFlow<RegisterState> = _registerState

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
    fun registerWithEmail(email:String,password:String, confPassword:String){
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty() || confPassword.isEmpty()){
                _registerState.value = RegisterState.Error("Pastikan data yang anda masukkan tidak kosong!")
            } else if (password != confPassword) {
                _registerState.value = RegisterState.Error("Kata sandi tidak cocok")
            } else {
                _registerState.value = RegisterState.Loading
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _registerState.value = RegisterState.Success("Registrasi Berhasil!")
                            val user = User()
                            saveUserData(user)
                        } else {
                            _registerState.value = RegisterState.Error(task.exception?.message ?: "Registrasi Gagal")
                        }
                    }
            }
        }
    }

}

sealed class RegisterState{
    object Idle:RegisterState()
    object Loading:RegisterState()
    data class Success(val message:String) :RegisterState()
    data class Error(val error: String):RegisterState()
}