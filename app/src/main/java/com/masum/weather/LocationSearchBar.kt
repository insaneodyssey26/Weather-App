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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val sampleSuggestions = listOf(
        "New York, NY",
        "London, UK",
        "Tokyo, Japan",
        "Paris, France",
        "Sydney, Australia",
        "Mumbai, India",
        "Toronto, Canada",
        "Berlin, Germany"
    ).filter { it.contains(searchText, ignoreCase = true) }
    
    if (sampleSuggestions.isNotEmpty()) {
        Column(
            modifier = modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(vertical = 8.dp)
        ) {
            sampleSuggestions.take(5).forEach { suggestion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onLocationSelected(suggestion) }
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
                        text = suggestion,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
