package com.masum.weather

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import com.masum.weather.BuildConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.masum.weather.viewmodel.WeatherViewModel
import com.masum.weather.viewmodel.WeatherUiState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masum.weather.ui.theme.WeatherTheme

@Composable

fun HomeScreen(modifier: Modifier = Modifier) {
    val weatherViewModel: WeatherViewModel = viewModel()
    val weatherState by weatherViewModel.weatherState.collectAsState()
    val forecastState by weatherViewModel.forecastState.collectAsState()
    val apiKey = BuildConfig.OPENWEATHER_API_KEY

    var currentCity by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    var isMenuVisible by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    var appTheme by remember { mutableStateOf("System") }
    var tempUnit by remember { mutableStateOf("C") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var showLocations by remember { mutableStateOf(false) }
    var recentLocations by remember { mutableStateOf(listOf<String>()) }
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")
    val cloud1X by infiniteTransition.animateFloat(
        initialValue = -0.3f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "cloud1X"
    )
    val cloud2X by infiniteTransition.animateFloat(
        initialValue = 1.1f,
        targetValue = -0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "cloud2X"
    )
    val cloud1Opacity by infiniteTransition.animateFloat(
        initialValue = 0.7f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "cloud1Opacity"
    )
    val cloud2Opacity by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "cloud2Opacity"
    )
    val isDark = when (appTheme) {
        "Light" -> false
        "Dark" -> true
        else -> androidx.compose.foundation.isSystemInDarkTheme()
    }
    if (showLocations) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.98f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Recent Locations", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4682B4), modifier = Modifier.padding(top = 32.dp, bottom = 16.dp))
                if (recentLocations.isEmpty()) {
                    Text("No recent locations", color = Color.Gray, fontSize = 16.sp, modifier = Modifier.padding(24.dp))
                } else {
                    recentLocations.forEach { location ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = location,
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        currentCity = location
                                        weatherViewModel.fetchWeather(location)
                                        weatherViewModel.fetchForecast(location)
                                        showLocations = false
                                        showSearchBar = true
                                    }
                            )
                            androidx.compose.material3.IconButton(
                                onClick = {
                                    recentLocations = recentLocations.filter { it != location }
                                }
                            ) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Default.Clear,
                                    contentDescription = "Remove",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                androidx.compose.material3.Button(onClick = { showLocations = false }, shape = RoundedCornerShape(32.dp)) {
                    Text("Back", fontSize = 16.sp)
                }
            }
        }
    } else if (showSettings) {
        val (darkTheme, dynamicColor) = when (appTheme) {
            "Light" -> false to false
            "Dark" -> true to false
            else -> androidx.compose.foundation.isSystemInDarkTheme() to true
        }
        com.masum.weather.ui.theme.WeatherTheme(darkTheme = darkTheme, dynamicColor = dynamicColor) {
            SettingsScreen(
                currentUnit = tempUnit,
                onUnitChange = { tempUnit = it },
                currentTheme = appTheme,
                onThemeChange = { appTheme = it },
                notificationsEnabled = notificationsEnabled,
                onNotificationsChange = { notificationsEnabled = it },
                onBack = { showSettings = false }
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            // ...background and menu code remains unchanged...
            Canvas(modifier = Modifier.matchParentSize().blur(24.dp)) {
                val w = size.width
                val h = size.height
                if (isDark) {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF232946), Color(0xFF2C2C54)),
                            startY = 0f, endY = h
                        ),
                        size = size
                    )
                } else {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB2EBF2),
                                Color(0xFFB3E5FC),
                                Color(0xFF81D4FA),
                                Color(0xFFB2DFDB),
                                Color(0xFFF5F5F5)
                            ),
                            startY = 0f, endY = h
                        ),
                        size = size
                    )
                }
                drawCloudGradient(
                    center = Offset(w * cloud1X, h * 0.18f),
                    scale = w * 0.24f,
                    alpha = cloud1Opacity
                )
                drawCloudGradient(
                    center = Offset(w * cloud2X, h * 0.72f),
                    scale = w * 0.32f,
                    alpha = cloud2Opacity
                )
            }
            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, end = 24.dp)
                .align(Alignment.TopEnd)
            ) {
                drawCircle(
                    color = Color(0xFFFFF9C4).copy(alpha = 0.18f),
                    radius = size.minDimension / 3.5f,
                    center = Offset(size.width * 0.85f, size.height * 0.12f)
                )
            }
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLandscapeBackground(this)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { isMenuVisible = !isMenuVisible }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = currentCity,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            if (!showSearchBar) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Button(
                        onClick = { showSearchBar = true },
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier
                            .height(56.dp)
                            .width(220.dp)
                    ) {
                        Text("Search for Location", fontSize = 18.sp)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LocationSearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        onLocationSelected = { location ->
                            currentCity = location
                            weatherViewModel.fetchWeather(location)
                            weatherViewModel.fetchForecast(location)
                            if (location.isNotBlank() && !recentLocations.contains(location)) {
                                recentLocations = (listOf(location) + recentLocations).take(10)
                            }
                        },
                        onSearchTextChange = { searchText ->
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(32.dp))
                            .background(Color.White.copy(alpha = 0.18f))
                            .padding(vertical = 28.dp, horizontal = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (weatherState) {
                                is WeatherUiState.Loading -> {
                                    Text("Loading weather...", color = Color.White)
                                }
                                is WeatherUiState.Error -> {
                                    Text((weatherState as WeatherUiState.Error).message, color = Color.Red)
                                }
                                is WeatherUiState.Success -> {
                                    val data = (weatherState as WeatherUiState.Success).data
                                    Text(
                                        text = data.cityName ?: "-",
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Canvas(modifier = Modifier.size(36.dp)) {
                                        drawSunIcon(this)
                                    }
                                    Text(
                                        text = "${data.main?.temp?.toInt() ?: "-"}°C",
                                        color = Color.White,
                                        fontSize = 88.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .align(Alignment.CenterHorizontally),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = data.weather?.firstOrNull()?.main ?: "-",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        style = androidx.compose.ui.text.TextStyle(
                                            shadow = Shadow(
                                                color = Color.Black.copy(alpha = 0.18f),
                                                blurRadius = 4f,
                                                offset = Offset(0f, 2f)
                                            )
                                        ),
                                        modifier = Modifier.padding(top = 6.dp)
                                    )
                                }
                                else -> {
                                    Text("No weather data available", color = Color.White)
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    Color.White.copy(alpha = 0.9f)
                                )
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(4.dp)
                                    .background(
                                        Color.Gray.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                            Text(
                                text = "Weather Today",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)
                            )
                            when (forecastState) {
                                is WeatherUiState.Loading -> {
                                    Text("Loading hourly forecast...", color = Color.Gray)
                                }
                                is WeatherUiState.ForecastSuccess -> {
                                    val forecast = (forecastState as WeatherUiState.ForecastSuccess).forecast
                                    val hourlyList = forecast.list?.take(8) ?: emptyList()
                                    androidx.compose.foundation.lazy.LazyRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        items(hourlyList.size) { index ->
                                            val item = hourlyList[index]
                                            val time = item.dtTxt?.substringAfter(' ')?.substring(0,5) ?: "--:--"
                                            val temp = item.main?.temp?.toInt()?.toString() ?: "-"
                                            HourlyWeatherItem(time = time, temp = temp)
                                        }
                                    }
                                }
                                is WeatherUiState.Error -> {
                                    Text((forecastState as WeatherUiState.Error).message, color = Color.Red)
                                }
                                is WeatherUiState.Empty -> {
                                    Text("No forecast data", color = Color.Gray)
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }

            if (isMenuVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .align(Alignment.CenterStart)
                        .clickable(onClick = { isMenuVisible = false })
                ) {}
            }
            HamburgerMenu(
                isVisible = isMenuVisible,
                onClose = { isMenuVisible = false },
                onMenuItemClick = { menuItem ->
                    when (menuItem) {
                        "Locations" -> {
                            showLocations = true
                            isMenuVisible = false
                        }
                        "Refresh" -> {
                            weatherViewModel.fetchWeather(currentCity)
                            weatherViewModel.fetchForecast(currentCity)
                            isMenuVisible = false
                        }
                        "Settings" -> {
                            showSettings = true
                            isMenuVisible = false
                        }
                        "About" -> {
                            showAbout = true
                            isMenuVisible = false
                        }
                    }
                }
            )

            if (showAbout) {
                AboutDialog(onDismiss = { showAbout = false })
            }
        }
    }
}

