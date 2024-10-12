package com.example.momby.presentation.splash

import androidx.datastore.core.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore:DataStore<Preferences>,
    private val auth: FirebaseAuth
):ViewModel() {
    private val _isLogin = MutableStateFlow(false)
    val isLogin: StateFlow<Boolean> = _isLogin

    init {
        viewModelScope.launch {
            checkLogin().collect{
                _isLogin.value = it
            }
        }
    }

    private val UID = stringPreferencesKey("uid")

    private fun checkLogin(): Flow<Boolean> {
        return dataStore.data.map {
            it[UID]?.isNotEmpty() ?: false
        }
    }
}