package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.poppinsFontFamily

@Composable
fun NumberField(
    value: String,
    unit: String,
    onValueChange: (String) -> Unit
) {
    var color by remember {
        mutableStateOf(DarkGrey)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .height(64.dp)
                .wrapContentWidth()
                .border(
                    width = 1.dp,
                    color = color,
                    shape = RoundedCornerShape(10)
                )
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box{
                    if (value.isEmpty() || value == "0" || value == "0.0") {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "0",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = color,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center
                        )
                        color = DarkGrey
                    } else {
                        color = DarkPink
                    }
                    BasicTextField(
                        modifier = Modifier
                            .width(95.dp)
                            .align(Alignment.Center),

                        value = if (value.isEmpty() || value == "0" || value == "0.0") "" else value,
                        onValueChange = onValueChange,
                        maxLines = 1,
                        textStyle = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = if (value.isEmpty() || value == "0" || value == "0.0") DarkGrey else DarkPink,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .width(56.dp)
                        .height(7.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(color)
                ){
                }
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            textAlign = TextAlign.Center,
            text = unit,
            style = MaterialTheme.typography.titleLarge,
            color = color
        )
    }

}