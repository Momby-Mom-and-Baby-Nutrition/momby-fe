package com.example.momby.presentation.forgot_password

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _isSuccessful  = MutableStateFlow<Boolean>(false)
    val isSuccessful:StateFlow<Boolean> = _isSuccessful

    fun forgotPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener{
            if(it.isSuccessful){
                println("Reset Password Berhasil dikirim ke email " + email)
                _isSuccessful.value = true
            } else {
                println(it.exception)
            }
        }
    }
}