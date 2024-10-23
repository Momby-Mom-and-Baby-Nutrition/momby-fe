package com.example.momby.presentation.history

import androidx.compose.foundation.background
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
//
//
//    var historyIndex by remember {
//        mutableStateOf(0)
//    }
//
//
//    var currentHistory by remember {
//        mutableStateOf(History(menuOptimized = null, date = null)) // Assuming History() is a valid empty constructor
//    }
//
//    LaunchedEffect(isLoading.value) {
//        historyIndex = historyList.value.size - 1
//        currentHistory = historyList.value[historyIndex]!!
//        println("NILAI HISTORY INDEX: " + historyIndex)
//    }



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
    /*
    if (currentHistory != null){
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
                        if (historyIndex != 0) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Arrow Back",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = viewModel.formatDate(currentHistory.date),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        if (historyIndex != (historyList.value.size - 1)) {
                            Icon(
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
                    if (currentHistory.menuOptimized?.MakanPagi?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = historyList.value[historyIndex]?.menuOptimized?.MakanPagi!!.makanan1,
                            isDone = historyList.value[historyIndex]?.menuOptimized?.MakanPagi?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                                //TIDAK BISA MENGUBAH NILAI DARI HISTORY
                            })
                    }
                    if (currentHistory.menuOptimized?.MakanPagi?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = historyList.value[historyIndex]?.menuOptimized?.MakanPagi!!.makanan2,
                            isDone = historyList.value[historyIndex]?.menuOptimized?.MakanPagi?.makanan2IsDone!!,
                            tipe = "Sumber Hewani",
                            onCheckedChange = { isChecked ->
                            })
                    }
                    if (currentHistory.menuOptimized?.MakanPagi?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = historyList.value[historyIndex]?.menuOptimized?.MakanPagi!!.makanan3,
                            isDone = historyList.value[historyIndex]?.menuOptimized?.MakanPagi?.makanan3IsDone!!,
                            tipe = "Sumber Nabati",
                            onCheckedChange = { isChecked ->
                            })
                    }

                    //makan kedua
                    BoxJamMakan(jam = "Snack Pagi")
                    MenuMakanItem(
                        menu = currentHistory.menuOptimized?.SnackPagi?.snack!!,
                        isDone = currentHistory.menuOptimized?.SnackPagi?.snackIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->

                        })

                    //makan ketiga
                    BoxJamMakan(jam = "Makan Siang")
                    if (currentHistory.menuOptimized?.MakanSiang?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.menuOptimized?.MakanSiang!!.makanan1,
                            isDone = currentHistory.menuOptimized?.MakanSiang?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->
                            })
                    }
                    if (currentHistory.menuOptimized?.MakanSiang?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.menuOptimized?.MakanSiang!!.makanan2,
                            isDone = currentHistory.menuOptimized?.MakanSiang?.makanan2IsDone!!,
                            tipe = "Sumber Hewani",
                            onCheckedChange = { isChecked ->

                            })
                    }
                    if (currentHistory.menuOptimized?.MakanSiang?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.menuOptimized?.MakanSiang!!.makanan3,
                            isDone = currentHistory.menuOptimized?.MakanSiang?.makanan3IsDone!!,
                            tipe = "Sumber Nabati",
                            onCheckedChange = { isChecked ->

                            })
                    }


                    //makan keempat
                    BoxJamMakan(jam = "Snack Sore")
                    MenuMakanItem(
                        menu = currentHistory.menuOptimized?.SnackSore?.snack!!,
                        isDone = currentHistory.menuOptimized?.SnackSore?.snackIsDone!!,
                        tipe = "Snack",
                        onCheckedChange = { isChecked ->

                        })

                    //makan kelimaa
                    BoxJamMakan(jam = "Makan Malam")
                    if (currentHistory.menuOptimized?.MakanMalam?.makanan1 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.menuOptimized?.MakanMalam!!.makanan1,
                            isDone = currentHistory.menuOptimized?.MakanMalam?.makanan1IsDone!!,
                            tipe = "Makanan Pokok",
                            onCheckedChange = { isChecked ->

                            })
                    }
                    if (currentHistory.menuOptimized?.MakanMalam?.makanan2 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.menuOptimized?.MakanMalam!!.makanan2,
                            isDone = currentHistory.menuOptimized?.MakanMalam?.makanan2IsDone!!,
                            tipe = "Sumber Hewani",
                            onCheckedChange = { isChecked ->
                            })
                    }
                    if (currentHistory.menuOptimized?.MakanMalam?.makanan3 != "Kosong") {
                        MenuMakanItem(
                            menu = currentHistory.menuOptimized?.MakanMalam!!.makanan3,
                            isDone = currentHistory.menuOptimized?.MakanMalam?.makanan3IsDone!!,
                            tipe = "Sumber Nabati",
                            onCheckedChange = { isChecked ->
                            })
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
     */
}