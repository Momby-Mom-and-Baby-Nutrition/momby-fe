package com.example.momby.presentation.personal_data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.momby.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PersonalDataViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _isRegisterSuccessful = MutableStateFlow(false)
    val isRegisterSuccessful = _isRegisterSuccessful.asStateFlow()



    fun saveUserData(
        user: User
    ) {
        val currentUID = auth.currentUser?.uid
        val userData = hashMapOf(
            "name" to user.name,
            "weightBefore" to user.weightBefore,
            "weightAfter" to user.weightAfter,
            "height" to user.height,
            "age" to user.age,
            "gestatAge" to user.gestatAge,
            "birthDate" to user.birthDate
        )
        currentUID?.let {
            db.collection("users")
                .document(it)
                .set(userData)
                .addOnSuccessListener {
                    _isRegisterSuccessful.value = true
                    println("User data berhasil ditambah!")
                }
                .addOnFailureListener { e ->
                    println("Error dalam menambah data: $e")
                }
        }
    }
}