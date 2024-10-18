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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightPink

@Composable
fun HomePageScreen(
    navController: NavController
) {

    val viewModel = hiltViewModel<HomePageViewModel>()

    val user = viewModel.userState.collectAsState()
    val isDataEmpty = viewModel.isDataEmpty.collectAsState()
    var isShowAlert by remember {
        mutableStateOf(false)
    }
    var menu = viewModel.menu.collectAsState()
    var nutritionMenu = viewModel.nutritionMenu.collectAsState()
    println("isDataEmpty: " + isDataEmpty.value)

    if (isDataEmpty.value) {
        println("isShowAlert: " + isShowAlert)

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
                    MenuMakanItem(
                        menu = menu.value?.MakanPagi!!.makananPokok,
                        isDone = menu.value?.MakanPagi?.makananPokokIsDone!!,
                        tipe = "Makanan Pokok",
                        onCheckedChange = { isChecked ->
                            val updateMakanPagi =
                                menu.value?.MakanPagi?.copy(makananPokokIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    MenuMakanItem(
                        menu = menu.value?.MakanPagi!!.sumberHewani,
                        isDone = menu.value?.MakanPagi?.sumberHewaniIsDone!!,
                        tipe = "Sumber Hewani",
                        onCheckedChange = { isChecked ->
                            val updateMakanPagi =
                                menu.value?.MakanPagi?.copy(sumberHewaniIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    MenuMakanItem(
                        menu = menu.value?.MakanPagi!!.sumberNabati,
                        isDone = menu.value?.MakanPagi?.sumberNabatiIsDone!!,
                        tipe = "Sumber Nabati",
                        onCheckedChange = { isChecked ->
                            val updateMakanPagi =
                                menu.value?.MakanPagi?.copy(sumberNabatiIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    MenuMakanItem(
                        menu = menu.value?.MakanPagi!!.sayuran,
                        isDone = menu.value?.MakanPagi?.sayuranIsDone!!,
                        tipe = "Sayuran",
                        onCheckedChange = { isChecked ->
                            val updateMakanPagi =
                                menu.value?.MakanPagi?.copy(sayuranIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    MenuMakanItem(
                        menu = menu.value?.MakanPagi!!.pelengkap,
                        isDone = menu.value?.MakanPagi?.pelengkapIsDone!!,
                        tipe = "Pelengkap",
                        onCheckedChange = { isChecked ->
                            val updateMakanPagi =
                                menu.value?.MakanPagi?.copy(pelengkapIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanPagi = updateMakanPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    //makan kedua
                    BoxJamMakan(jam = "Snack Pagi")
                    MenuMakanItem(
                        menu = menu.value?.SnackPagi?.pelengkap!!,
                        isDone = menu.value?.SnackPagi?.pelengkapIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->
                            val updateSnackPagi =
                                menu.value?.SnackPagi?.copy(pelengkapIsDone = isChecked)
                            val updateMenu = menu.value?.copy(SnackPagi = updateSnackPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    //makan ketiga
                    BoxJamMakan(jam = "Makan Siang")
                    MenuMakanItem(
                        menu = menu.value?.MakanSiang!!.makananPokok,
                        isDone = menu.value?.MakanSiang?.makananPokokIsDone!!,
                        tipe = "Makanan Pokok",
                        onCheckedChange = { isChecked ->
                            val updateMakanSiang =
                                menu.value?.MakanSiang?.copy(makananPokokIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    MenuMakanItem(
                        menu = menu.value?.MakanSiang!!.sumberHewani,
                        isDone = menu.value?.MakanSiang?.sumberHewaniIsDone!!,
                        tipe = "Sumber Hewani",
                        onCheckedChange = { isChecked ->
                            val updateMakanSiang =
                                menu.value?.MakanSiang?.copy(sumberHewaniIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    MenuMakanItem(
                        menu = menu.value?.MakanSiang!!.sumberNabati,
                        isDone = menu.value?.MakanSiang?.sumberNabatiIsDone!!,
                        tipe = "Sumber Nabati",
                        onCheckedChange = { isChecked ->
                            val updateMakanPagi =
                                menu.value?.MakanSiang?.copy(sumberNabatiIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanSiang = updateMakanPagi!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    MenuMakanItem(
                        menu = menu.value?.MakanSiang!!.sayuran,
                        isDone = menu.value?.MakanSiang?.sayuranIsDone!!,
                        tipe = "Sayuran",
                        onCheckedChange = { isChecked ->
                            val updateMakanSiang =
                                menu.value?.MakanSiang?.copy(sayuranIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    MenuMakanItem(
                        menu = menu.value?.MakanSiang!!.pelengkap,
                        isDone = menu.value?.MakanSiang?.pelengkapIsDone!!,
                        tipe = "Pelengkap",
                        onCheckedChange = { isChecked ->
                            val updateMakanSiang =
                                menu.value?.MakanSiang?.copy(pelengkapIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanSiang = updateMakanSiang!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    //makan keempat
                    BoxJamMakan(jam = "Snack Sore")
                    MenuMakanItem(
                        menu = menu.value?.SnackSore?.pelengkap!!,
                        isDone = menu.value?.SnackSore?.pelengkapIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->
                            val updateSnackSore =
                                menu.value?.SnackSore?.copy(pelengkapIsDone = isChecked)
                            val updateMenu = menu.value?.copy(SnackSore = updateSnackSore!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    //makan kelimaa
                    BoxJamMakan(jam = "Makan Malam")
                    MenuMakanItem(
                        menu = menu.value?.MakanMalam!!.makananPokok,
                        isDone = menu.value?.MakanMalam?.makananPokokIsDone!!,
                        tipe = "Makanan Pokok",
                        onCheckedChange = { isChecked ->
                            val updateMakanMalam =
                                menu.value?.MakanMalam?.copy(makananPokokIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    MenuMakanItem(
                        menu = menu.value?.MakanMalam!!.sumberHewani,
                        isDone = menu.value?.MakanMalam?.sumberHewaniIsDone!!,
                        tipe = "Sumber Hewani",
                        onCheckedChange = { isChecked ->
                            val updateMakanMalam =
                                menu.value?.MakanMalam?.copy(sumberHewaniIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    MenuMakanItem(
                        menu = menu.value?.MakanMalam!!.sumberNabati,
                        isDone = menu.value?.MakanMalam?.sumberNabatiIsDone!!,
                        tipe = "Sumber Nabati",
                        onCheckedChange = { isChecked ->
                            val updateMakanMalam =
                                menu.value?.MakanMalam?.copy(sumberNabatiIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                            viewModel.updateMenu(updateMenu!!)
                        })

                    MenuMakanItem(
                        menu = menu.value?.MakanMalam!!.sayuran,
                        isDone = menu.value?.MakanMalam?.sayuranIsDone!!,
                        tipe = "Sayuran",
                        onCheckedChange = { isChecked ->
                            val updateMakanMalam =
                                menu.value?.MakanMalam?.copy(sayuranIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
                    MenuMakanItem(
                        menu = menu.value?.MakanMalam!!.pelengkap,
                        isDone = menu.value?.MakanMalam?.pelengkapIsDone!!,
                        tipe = "Pelengkap",
                        onCheckedChange = { isChecked ->
                            val updateMakanMalam =
                                menu.value?.MakanMalam?.copy(pelengkapIsDone = isChecked)
                            val updateMenu = menu.value?.copy(MakanMalam = updateMakanMalam!!)
                            viewModel.updateMenu(updateMenu!!)
                        })
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
                text = "Kalori\t\t\t\t\t\t: " + nutritionMenu.value?.teeKalori?.toInt()+ " kalori",
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
                    activity = 2,
                    gestation = user.value?.gestatAge!!
                )
                viewModel.getMenu(request)
            }
        }
    }
}