package com.example.momby.presentation.homepage

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.momby.component.NoMenuCard
import com.example.momby.component.NutritionalIndicators
import com.example.momby.data.model.request.OptimizeMenuRequest
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightPink

@Composable
fun HomePageScreen(
    navController: NavController
) {

    val viewModel = hiltViewModel<HomePageViewModel>()

    val user = viewModel.userState.collectAsState()
    val isDataEmpty = viewModel.isDataEmpty.collectAsState()
    var menu = viewModel.menu.collectAsState()
    var nutritionMenu = viewModel.nutritionMenu.collectAsState()
    var isLoading = viewModel.isLoading.collectAsState()
    if (isDataEmpty.value) {
        CustomAlertDialog(
            onDismissRequest = {},
            onConfirmClick = {
                navController.navigate("profile_edit")
            }
        )
    }

    LaunchedEffect(menu.value) {
        if (menu.value != null) {
            viewModel.updateMenuData(menu.value!!)
        }
    }

    //Loading Screen
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
                                modifier = Modifier.size(120.dp).clip(CircleShape),
                                model = if(user.value?.profilePictureUrl != "")user.value?.profilePictureUrl else R.drawable.profile_grey,
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

        if (menu.value != null) {
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
                    if (menu.value?.MakanPagi?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanPagi!!.makanan1,
                            isDone = menu.value?.MakanPagi?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanPagi =
                                    menu.value?.MakanPagi?.copy(makanan1IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                    if (menu.value?.MakanPagi?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanPagi!!.makanan2,
                            isDone = menu.value?.MakanPagi?.makanan2IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanPagi =
                                    menu.value?.MakanPagi?.copy(makanan2IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                    if (menu.value?.MakanPagi?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanPagi!!.makanan3,
                            isDone = menu.value?.MakanPagi?.makanan3IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanPagi =
                                    menu.value?.MakanPagi?.copy(makanan3IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }

                    //makan kedua
                    BoxJamMakan(jam = "Snack Pagi")
                    MenuMakanItem(
                        menu = menu.value?.SnackPagi?.snack!!,
                        isDone = menu.value?.SnackPagi?.snackIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->
                            val updateSnackPagi =
                                menu.value?.SnackPagi?.copy(snackIsDone = isChecked)
                            val updateMenu = menu.value?.copy(SnackPagi = updateSnackPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    //makan ketiga
                    BoxJamMakan(jam = "Makan Siang")
                    if (menu.value?.MakanSiang?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanSiang!!.makanan1,
                            isDone = menu.value?.MakanSiang?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanSiang =
                                    menu.value?.MakanSiang?.copy(makanan1IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                    if (menu.value?.MakanSiang?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanSiang!!.makanan2,
                            isDone = menu.value?.MakanSiang?.makanan2IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanSiang =
                                    menu.value?.MakanSiang?.copy(makanan2IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                    if (menu.value?.MakanSiang?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanSiang!!.makanan3,
                            isDone = menu.value?.MakanSiang?.makanan3IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanSiang =
                                    menu.value?.MakanSiang?.copy(makanan3IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }


                    //makan keempat
                    BoxJamMakan(jam = "Snack Sore")
                    MenuMakanItem(
                        menu = menu.value?.SnackSore?.snack!!,
                        isDone = menu.value?.SnackSore?.snackIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->
                            val updateSnackSore =
                                menu.value?.SnackSore?.copy(snackIsDone = isChecked)
                            val updateMenu = menu.value?.copy(SnackSore = updateSnackSore!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    //makan kelimaa
                    BoxJamMakan(jam = "Makan Malam")
                    if (menu.value?.MakanMalam?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanMalam!!.makanan1,
                            isDone = menu.value?.MakanMalam?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanMalam =
                                    menu.value?.MakanMalam?.copy(makanan1IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                    if (menu.value?.MakanMalam?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanMalam!!.makanan2,
                            isDone = menu.value?.MakanMalam?.makanan2IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanMalam =
                                    menu.value?.MakanMalam?.copy(makanan2IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                    if (menu.value?.MakanMalam?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = menu.value?.MakanMalam!!.makanan3,
                            isDone = menu.value?.MakanMalam?.makanan3IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                val updateMakanMalam =
                                    menu.value?.MakanMalam?.copy(makanan3IsDone = isChecked)
                                val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                                viewModel.updateMenu(updateMenu!!)
                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Nutrisi Dari Menu di Atas",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Kalori\t\t\t\t\t\t: " + nutritionMenu.value?.teeKalori?.toInt() + " kalori",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Karbohidrat\t\t: " + nutritionMenu.value?.karbohidrat?.toInt() + " gram",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Protein\t\t\t\t\t: " + nutritionMenu.value?.protein?.toInt() + " gram",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Lemak\t\t\t\t\t\t: " + nutritionMenu.value?.lemak?.toInt() + " gram",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
        } else {
            NoMenuCard {
                val request = OptimizeMenuRequest(
                    height = user.value?.height?.toFloat()!!,
                    weight = user.value?.weightAfter?.toFloat()!!,
                    age = user.value?.age!!,
                    activity = user.value?.activityLevel!!,
                    gestation = user.value?.gestatAge!!,
                )
                viewModel.getMenu(request)
            }
        }
    }
    if (isLoading.value){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGrey.copy(0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = DarkPink
            )
        }
    }
}
