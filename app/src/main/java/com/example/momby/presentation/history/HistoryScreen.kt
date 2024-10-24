package com.example.momby.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import com.example.momby.component.MenuMakanItem
import com.example.momby.model.History
import com.example.momby.presentation.homepage.HomePageViewModel
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightPink

@Composable
fun History(navController: NavController) {
    val viewModel = hiltViewModel<HistoryViewModel>()

    var historyList = viewModel.historyList.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val historyIndex = viewModel.historyIndex.collectAsState()
    val currentHistory = viewModel.currentHistory.collectAsState()
    val isHistoryEmpty = viewModel.isHistoryEmpty.collectAsState()



    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Loading...", style = MaterialTheme.typography.titleMedium)
        }
        return
    }
    //JIKA HISTORY KOSONG
    if (historyList.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .aspectRatio(1f)
                        .padding(12.dp),
                    model = R.drawable.ic_no_history,
                    contentDescription = "No Histy Icon",
                )
                Text(
                    text = "Tidak ada histori",
                    style = MaterialTheme.typography.titleMedium,
                    color = DarkGrey
                )
            }
        }
    }
    //JIKA HISTORY TIDAK KOSONG

    if (historyList.value.isNotEmpty()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)) {
                        if (historyIndex.value != 0) {
                            Icon(
                                modifier = Modifier.clickable {
                                                              viewModel.prevHistory()
                                },
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Arrow Back",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = viewModel.formatDate(currentHistory.value?.date),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        if (historyIndex.value != (historyList.value.size - 1)) {
                            Icon(
                                modifier = Modifier.clickable {
                                    viewModel.nextHistory()
                                },
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Arrow Back",
                                tint = Color.White
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    //makan pertama
                    BoxJamMakan(jam = "Sarapan")
                    if (currentHistory.value?.menuOptimized?.MakanPagi?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanPagi!!.makanan1,
                            isDone = currentHistory.value?.menuOptimized?.MakanPagi?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                //TIDAK BISA MENGUBAH NILAI DARI HISTORY
                            })
                    }
                    if (currentHistory.value?.menuOptimized?.MakanPagi?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanPagi!!.makanan2,
                            isDone = currentHistory.value?.menuOptimized?.MakanPagi?.makanan2IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                            })
                    }
                    if (currentHistory.value?.menuOptimized?.MakanPagi?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanPagi!!.makanan3,
                            isDone = currentHistory.value?.menuOptimized?.MakanPagi?.makanan3IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                            })
                    }

                    //makan kedua
                    BoxJamMakan(jam = "Snack Pagi")
                    MenuMakanItem(
                        menu = currentHistory.value?.menuOptimized?.SnackPagi?.snack!!,
                        isDone = currentHistory.value?.menuOptimized?.SnackPagi?.snackIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->

                        })

                    //makan ketiga
                    BoxJamMakan(jam = "Makan Siang")
                    if (currentHistory.value?.menuOptimized?.MakanSiang?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanSiang!!.makanan1,
                            isDone = currentHistory.value?.menuOptimized?.MakanSiang?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                            })
                    }
                    if (currentHistory.value?.menuOptimized?.MakanSiang?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanSiang!!.makanan2,
                            isDone = currentHistory.value?.menuOptimized?.MakanSiang?.makanan2IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->

                            })
                    }
                    if (currentHistory.value?.menuOptimized?.MakanSiang?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanSiang!!.makanan3,
                            isDone = currentHistory.value?.menuOptimized?.MakanSiang?.makanan3IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->

                            })
                    }


                    //makan keempat
                    BoxJamMakan(jam = "Snack Sore")
                    MenuMakanItem(
                        menu = currentHistory.value?.menuOptimized?.SnackSore?.snack!!,
                        isDone = currentHistory.value?.menuOptimized?.SnackSore?.snackIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->

                        })

                    //makan kelimaa
                    BoxJamMakan(jam = "Makan Malam")
                    if (currentHistory.value?.menuOptimized?.MakanMalam?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanMalam!!.makanan1,
                            isDone = currentHistory.value?.menuOptimized?.MakanMalam?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->

                            })
                    }
                    if (currentHistory.value?.menuOptimized?.MakanMalam?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanMalam!!.makanan2,
                            isDone = currentHistory.value?.menuOptimized?.MakanMalam?.makanan2IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                            })
                    }
                    if (currentHistory.value?.menuOptimized?.MakanMalam?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.value?.menuOptimized?.MakanMalam!!.makanan3,
                            isDone = currentHistory.value?.menuOptimized?.MakanMalam?.makanan3IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
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
                text = "Kalori\t\t\t\t\t\t: " + currentHistory.value?.nutrisiMenu?.teeKalori?.toInt()+ " kalori",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Karbohidrat\t\t: " + currentHistory.value?.nutrisiMenu?.karbohidrat?.toInt() + " gram",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Protein\t\t\t\t\t: " + currentHistory.value?.nutrisiMenu?.protein?.toInt() + " gram",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Lemak\t\t\t\t\t\t: " + currentHistory.value?.nutrisiMenu?.lemak?.toInt() + " gram",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink
            )
            Spacer(modifier = Modifier.height(24.dp))

        }
    }

}