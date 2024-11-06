package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.momby.R
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightPink

@Composable
fun BoxJamMakan(
    jam: String,
    ic: Painter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(DarkPink, LightPink)
                )
            ), contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = ic,
                contentDescription = "",
                tint = Color.White
            )
            Text(
                text = jam,
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Icon(
                modifier = Modifier.size(36.dp),
                painter = ic,
                contentDescription = "",
                tint = Color.White

            )

        }

    }
}