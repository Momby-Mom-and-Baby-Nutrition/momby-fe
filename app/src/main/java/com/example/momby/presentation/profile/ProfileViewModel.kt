package com.example.momby.presentation.profile

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.momby.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _email = MutableStateFlow<String>("")
    val email:StateFlow<String> = _email

    private val UID = stringPreferencesKey("uid")


    init {
        getUserData()
        getEmailUser()
    }

    private fun getEmailUser() {
        val currentEmail = auth.currentUser?.email
        _email.value = currentEmail.toString()
    }

    private fun getUserData() {
        val currenUID = auth.currentUser?.uid
        if (currenUID != null) {
            db.collection("users").document(currenUID).get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    _user.value = user
                    println("Data berhasil diambil")
            }.addOnFailureListener{
                println("Data tidak berhasil diambil")
                }
        } else {
            println("UID Kosong")
        }
    }

    fun uploadProfilePicture(imageUri: Uri) {
        val currentUID = auth.currentUser?.uid
        if (currentUID != null) {
            val storageRef: StorageReference = storage.reference.child("profile_pictures/$currentUID.jpg")

            storageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        saveProfilePictureUrl(downloadUri.toString())
                    }.addOnFailureListener { e ->
                        println("Gagal mendapatkan URL: ${e.message}")
                    }
                }.addOnFailureListener { e ->
                    println("Upload foto gagal: ${e.message}")
                }
        } else {
            println("UID Kosong")
        }
    }

    private fun saveProfilePictureUrl(url: String) {
        val currentUID = auth.currentUser?.uid
        if (currentUID != null) {
            db.collection("users").document(currentUID)
                .update("profilePictureUrl", url)
                .addOnSuccessListener {
                    println("URL foto profil berhasil diperbarui")
                }.addOnFailureListener { e ->
                    println("Gagal memperbarui URL foto profil: ${e.message}")
                }
        }
    }


    suspend fun removeUID(){
        dataStore.edit {
            it.remove(UID)
        }
    }

    fun logout(){
        auth.signOut()
        viewModelScope.launch {
            removeUID()
        }
    }
}