fun drawLandscapeBackground(drawScope: DrawScope) {
    with(drawScope) {
        val width = size.width
        val height = size.height
        val mountainPath = Path().apply {
            moveTo(0f, height * 0.6f)
            lineTo(width * 0.2f, height * 0.5f)
            lineTo(width * 0.4f, height * 0.55f)
            lineTo(width * 0.6f, height * 0.45f)
            lineTo(width * 0.8f, height * 0.52f)
            lineTo(width, height * 0.48f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(
            path = mountainPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF9370DB).copy(alpha = 0.6f),
                    Color(0xFF6A5ACD).copy(alpha = 0.4f)
                )
            )
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.3f),
            radius = 30f,
            center = Offset(width * 0.2f, height * 0.25f)
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.2f),
            radius = 25f,
            center = Offset(width * 0.7f, height * 0.3f)
        )
    }
}

fun drawSunIcon(drawScope: DrawScope) {
    with(drawScope) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 3
        for (i in 0 until 8) {
            val angle = (i * 45f) * (Math.PI / 180f)
            val startX = center.x + (radius * 1.5f * kotlin.math.cos(angle)).toFloat()
            val startY = center.y + (radius * 1.5f * kotlin.math.sin(angle)).toFloat()
            val endX = center.x + (radius * 2f * kotlin.math.cos(angle)).toFloat()
            val endY = center.y + (radius * 2f * kotlin.math.sin(angle)).toFloat()
            drawLine(
                color = Color(0xFFFFD700),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2.dp.toPx()
            )
        }
        drawCircle(
            color = Color(0xFFFFD700),
            radius = radius,
            center = center
        )
    }
}

