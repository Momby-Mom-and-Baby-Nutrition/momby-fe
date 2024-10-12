package com.example.momby.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    val viewModel = hiltViewModel<SplashViewModel>()
    val isLogin = viewModel.isLogin.collectAsState()

    LaunchedEffect(Unit) {
        delay(4000)
        if (isLogin.value){
            navController.navigate("beranda")
        } else{
            navController.navigate("on_boarding")

        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AsyncImage(model = R.drawable.splash_logo, contentDescription = "Logo Icon")
    }
}