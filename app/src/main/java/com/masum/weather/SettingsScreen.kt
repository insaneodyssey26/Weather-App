package com.masum.weather

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentUnit: String = "C",
    onUnitChange: (String) -> Unit = {},
    currentTheme: String = "System",
    onThemeChange: (String) -> Unit = {},
    notificationsEnabled: Boolean = true,
    onNotificationsChange: (Boolean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CenterAlignedTopAppBar(
                title = { Text("Settings", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: handle back */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text("General", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                SettingCard(
                    icon = Icons.Filled.Settings,
                    iconBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                    title = "Temperature Unit",
                    content = {
                        Row {
                            listOf("C", "F").forEach { unit ->
                                val selected = currentUnit == unit
                                if (selected) {
                                    ElevatedButton(
                                        onClick = { onUnitChange(unit) },
                                        shape = RoundedCornerShape(50),
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) { Text(unit) }
                                } else {
                                    OutlinedButton(
                                        onClick = { onUnitChange(unit) },
                                        shape = RoundedCornerShape(50),
                                        modifier = Modifier.padding(end = 8.dp)
                                    ) { Text(unit) }
                                }
                            }
                        }
                    }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                SettingCard(
                    icon = Icons.Filled.Info,
                    iconBgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
                    title = "Theme",
                    content = {
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                        ) {
                            listOf("Light", "Dark", "System").forEach { theme ->
                                val selected = currentTheme == theme
                                if (selected) {
                                    ElevatedButton(
                                        onClick = { onThemeChange(theme) },
                                        shape = RoundedCornerShape(50),
                                        modifier = Modifier.padding(end = 4.dp)
                                    ) { Text(theme, maxLines = 1) }
                                } else {
                                    OutlinedButton(
                                        onClick = { onThemeChange(theme) },
                                        shape = RoundedCornerShape(50),
                                        modifier = Modifier.padding(end = 4.dp)
                                    ) { Text(theme, maxLines = 1) }
                                }
                            }
                        }
                    }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                Text("Notifications", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                // Notifications
                SettingCard(
                    icon = Icons.Filled.Notifications,
                    iconBgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.15f),
                    title = "Weather Notifications",
                    content = {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = onNotificationsChange
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SettingCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBgColor: Color,
    title: String,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(iconBgColor, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = title, modifier = Modifier.size(26.dp), tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}
