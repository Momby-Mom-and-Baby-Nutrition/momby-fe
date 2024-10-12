package com.example.momby

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    val showLoading = mutableStateOf(false)
    val currentRoute = mutableStateOf("splash")
    val showBottomBar = mutableStateOf(false)
}