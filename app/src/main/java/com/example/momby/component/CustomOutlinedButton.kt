package com.example.momby.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkPink

@Composable
fun CustomOutlinedButton(
    modifier: Modifier,
    text:String,
    onClick:()->Unit
){
    OutlinedButton(modifier = modifier
        .fillMaxWidth()
        .height(60.dp),
        border = BorderStroke(2.dp, DarkPink),
        onClick = onClick) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = DarkPink
        )

    }
}