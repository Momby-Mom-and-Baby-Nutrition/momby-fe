package com.example.momby.presentation.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.CustomReactiveButton
import com.example.momby.component.HintPasswordField
import com.example.momby.component.HintTextField
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.DisablePink
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LoginScreen(
    navController: NavController,
) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }


    var enableButton by remember {
        mutableStateOf(false)
    }

    if (email.isNotEmpty() && password.isNotEmpty()) {
        enableButton = true
    } else {
        enableButton = false
    }

    val viewModel = hiltViewModel<LoginViewModel>()
    val loginState by viewModel.loginState.collectAsState()
    var isStayLogin = viewModel.isStayLogin.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val signInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                println("Nilai dari result data: " + result.data)
                viewModel.handleSignInResult(data)
            } else{
                println("Nilai dari result data: KOSONG" )
            }
        }


    Box(modifier = Modifier.fillMaxSize()) {
        when (loginState) {
            is LoginState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkGrey.copy(0.4f)), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                }

            }

            is LoginState.Success -> {
                LaunchedEffect(Unit) {
                    delay(2500)
                    navController.navigate("beranda")
                }
            }

            is LoginState.Error -> {
                val errorMessage = (loginState as LoginState.Error).message
                AlertDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.resetLoginState() },
                            colors = ButtonDefaults.buttonColors(
                                DarkPink
                            )
                        ) {
                            Text("OK")
                        }
                    },
                    title = {
                        Text(text = "Error")
                    },
                    text = {
                        Text(text = errorMessage ?: "Unknown error occurred.")
                    }
                )
            }

            else -> {
                //
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier.size(120.dp),
            model = R.drawable.logo2,
            contentDescription = "Top Logo"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Login",
            style = MaterialTheme.typography.titleMedium,
            color = DarkPink
        )
        Text(
            modifier = Modifier.fillMaxWidth(), text = "Tolong masukkan email dan kata sandi!",
            style = MaterialTheme.typography.bodyLarge,
            color = DarkPink
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Email",
            style = MaterialTheme.typography.titleSmall,
            color = DarkPink
        )
        Spacer(modifier = Modifier.height(6.dp))
        HintTextField(hint = "Input your email", onValueChange = { email = it }, value = email)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Password",
            style = MaterialTheme.typography.titleSmall,
            color = DarkPink
        )
        Spacer(modifier = Modifier.height(6.dp))
        HintPasswordField(
            hint = "Input your password",
            onValueChange = { password = it },
            value = password
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isStayLogin.value, onCheckedChange = {
                    viewModel.setStayLogin(!isStayLogin.value)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkPink
                )
            )

            Text(
                modifier = Modifier.weight(1f),
                text = "Remember me",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.clickable {
                    navController.navigate("forgot_password")
                },
                text = "Forgot password?",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = DarkPink
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomReactiveButton(modifier = Modifier, onClick = {
            viewModel.loginWithEmail(email, password)
            if (isStayLogin.value) {
                coroutineScope.launch {
                    viewModel.saveUID()
                }
            }
        }, text = "Login", isEnable = enableButton)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f), thickness = 1.dp)
            Text(text = "     or     ")
            Divider(modifier = Modifier.weight(1f), thickness = 1.dp)

        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            border = BorderStroke(2.dp, DarkPink),
            onClick = {
                val signInIntent = viewModel.getSignInIntent()
                signInLauncher.launch(signInIntent)

            }) {
            AsyncImage(
                modifier = Modifier.size(24.dp),
                model = R.drawable.google_logo,
                contentDescription = "Google Logo Button"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Login with Google",
                style = MaterialTheme.typography.titleSmall,
                color = DarkPink
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Don’t have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.clickable {
                    navController.navigate("register")
                },
                text = "Register",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = DarkPink,
            )
        }
    }
}