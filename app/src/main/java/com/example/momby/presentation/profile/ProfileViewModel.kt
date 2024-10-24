package com.example.momby.presentation.profile

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
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

    fun uploadImage(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val profilePicRef = storageRef.child("profile_pictures/$userId.jpg")

        profilePicRef.putFile(uri).addOnSuccessListener {
            profilePicRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                saveProfilePictureUrl(downloadUrl.toString())
                getUserData()
            }
        }.addOnFailureListener { exception ->
            println("Gagal mengupload gambar: ${exception.message}")
        }
    }

    private fun saveProfilePictureUrl(url: String) {
        val currentUID = auth.currentUser?.uid
        if (currentUID != null) {
            db.collection("users").document(currentUID)
                .update("profilePictureUrl", url)
                .addOnSuccessListener {
                    println("URL foto profil berhasil diperbarui")
                }
                .addOnFailureListener { e ->
                    println("Gagal memperbarui URL foto profil: ${e.message}")
                }
        }
    }

    fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "profile_picture_${System.currentTimeMillis()}.jpg")
        return try {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Simpan bitmap sebagai JPEG
            }
            Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


}