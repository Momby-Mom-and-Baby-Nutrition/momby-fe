package com.example.momby.presentation.homepage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.momby.data.model.KebutuhanNutrisi
import com.example.momby.data.model.MenuOptimized
import com.example.momby.data.model.NutrisiMenu
import com.example.momby.data.model.request.OptimizeMenuRequest
import com.example.momby.data.model.response.OptimizeMenuResponse
import com.example.momby.data.remote.ApiService
import com.example.momby.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db:FirebaseFirestore,
    private val apiService: ApiService
):ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState:StateFlow<User?> = _userState

    private val _isDataEmpty = MutableStateFlow<Boolean>(false)
    val isDataEmpty:StateFlow<Boolean> = _isDataEmpty

    private val _responseData = MutableStateFlow<OptimizeMenuResponse?>(null)

    private val _menu = MutableStateFlow<MenuOptimized?>(null)
    var menu: StateFlow<MenuOptimized?> = _menu

    private val _nutritionMenu = MutableStateFlow<NutrisiMenu?>(null)
    var nutritionMenu: StateFlow<NutrisiMenu?> = _nutritionMenu

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
        if (_userState.value?.age == 0 && _userState.value?.age == 0){
            println("Kondisi CheckData: 1")
            _isDataEmpty.value = true
        } else{
            _isDataEmpty.value = false
            println("Kondisi CheckData: 2")
        }
    }
    fun updateUserData(user: User) {
        val currentUID = auth.currentUser?.uid
        if (currentUID != null) {
            db.collection("users").document(currentUID)
                .set(user)
                .addOnSuccessListener {
                    _userState.value = user
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }
    fun getMenu(request:OptimizeMenuRequest){
        viewModelScope.launch {
            try {
                _responseData.value = apiService.getMenu(request)
                _menu.value = _responseData.value?.menu_optimized

                val updatedUser = userState.value?.copy(
                    calorieNeeds = _responseData.value?.nutritional_needs?.teeKalori?.toInt(),
                    proteinNeeds = _responseData.value?.nutritional_needs?.protein?.toInt(),
                    carbNeeds = _responseData.value?.nutritional_needs?.karbohidrat?.toInt(),
                    fatNeeds = _responseData.value?.nutritional_needs?.lemak?.toInt()
                )

                if (updatedUser != null) {
                    updateUserData(updatedUser)
                }

                _nutritionMenu.value = _responseData.value?.nutritional_get

                println("Response Data: ${_responseData.value}")
                println("Makanan Pokok Pagi: " + _menu.value?.MakanPagi?.makananPokok)
                println("Sumber Hewani Pagi: " + _menu.value?.MakanPagi?.sumberHewani)
                println("Sumber Nabati Pagi: " + _menu.value?.MakanPagi?.sumberNabati)
                println("Sayuran Pagi: " + _menu.value?.MakanPagi?.sayuran)
                println("Pelengkap Pagi: " + _menu.value?.MakanPagi?.pelengkap)
            }catch (e:Exception){
                println("Error bro: " + e)
            }
        }
    }

    fun updateMenu(updatedMenu: MenuOptimized) {
        _menu.value = updatedMenu
    }
}