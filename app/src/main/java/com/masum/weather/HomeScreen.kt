package com.masum.weather

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masum.weather.ui.theme.WeatherTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFb2f0ff), Color(0xFFe0c3fc))
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Tuscany",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            IconButton(onClick = { /* TODO: Menu */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Khardaha",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Today, July 15 5:10",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_day),
                        contentDescription = "Weather Icon",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "It's Sunny",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            val tempGradient = Brush.linearGradient(
                colors = listOf(Color(0xFFb2f0ff), Color(0xFF2193b0)),
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(200f, 200f),
                tileMode = TileMode.Clamp
            )
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            brush = tempGradient,
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Light,
                            shadow = Shadow(
                                color = Color(0x552193b0),
                                blurRadius = 24f,
                                offset = androidx.compose.ui.geometry.Offset(0f, 8f)
                            )
                        )
                    ) {
                        append("23")
                    }
                    withStyle(
                        style = SpanStyle(
                            brush = tempGradient,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Normal,
                            baselineShift = androidx.compose.ui.text.style.BaselineShift.Superscript
                        )
                    ) {
                        append("°")
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(190.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.7f),
                                Color(0xFFe0c3fc).copy(alpha = 0.3f)
                            )
                        ),
                        shape = RoundedCornerShape(36.dp)
                    )
                    .padding(top = 18.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(8.dp)
                        .background(
                            color = Color.Gray.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Weather Today",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HourlyWeatherItem(time = "05:00", temp = "23", iconPainter = painterResource(id = android.R.drawable.ic_menu_day))
                    HourlyWeatherItem(time = "06:00", temp = "16", iconPainter = painterResource(id = android.R.drawable.ic_menu_day))
                    HourlyWeatherItem(time = "07:00", temp = "3", iconPainter = painterResource(id = android.R.drawable.ic_menu_day))
                    HourlyWeatherItem(time = "08:00", temp = "23", iconPainter = painterResource(id = android.R.drawable.ic_menu_day))
                }
            }
        }
    }
}

@Composable
fun HourlyWeatherItem(time: String, temp: String, iconPainter: Painter) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            tint = Color(0xFF2193b0),
            modifier = Modifier.size(28.dp)
        )
        Text(text = time, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        Text(text = temp + "°", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WeatherTheme {
        HomeScreen()
    }
}
