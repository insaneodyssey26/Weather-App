package com.masum.weather

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedHamburgerMenu(
    visible: Boolean,
    iconOffset: Pair<Float, Float>,
    onDismiss: () -> Unit,
    onMenuItemClick: (String) -> Unit = {}
) {
    // Animate scale for pop effect
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.7f,
        animationSpec = tween(durationMillis = 350)
    )
    if (visible) {
        Popup(
            alignment = Alignment.TopStart,
            offset = IntOffset(iconOffset.first.toInt(), iconOffset.second.toInt()),
            onDismissRequest = onDismiss
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(350)),
                exit = fadeOut(tween(200))
            ) {
                HamburgerMenu(
                    modifier = Modifier
                        .scale(scale)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                        ),
                    onMenuItemClick = {
                        onMenuItemClick(it)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun HamburgerMenu(
    modifier: Modifier = Modifier,
    onMenuItemClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(240.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
            )
            .padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Menu",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        MenuItem(icon = Icons.Filled.Home, label = "Home") { onMenuItemClick("Home") }
        MenuItem(icon = Icons.Filled.Settings, label = "Settings") { onMenuItemClick("Settings") }
        MenuItem(icon = Icons.Filled.Info, label = "About") { onMenuItemClick("About") }
    }
}

@Composable
fun MenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
