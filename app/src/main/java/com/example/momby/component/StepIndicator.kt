package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.LightGrey

@Composable
fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val stepColors = listOf(
            if (currentStep >= 1) DarkPink else LightGrey,
            if (currentStep >= 2) DarkPink else LightGrey,
            if (currentStep >= 3) DarkPink else LightGrey,
            if (currentStep >= 4) DarkPink else LightGrey,
            if (currentStep >= 5) DarkPink else LightGrey,
            if (currentStep >= 6) DarkPink else LightGrey,
            if (currentStep >= 7) DarkPink else LightGrey,
        )

        stepColors.forEachIndexed { index, color ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(7.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
            )
            if (index < stepColors.size - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}