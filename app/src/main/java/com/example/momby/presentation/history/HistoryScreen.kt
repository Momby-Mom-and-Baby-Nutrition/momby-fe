package com.example.momby.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink

@Composable
fun History(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(0.4f)
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