package com.example.momby.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.momby.R
import com.example.momby.ui.theme.Green

@Composable
fun RoundCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        modifier = modifier
            .size(36.dp)
    ) {
        Box(modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(if (isChecked) Color.Transparent else Color.Gray)
            .border(width = 3.dp, if(isChecked) Green else Color.Transparent, shape =  CircleShape),
            contentAlignment = Alignment.Center
        ){
            if (isChecked) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = "Checked",
                    tint = Green,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
}