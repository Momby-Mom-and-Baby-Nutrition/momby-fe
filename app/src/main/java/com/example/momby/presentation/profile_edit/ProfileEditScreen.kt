package com.example.momby.presentation.profile_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.HintTextField
import com.example.momby.component.NumberField
import com.example.momby.component.StepIndicator
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.DisablePink
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(navController: NavController) {
    val viewModel = hiltViewModel<ProfileEditViewModel>()
    val user = viewModel.user.collectAsState()

    var currentStep by remember {
        mutableStateOf(1)
    }
    var fullName by remember {
        mutableStateOf(user.value?.name.toString())
    }
    var weightBefore by remember {
        mutableStateOf(user.value?.weightBefore.toString())
    }
    var weightAfter by remember {
        mutableStateOf(user.value?.weightAfter.toString())
    }
    var height by remember {
        mutableStateOf(user.value?.height.toString())
    }
    var age by remember {
        mutableStateOf(user.value?.age.toString())
    }
    var gestatAge by remember {
        mutableStateOf(user.value?.gestatAge.toString())
    }
    var isCheckedYes by remember {
        mutableStateOf(false)
    }
    var isCheckedNo by remember {
        mutableStateOf(false)
    }
    var isDataSaveSuccess by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(user.value) {
        fullName = user.value?.name ?: ""
        weightBefore = user.value?.weightBefore?.toString() ?: ""
        weightAfter = user.value?.weightAfter?.toString() ?: ""
        height = user.value?.height?.toString() ?: ""
        age = user.value?.age?.toString() ?: ""
        gestatAge = user.value?.gestatAge?.toString() ?: ""
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 64.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        // Button Next
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            onClick = {
                if (currentStep < 7) currentStep++
                else {
                    val updateUser = user.value?.copy(
                        name = fullName,
                        weightBefore = weightBefore.toDouble(),
                        weightAfter = weightAfter.toDouble(),
                        height = height.toDouble(),
                        age = age.toInt(),
                        gestatAge = gestatAge.toInt()
                    )
                    updateUser?.let { viewModel.updateUserData(it) }

                    isDataSaveSuccess = !isDataSaveSuccess
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkPink,
                contentColor = Color.White,
                disabledContainerColor = DisablePink,
                disabledContentColor = Color.White
            ),

            shape = RoundedCornerShape(50),
            enabled = when (currentStep) {
                1 -> fullName.isNotEmpty()
                2 -> weightBefore.isNotEmpty()
                3 -> weightAfter.isNotEmpty()
                4 -> height.isNotEmpty()
                5 -> age.isNotEmpty()
                6 -> gestatAge.isNotEmpty()
                7 -> isCheckedNo == true || isCheckedYes == true
                else -> true
            }
        ) {
            Text(text = if (currentStep < 7) "Next" else "Finish", fontSize = 16.sp)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        StepIndicator(currentStep)

        Spacer(modifier = Modifier.height(24.dp))


        when (currentStep) {
            1 -> {
                Text(
                    text = "Identitas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkPink,
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Masukkan nama lengkap anda",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    HintTextField(
                        hint = "Nama lengkap anda",
                        onValueChange = { fullName = it },
                        value = fullName
                    )
                }
            }

            2 -> {
                Row {
                    Icon(
                        modifier = Modifier.clickable { currentStep-- },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Step",
                        tint = DarkPink
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Berat badan sebelum hamil",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DarkPink,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "Harap masukkan berat badan sebelum hamil untuk membantu kami memberikan rencana kesehatan dan nutrisi yang lebih akurat selama perjalanan kehamilan Anda.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    NumberField(
                        value = weightBefore,
                        unit = "Kilogram",
                        onValueChange = { weightBefore = it })
                }
            }

            3 -> {
                Row {
                    Icon(
                        modifier = Modifier.clickable { currentStep-- },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Step",
                        tint = DarkPink
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Berat badan saat ini",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DarkPink,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "Harap masukkan berat badan saat ini untuk membantu kami memberikan rencana kesehatan dan nutrisi yang lebih akurat selama perjalanan kehamilan Anda.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    NumberField(
                        value = weightAfter,
                        unit = "Kilogram",
                        onValueChange = { weightAfter = it })
                }
            }

            4 -> {
                Row {
                    Icon(
                        modifier = Modifier.clickable { currentStep-- },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Step",
                        tint = DarkPink
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Tinggi badan",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DarkPink,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "Harap masukkan tinggi badan anda saat ini.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    NumberField(
                        value = height,
                        unit = "Centimeter",
                        onValueChange = { height = it })
                }
            }

            5 -> {
                Row {
                    Icon(
                        modifier = Modifier.clickable { currentStep-- },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Step",
                        tint = DarkPink
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Umur",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DarkPink,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "Harap masukkan umur anda saat ini.\n" +
                            "contoh: jika anda berusia 23 tahun, isikan 23.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    NumberField(value = age, unit = "Tahun", onValueChange = { age = it })
                }
            }

            6 -> {
                Row {
                    Icon(
                        modifier = Modifier.clickable { currentStep-- },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Step",
                        tint = DarkPink
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Usia Kehamilan",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DarkPink,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "Harap masukkan usia kehamilan anda saat ini.\n" +
                            "contoh: jika usia kehamilan anda 2 bulan, isikan 8 minggu.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    NumberField(
                        value = gestatAge,
                        unit = "Minggu",
                        onValueChange = { gestatAge = it })
                }
            }

            7 -> {
                Row {
                    Icon(
                        modifier = Modifier.clickable { currentStep-- },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Step",
                        tint = DarkPink
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Aktivitas fisik",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DarkPink,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))


                Text(
                    text = "Saya suka berolahraga dengan aktivitas fisik: jalan cepat, lari, senam aerobik, mendaki, menari, karate, bermain bola besar, berenang, bersepeda (>16 km/jam)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkPink,
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isCheckedYes,
                        onCheckedChange = {
                            isCheckedYes = it
                            if (it) isCheckedNo = false
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkPink,
                            uncheckedColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "YA", style = MaterialTheme.typography.bodyLarge, color = DarkPink)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isCheckedNo,
                        onCheckedChange = {
                            isCheckedNo = it
                            if (it) isCheckedYes = false
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkPink,
                            uncheckedColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "TIDAK",
                        style = MaterialTheme.typography.bodyLarge,
                        color = DarkPink
                    )
                }
            }
        }
    }

    if (isDataSaveSuccess) {
        ModalBottomSheet(
            onDismissRequest = {
                coroutineScope.launch { bottomSheetState.hide() }
                isDataSaveSuccess = false
            },
            sheetState = bottomSheetState
        ) {
            AsyncImage(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                model = R.drawable.vector_save_data,
                contentDescription = "Register Successful"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Data anda sudah tersimpan",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = DarkPink
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Silahkan untuk klik  tombol Generate untuk mendapatkan rekomendasi menu ",
                style = MaterialTheme.typography.bodyLarge,
                color = DarkPink,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch { bottomSheetState.hide() }
                    isDataSaveSuccess = false
                    navController.navigate("beranda")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink
                )
            ) {
                Text("Beranda")
            }

            Spacer(modifier = Modifier.height(64.dp))

        }
    }
}