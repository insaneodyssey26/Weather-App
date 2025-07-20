package com.masum.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.masum.weather.network.LocationIqRetrofitInstance
import com.masum.weather.network.model.LocationIqSuggestion
import com.masum.weather.BuildConfig
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationSearchBar(
    modifier: Modifier = Modifier,
    onLocationSelected: (String) -> Unit = {},
    onSearchTextChange: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .background(
                Color.White,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { 
                searchText = it
                onSearchTextChange(it)
                isSearching = it.isNotEmpty()
            },
            placeholder = {
                Text(
                    text = "Search for a location...",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF4682B4),
                    modifier = Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchText = ""
                            onSearchTextChange("")
                            isSearching = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (searchText.isNotEmpty()) {
                        onLocationSelected(searchText)
                        keyboardController?.hide()
                    }
                }
            ),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color(0xFF4682B4)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        
        if (isSearching && searchText.isNotEmpty()) {
            LocationSuggestions(
                searchText = searchText,
                onLocationSelected = { location ->
                    searchText = location
                    onLocationSelected(location)
                    isSearching = false
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp)
            )
        }
    }
}

@Composable
fun LocationSuggestions(
    searchText: String,
    onLocationSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var suggestions by remember { mutableStateOf<List<LocationIqSuggestion>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchText) {
        if (searchText.isNotBlank()) {
            isLoading = true
            error = null
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val result = LocationIqRetrofitInstance.api.autocomplete(
                        apiKey = BuildConfig.LOCATIONIQ_API_KEY,
                        query = searchText
                    )
                    suggestions = result
                    isLoading = false
                } catch (e: Exception) {
                    suggestions = emptyList()
                    error = "No suggestions found"
                    isLoading = false
                }
            }
        } else {
            suggestions = emptyList()
            error = null
        }
    }

    if (isLoading) {
        Box(
            modifier = modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", color = Color.Gray)
        }
    } else if (!suggestions.isNullOrEmpty()) {
        Column(
            modifier = modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(vertical = 8.dp)
        ) {
            suggestions.take(5).forEach { suggestion ->
                val address = suggestion.address
                val parts = listOfNotNull(
                    suggestion.displayPlace,
                    address?.city?.takeIf { it != suggestion.displayPlace },
                    address?.state,
                    address?.country
                ).distinct()
                val displayName = parts.joinToString(", ").ifBlank {
                    suggestion.displayAddress ?: "Unknown"
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLocationSelected(displayName) }
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = displayName,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    } else if (error != null) {
        Box(
            modifier = modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(error ?: "No suggestions", color = Color.Gray)
        }
    }
}
