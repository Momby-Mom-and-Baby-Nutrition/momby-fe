package com.example.momby.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.poppinsFontFamily

@Composable
fun HintTextField(
    hint: String,
    onValueChange: (String) -> Unit,
    value: String,
) {
    var color by remember{
      mutableStateOf(DarkGrey)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, // Define the stroke width
                color = color, // Set the stroke color to grey
                shape = RoundedCornerShape(100) // Optional: Use rounded corners
            )
    ) {
        if (value.isEmpty()) {
            color = DarkGrey
            Text(
                modifier= Modifier.padding(vertical = 16.dp, horizontal = 28.dp),
                text = hint,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGrey
            )
        } else{
            color = DarkPink
        }

        BasicTextField(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 28.dp),
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = DarkPink
            ),

        )
    }

}