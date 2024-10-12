package com.example.momby.presentation.homepage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.component.BoxJamMakan
import com.example.momby.component.CustomAlertDialog
import com.example.momby.component.CustomButton
import com.example.momby.component.MenuMakanItem
import com.example.momby.component.NutritionalIndicators
import com.example.momby.component.RoundCheckbox
import com.example.momby.model.MenuMakan
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightPink

@Composable
fun HomePageScreen(
    navController: NavController
) {
    //bagian checker menu
    var isCheck by remember {
        mutableStateOf(
            false
        )
    }

    //(hapus) dummyData
    var menuDummy by remember {
        mutableStateOf(
            MenuMakan(
                id = 1,
                nama = "Sayur Geminx",
                desc = "Disajikan dengan nasi hangat",
                isDone = false
            )
        )
    }

    val viewModel = hiltViewModel<HomePageViewModel>()

    val user = viewModel.userState.collectAsState()
    val isDataEmpty = viewModel.isDataEmpty.collectAsState()
    var isShowAlert by remember {
        mutableStateOf(false)
    }
    println("isDataEmpty: " +isDataEmpty.value)

    if (isDataEmpty.value){
        println("isShowAlert: " +isShowAlert)

        CustomAlertDialog(
            onDismissRequest = {},
            onConfirmClick = {
                navController.navigate("profile_edit")
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                DarkPink,
                                LightPink
                            )
                        )
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Halo",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = user.value?.name + "\uD83D\uDC4B",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = R.drawable.profile_grey,
                                contentDescription = "Profile Icon Grey"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = DarkPink
                            ), onClick = {
                                navController.navigate("profile")
                            }) {
                                Text(
                                    text = "Ubah Profil",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Kebutuhan Harian",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            NutritionalIndicators(
                                nutrition = "Kalori",
                                amount = user.value?.calorieNeeds.toString(),
                                unit = "kalori"
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            NutritionalIndicators(
                                nutrition = "Karbohidrat",
                                amount = user.value?.carbNeeds.toString(),
                                unit = "gram"
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            NutritionalIndicators(
                                nutrition = "Lemak",
                                amount = user.value?.fatNeeds.toString(),
                                unit = "gram"
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            NutritionalIndicators(
                                nutrition = "Protein",
                                amount = user.value?.calorieNeeds.toString(),
                                unit = "gram"
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(24.dp))


        //MENU CARD
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(brush = Brush.verticalGradient(listOf(LightPink, DarkPink))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "MENU HARI INI",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                //makan pertama
                BoxJamMakan(jam = "Sarapan")
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })

                //makan kedua
                BoxJamMakan(jam = "Snack Pagi")
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })

                //makan ketiga
                BoxJamMakan(jam = "Makan Siang")
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })

                //makan keempat
                BoxJamMakan(jam = "Snack Sore")
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })


                //makan kelimaa
                BoxJamMakan(jam = "Makan Malam")
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
                MenuMakanItem(
                    menu = menuDummy,
                    onCheckedChange = { isChecked ->
                        menuDummy = menuDummy.copy(isDone = isChecked)
                    })
            }
        }

        Spacer(modifier = Modifier.height(64.dp))


    }

}