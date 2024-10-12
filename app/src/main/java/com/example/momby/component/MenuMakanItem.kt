package com.example.momby.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.momby.model.MenuMakan
import com.example.momby.ui.theme.DarkGrey

@Composable
fun MenuMakanItem(
    menu:MenuMakan,
    onCheckedChange:(Boolean)->Unit
){
    Box {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = menu.nama, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = menu.desc,
                        style = MaterialTheme.typography.bodyMedium,
                        color = DarkGrey
                    )
                }
                RoundCheckbox(isChecked = menu.isDone, onCheckedChange = onCheckedChange)
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = DarkGrey
            )

        }
    }
}