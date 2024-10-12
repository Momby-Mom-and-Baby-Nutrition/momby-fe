package com.example.momby.presentation.register


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.momby.ui.theme.LightPink
import com.example.momby.ui.theme.poppinsFontFamily

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confPassword by remember {
        mutableStateOf("")
    }
    var agree by remember {
        mutableStateOf(false)
    }
    var agreementIcon by remember {
        mutableStateOf(R.drawable.check_disable)
    }
    if (agree) {
        agreementIcon = R.drawable.check_active
    } else {
        agreementIcon = R.drawable.check_disable
    }

    var enableButton by remember {
        mutableStateOf(false)
    }
    if (email.isNotEmpty() && password.isNotEmpty() && confPassword.isNotEmpty() && agree) {
        enableButton = true
    } else {
        enableButton = false
    }

    val viewModel = hiltViewModel<RegisterViewModel>()
    val registerState by viewModel.registerState.collectAsState()
    Box(modifier = Modifier.fillMaxSize()){
        when (registerState) {
            is RegisterState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().background(DarkGrey.copy(alpha = 0.4f)), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(64.dp))
                }
            }
            is RegisterState.Success -> {
                Text("Success: ${(registerState as RegisterState.Success).message}")
                // Navigate to personal data screen or login screen after successful registration
                navController.navigate("personal_data")
            }
            is RegisterState.Error -> {
                Text("Error: ${(registerState as RegisterState.Error).error}")
            }
            else -> { //
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
            text = "Register",
            style = MaterialTheme.typography.titleMedium,
            color = DarkPink
        )
        Text(
            modifier = Modifier.fillMaxWidth(), text = "Pertama, mari buat akun anda",
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

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Confirm",
            style = MaterialTheme.typography.titleSmall,
            color = DarkPink
        )
        Spacer(modifier = Modifier.height(6.dp))
        HintPasswordField(
            hint = "Input your password again",
            onValueChange = { confPassword = it },
            value = confPassword
        )


        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { agree = !agree },
                model = agreementIcon,
                contentDescription = "Agreement Check Box"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "I agree with the ",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.clickable { /*DO SOMETHING*/ }, text = "terms of service",
                fontFamily = poppinsFontFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = DarkPink

            )
            Text(
                text = " and ",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier.clickable { /*DO SOMETHING*/ }, text = "privacy policy",
                fontFamily = poppinsFontFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = DarkPink
            )
        }

        Spacer(modifier = Modifier.height(12.dp))



        CustomReactiveButton(modifier = Modifier,
            onClick = {
                viewModel.registerWithEmail(email,password,confPassword)
            },
            text = "Register",
            isEnable = enableButton)


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
            onClick = { /*TODO*/ }) {
            AsyncImage(
                modifier = Modifier.size(24.dp),
                model = R.drawable.google_logo,
                contentDescription = "Google Logo Button"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Register with Google",
                style = MaterialTheme.typography.titleSmall,
                color = DarkPink
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.clickable {
                    navController.navigate("login")
                },
                text = "Login",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = DarkPink,
            )
        }

    }
}