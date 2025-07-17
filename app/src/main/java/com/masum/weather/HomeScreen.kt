package com.masum.weather

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masum.weather.ui.theme.WeatherTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var isMenuVisible by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF87CEEB),
                        Color(0xFF4682B4),
                        Color(0xFF6A5ACD),
                        Color(0xFF9370DB)
                    )
                )
            )
    ) {
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
                text = "Tuscany",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
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
                    println("Location selected: $location")
                },
                onSearchTextChange = { searchText ->
                    println("Search text: $searchText")
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
                    Text(
                        text = "Today, Oct 19 6:10",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Canvas(modifier = Modifier.size(36.dp)) {
                        drawSunIcon(this)
                    }
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 88.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.22f),
                                        blurRadius = 10f,
                                        offset = Offset(0f, 4f)
                                    )
                                )
                            ) {
                                append("23")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("°")
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "It's Sunny",
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
                    val hourlyData = listOf(
                        "06:00 AM" to "23",
                        "09:00 AM" to "16",
                        "12:00 PM" to "3",
                        "03:00 PM" to "23",
                        "06:00 PM" to "21",
                        "09:00 PM" to "18",
                        "12:00 AM" to "15"
                    )
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(hourlyData.size) { index ->
                            val (time, temp) = hourlyData[index]
                            HourlyWeatherItem(time = time, temp = temp)
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
                println("Menu item clicked: $menuItem")
            }
        )
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
