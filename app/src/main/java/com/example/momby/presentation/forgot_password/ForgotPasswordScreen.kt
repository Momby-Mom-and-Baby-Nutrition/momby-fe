package com.example.momby.presentation.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.HintTextField
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.DisablePink
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ForgotPasswordScreen(
    navController: NavController
) {
    var email by remember {
        mutableStateOf("")
    }

    var enableButton by remember {
        mutableStateOf(false)
    }

    if (email.isNotEmpty()) {
        enableButton = true
    } else {
        enableButton = false
    }

    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val viewModel = hiltViewModel<ForgotPasswordViewModel>()
    var isResetSuccessful = viewModel.isSuccessful.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier.size(40.dp),
                tint = DarkPink,
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button"
            )
        }
        AsyncImage(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            model = R.drawable.vector_forgot_password,
            contentDescription = "Forgot Password Vector"
        )
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Lupa Kata Sandi",
            style = MaterialTheme.typography.titleMedium,
            color = DarkPink
        )
        Text(
            text = "Silakan masukkan alamat email untuk melanjutkan reset kata sandi",
            style = MaterialTheme.typography.bodyLarge,
            color = DarkPink
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = "Email", style = MaterialTheme.typography.titleSmall, color = DarkPink)
        HintTextField(hint = "Input your email", onValueChange = { email = it }, value = email)

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(),
            enabled = enableButton,
            onClick = {
                viewModel.forgotPassword(email)
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkPink,
                contentColor = Color.White,
                disabledContainerColor = DisablePink,
                disabledContentColor = Color.White
            )
        ) {
            Text(
                text = "Kirim",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleSmall
            )

        }
        if (isResetSuccessful.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { bottomSheetState.hide() }
                },
                sheetState = bottomSheetState
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Spacer(modifier = Modifier.height(12.dp))
                    AsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally),
                        model = R.drawable.vector_reset_password,
                        contentDescription = "Forgot Password Vector"
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Permintaan Atur Ulang Kata Sandi Berhasil!",
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = DarkPink
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Silahkan check email anda",
                        style = MaterialTheme.typography.bodyLarge,
                        color = DarkPink,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch { bottomSheetState.hide() }
                            navController.navigate("login")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 48.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkPink
                        )
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.height(64.dp))
                }


            }
        }
    }
}
