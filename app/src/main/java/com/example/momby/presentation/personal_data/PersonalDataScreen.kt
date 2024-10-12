package com.example.momby.presentation.personal_data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.HintDatePicker
import com.example.momby.component.HintTextField
import com.example.momby.component.RegisterBottomSheet
import com.example.momby.model.User
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.DisablePink
import kotlinx.coroutines.launch
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(
    navController: NavController
) {
    var name by remember {
        mutableStateOf("")
    }

    var date by remember {
        mutableStateOf<Date?>(null)
    }

    var enableButton by remember {
        mutableStateOf(false)
    }
    if (name.isNotEmpty() && date != null) {
        enableButton = true
    } else {
        enableButton = false
    }
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()


    val viewModel = hiltViewModel<PersonalDataViewModel>()

    val isRegisterSuccessful by viewModel.isRegisterSuccessful.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(48.dp))
        AsyncImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            model = R.drawable.vector_personal_data,
            contentDescription = "Personal Data Vector"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 56.dp, topEnd = 56.dp))
                .background(DisablePink.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(horizontal = 48.dp, vertical = 36.dp)) {
                Text(
                    text = "Selamat datang di Momby",
                    style = MaterialTheme.typography.titleMedium,
                    color = DarkPink
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Masukkan data Anda sekarang dan bersiaplah untuk kejutan menarik yang disediakan Momby untuk Anda!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Nama Lengkap",
                    style = MaterialTheme.typography.titleSmall,
                    color = DarkPink
                )
                Spacer(modifier = Modifier.height(6.dp))
                HintTextField(
                    hint = "input your full name",
                    onValueChange = { name = it },
                    value = name
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Tanggal Lahir",
                    style = MaterialTheme.typography.titleSmall,
                    color = DarkPink
                )
                Spacer(modifier = Modifier.height(6.dp))
                HintDatePicker(
                    hint = "input your birthday",
                    onValueChange = { date = it },
                    value = date
                )

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    enabled = enableButton,
                    onClick = {
                        val user = User(
                            name = name,
                            birthDate = date
                        )
                        viewModel.saveUserData(user) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkPink,
                        contentColor = Color.White,
                        disabledContainerColor = DisablePink,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Confirm",
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.titleSmall
                    )

                }
            }
        }

        if (isRegisterSuccessful) {
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch { bottomSheetState.hide() }
                },
                sheetState = bottomSheetState
            ) {
                RegisterBottomSheet(onClick = {
                    navController.navigate("beranda")
                })
            }
        }
    }
}