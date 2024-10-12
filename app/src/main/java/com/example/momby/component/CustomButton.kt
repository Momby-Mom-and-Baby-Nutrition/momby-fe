package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightPink

@Composable
fun CustomButton(
    modifier: Modifier,
    text:String,
    onClick:()->Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(percent = 100))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        LightPink,
                        DarkPink
                    )
                )
            )
    ) {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                ,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(text = text, style = MaterialTheme.typography.titleSmall)
        }
    }
}