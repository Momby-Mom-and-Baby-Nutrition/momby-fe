package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.DisablePink
import com.example.momby.ui.theme.LightPink

@Composable
fun CustomReactiveButton(
    modifier: Modifier,
    onClick:()-> Unit,
    text:String,
    isEnable:Boolean
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(percent = 100))
            .background(
                brush = if (isEnable) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            LightPink,
                            DarkPink
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            DisablePink,
                            DisablePink
                        )
                    )
                }
            )
    ){
        Button(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(), enabled = isEnable, onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White,
                disabledContainerColor = DisablePink,
                disabledContentColor = Color.White
            )
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleSmall
            )

        }
    }
}