package com.example.momby.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.momby.ui.theme.DarkGrey
import com.example.momby.ui.theme.DarkPink
import com.example.momby.ui.theme.poppinsFontFamily
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HintDatePicker(
    hint: String,
    onValueChange: (Date?) -> Unit,
    value: Date?,
) {

    var color by remember {
        mutableStateOf(DarkGrey)
    }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)

            onValueChange(selectedCalendar.time) // Pass the Date object here
        },
        year, month, day
    )

    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    val formattedDate = value?.let { dateFormat.format(it) } ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(100)
            )
            .clickable {
                datePickerDialog.show()
            }
    ) {
        if (value == null) {
            color = DarkGrey
            Text(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 28.dp)
                    .clickable {
                        datePickerDialog.show()
                    },
                text = hint,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGrey
            )
        } else {
            color = DarkPink
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 28.dp)
                .clickable {
                    datePickerDialog.show()
                },
            value = formattedDate,
            onValueChange = {
                datePickerDialog.show()
            },
            readOnly = true,
            maxLines = 1,

            singleLine = true,
            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = DarkPink
            ),

            )
        Icon(modifier = Modifier.align(Alignment.CenterEnd).padding(horizontal = 16.dp).clickable {
            datePickerDialog.show()
        }, imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
    }
}