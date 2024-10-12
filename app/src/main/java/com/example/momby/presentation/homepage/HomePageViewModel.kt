package com.example.momby.presentation.homepage

import androidx.lifecycle.ViewModel
import com.example.momby.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db:FirebaseFirestore
):ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState:StateFlow<User?> = _userState

    private val _isDataEmpty = MutableStateFlow<Boolean>(false)
    val isDataEmpty:StateFlow<Boolean> = _isDataEmpty


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
                        _userState.value = user
                    } else {
                        _userState.value = null
                    }
                    CheckData()
                }
                .addOnFailureListener{
                    _userState.value = null
                }
        }
    }


    private fun CheckData(){
        //update check data dengan mengecek kebutuhan nutrisi
        if (_userState.value?.age == 0 && _userState.value?.age == 0){
            println("Kondisi CheckData: 1")
            _isDataEmpty.value = true
        } else{
            _isDataEmpty.value = false
            println("Kondisi CheckData: 2")

        }
    }
}