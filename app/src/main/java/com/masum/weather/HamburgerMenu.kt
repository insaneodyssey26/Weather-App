package com.masum.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HamburgerMenu(
    isVisible: Boolean,
    onClose: () -> Unit,
    onMenuItemClick: (String) -> Unit = {}
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 24.dp, end = 24.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Weather App",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4682B4),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Menu",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                MenuItem(
                    icon = Icons.Filled.Home, 
                    label = "Home"
                ) { 
                    onMenuItemClick("Home")
                    onClose()
                }
                
                MenuItem(
                    icon = Icons.Filled.LocationOn,
                    label = "Locations"
                ) { 
                    onMenuItemClick("Locations")
                    onClose()
                }
                
                MenuItem(
                    icon = Icons.Filled.Refresh,
                    label = "Refresh"
                ) { 
                    onMenuItemClick("Refresh")
                    onClose()
                }
                
                MenuItem(
                    icon = Icons.Filled.Settings,
                    label = "Settings"
                ) { 
                    onMenuItemClick("Settings")
                    onClose()
                }
                
                MenuItem(
                    icon = Icons.Filled.Info,
                    label = "About"
                ) { 
                    onMenuItemClick("About")
                    onClose()
                }
            }
        }
    }
}

@Composable
fun MenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector, 
    label: String, 
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF4682B4),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}
