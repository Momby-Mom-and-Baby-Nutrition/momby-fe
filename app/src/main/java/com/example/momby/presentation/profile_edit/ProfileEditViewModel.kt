package com.example.momby.presentation.profile_edit

import androidx.lifecycle.ViewModel
import com.example.momby.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val auth:FirebaseAuth,
    private val db:FirebaseFirestore
):ViewModel(){

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        fetchUserData()
    }
    private fun fetchUserData(){
        val currentUID = auth.currentUser?.uid
        if (currentUID !=null){
            db.collection("users").document(currentUID).get()
                .addOnSuccessListener {
                    if (it.exists()){
                        val user = it.toObject(User::class.java)
                        _user.value = user
                    } else {
                        _user.value = null
                    }
                }
                .addOnFailureListener{
                    _user.value = null
                }
        }
    }

    fun updateUserData(user: User) {
        val currentUID = auth.currentUser?.uid
        if (currentUID != null) {
            db.collection("users").document(currentUID)
                .set(user)
                .addOnSuccessListener {
                    _user.value = user
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }
}