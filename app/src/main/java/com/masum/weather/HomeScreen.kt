package com.masum.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masum.weather.ui.theme.WeatherTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Weather App",
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = "City: [Your City]",
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Temperature: --°C",
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Condition: [Clear/Sunny]",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WeatherTheme {
        HomeScreen()
    }
}

