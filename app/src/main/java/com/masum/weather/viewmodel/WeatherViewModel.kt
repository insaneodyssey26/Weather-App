package com.masum.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masum.weather.network.model.CurrentWeatherResponse
import com.masum.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val data: CurrentWeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
    object Empty : WeatherUiState()
}

class WeatherViewModel : ViewModel() {
    private val repository = WeatherRepository()
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    fun fetchWeather(city: String) {
        _weatherState.value = WeatherUiState.Loading
        viewModelScope.launch {
            try {
                val result = repository.getCurrentWeather(city)
                _weatherState.value = WeatherUiState.Success(result)
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