@Composable
fun HourlyWeatherItem(time: String, temp: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Canvas(modifier = Modifier.size(28.dp)) {
            drawSunIcon(this)
        }
        Text(
            text = time,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "${temp}°",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 2.dp)
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

fun DrawScope.drawCloudGradient(center: Offset, scale: Float, alpha: Float) {
    val white = Color.White.copy(alpha = 0.85f * alpha)
    val lightGray = Color(0xFFE3EAF2).copy(alpha = 0.65f * alpha)
    val blueTint = Color(0xFFB3D8F7).copy(alpha = 0.45f * alpha)
    drawCircle(
        brush = Brush.radialGradient(listOf(white, lightGray), center = center, radius = scale * 1.1f),
        radius = scale,
        center = center
    )
    drawCircle(
        brush = Brush.radialGradient(listOf(white, blueTint), center = center + Offset(-scale * 0.7f, scale * 0.1f), radius = scale * 0.8f),
        radius = scale * 0.7f,
        center = center + Offset(-scale * 0.7f, scale * 0.1f)
    )
    drawCircle(
        brush = Brush.radialGradient(listOf(white, lightGray), center = center + Offset(scale * 0.7f, scale * 0.15f), radius = scale * 0.7f),
        radius = scale * 0.6f,
        center = center + Offset(scale * 0.7f, scale * 0.15f)
    )
    drawCircle(
        brush = Brush.radialGradient(listOf(white, blueTint), center = center + Offset(0f, -scale * 0.5f), radius = scale * 0.6f),
        radius = scale * 0.5f,
        center = center + Offset(0f, -scale * 0.5f)
    )
}
