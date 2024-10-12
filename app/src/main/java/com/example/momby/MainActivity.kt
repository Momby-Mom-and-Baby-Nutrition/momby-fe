package com.example.momby

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.momby.presentation.forgot_password.ForgotPasswordScreen
import com.example.momby.presentation.history.History
import com.example.momby.presentation.homepage.HomePageScreen
import com.example.momby.presentation.login.LoginScreen
import com.example.momby.presentation.onboarding.OnBoardingScreen
import com.example.momby.presentation.personal_data.PersonalDataScreen
import com.example.momby.presentation.profile.ProfileScreen
import com.example.momby.presentation.profile_edit.ProfileEditScreen
import com.example.momby.presentation.register.RegisterScreen
import com.example.momby.presentation.splash.SplashScreen
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.MombyTheme
import dagger.hilt.android.AndroidEntryPoint

lateinit var navController: NavHostController
lateinit var mainViewModel: MainViewModel
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val _mainViewModel by viewModels<MainViewModel>()
        mainViewModel = _mainViewModel
        setContent {
            navController = rememberNavController()

            //BOTTOM NAVBAR
            navController.addOnDestinationChangedListener { _, destination, _ ->
                destination.route?.let {
                    mainViewModel.currentRoute.value = it

                    when (it) {
                        "beranda", "history", "profile" -> mainViewModel.showBottomBar.value =
                            true

                        else -> mainViewModel.showBottomBar.value = false
                    }
                }
            }





            MombyTheme {
                Scaffold(
                    bottomBar = {
                        if (mainViewModel.showBottomBar.value){
                            BottomAppBar {
                                Row {
                                    //item 1
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f)
                                            .clickable {
                                                navController.navigate("beranda")
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    )
                                    {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.nav_home_page),
                                            contentDescription = "Icon Beranda Bottom Bar",
                                            tint = if (mainViewModel.currentRoute.value == "beranda") DarkPink else DarkGrey
                                        )
                                        Text(
                                            text = "Beranda",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (mainViewModel.currentRoute.value == "beranda") DarkPink else DarkGrey
                                        )
                                    }

                                    //item 2
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f)
                                            .clickable {
                                                navController.navigate("history")
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    )
                                    {
                                        Icon(modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.nav_history),
                                            contentDescription = "Icon History Bottom Bar",
                                            tint = if (mainViewModel.currentRoute.value == "history") DarkPink else DarkGrey
                                        )
                                        Text(
                                            text = "History",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (mainViewModel.currentRoute.value == "history") DarkPink else DarkGrey
                                        )
                                    }

                                    //item 3
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f)
                                            .clickable {
                                                navController.navigate("profile")
                                            },
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    )
                                    {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.nav_profile),
                                            contentDescription = "Icon Profile Bottom Bar",
                                            tint = if (mainViewModel.currentRoute.value == "profile") DarkPink else DarkGrey
                                        )
                                        Text(
                                            text = "Profile",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (mainViewModel.currentRoute.value == "profile") DarkPink else DarkGrey
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        composable("splash") { 
                            SplashScreen(navController = navController)
                        }
                        composable("beranda") { 
                            HomePageScreen(navController = navController)
                        }
                        composable("login"){
                            LoginScreen(navController = navController)
                        }
                        composable("register") {
                            RegisterScreen(navController = navController)
                        }
                        composable("on_boarding") {
                            OnBoardingScreen(navController = navController)
                        }
                        composable("personal_data"){
                            PersonalDataScreen(navController = navController)
                        }
                        composable("forgot_password"){
                            ForgotPasswordScreen(navController = navController)
                        }
                        composable("profile_edit"){
                            ProfileEditScreen(navController = navController)
                        }
                        composable("history"){
                            History(navController = navController)
                        }
                        composable("profile"){
                            ProfileScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}