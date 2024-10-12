package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkPink

@Composable
fun NutritionalIndicators(
    nutrition:String,
    amount:String,
    unit:String
){
    Row {
        Box(
            modifier = Modifier
                .width(90.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
        ) {
            Text(modifier = Modifier.padding(8.dp),
                text = nutrition,
                style = MaterialTheme.typography.bodySmall,
                color = DarkPink,
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
            , contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = if (amount == "0") "-" else "$amount $unit",
                style = MaterialTheme.typography.bodySmall,
                color = DarkPink,
                fontWeight = FontWeight.Bold
            )
        }
    }
}