package com.masum.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MiniForecastItem(time: String, temp: String, icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            fontSize = 22.sp,
            modifier = Modifier.height(28.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = time,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 13.sp
        )
        Text(
            text = temp,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}
