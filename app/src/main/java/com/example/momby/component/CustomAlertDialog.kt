package com.example.momby.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkPink

@Composable
fun CustomAlertDialog(onDismissRequest: () -> Unit, onConfirmClick: () -> Unit) {
    AlertDialog(onDismissRequest = onDismissRequest, confirmButton = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Isi Data anda",
                style = MaterialTheme.typography.titleLarge,
                color = DarkPink,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description
            Text(
                text = "Anda perlu mengisi data untuk menikmati fitur",
                style = MaterialTheme.typography.bodyLarge,
                color = DarkPink,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Confirm Button
            Button(
                onClick = onConfirmClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPink,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Isi Data",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    })
}