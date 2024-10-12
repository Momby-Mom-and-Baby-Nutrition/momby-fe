package com.example.momby.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.momby.R
import com.example.momby.ui.theme.DarkPink
import kotlinx.coroutines.launch

@Composable
fun RegisterBottomSheet(
    onClick:()->Unit
){
    Column {
        AsyncImage(
            modifier= Modifier.align(Alignment.CenterHorizontally),
            model = R.drawable.vector_register_successful,
            contentDescription = "Register Successful"
        )
        Text(
            text = "Akun berhasil dibuat",
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = DarkPink
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Masuk ke akun anda!",
            style = MaterialTheme.typography.bodyLarge,
            color = DarkPink,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClick,
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
