package com.example.proyectofinal.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DateTimePicker(
    onDateTimeSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }
    var showDatePicker by remember { mutableStateOf(true) }

    if (showDatePicker) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.timeInMillis
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        LaunchedEffect(Unit) {
            datePickerDialog.show()
        }

        DisposableEffect(Unit) {
            onDispose {
                datePickerDialog.dismiss()
            }
        }
    } else {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay: Int, minute: Int ->
                calendar.timeInMillis = selectedDate
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                onDateTimeSelected(calendar.timeInMillis)
                onDismiss()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // Formato 24 horas
        )

        LaunchedEffect(Unit) {
            timePickerDialog.show()
        }

        DisposableEffect(Unit) {
            onDispose {
                timePickerDialog.dismiss()
            }
        }
    }
}