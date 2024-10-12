package com.example.momby.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.ui.theme.DarkPink

@Composable
fun NoMenuCard(
    onClick:()-> Unit
){
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Menu hari ini",
                style = MaterialTheme.typography.titleMedium,
                color = DarkPink,
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.height(36.dp))
            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                model = R.drawable.vector_no_menu, contentDescription = "No Menu Vector"
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Tidak ada menu",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleSmall, color = DarkPink,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Tekan tombol dibawah untuk membuat menu harian secara otomatis",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
                color = DarkPink,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    CustomButton(modifier = Modifier,text = "Generate", onClick = onClick)
}