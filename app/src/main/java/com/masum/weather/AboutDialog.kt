package com.masum.weather

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("About Weather App", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        },
        text = {
            Text("Weather App\n\nVersion 1.0.0\n\nDeveloped by @insaneodyssey26\n\nA weather app built with Jetpack Compose.", style = MaterialTheme.typography.bodyMedium)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}
