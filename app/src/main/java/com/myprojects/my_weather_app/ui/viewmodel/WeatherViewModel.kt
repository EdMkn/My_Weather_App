package com.myprojects.my_weather_app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myprojects.my_weather_app.data.LocationHelper
import com.myprojects.my_weather_app.data.api.RetrofitClient
import com.myprojects.my_weather_app.data.model.ForecastResponse
import com.myprojects.my_weather_app.data.model.WeatherResponse
import com.myprojects.my_weather_app.BuildConfig
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherApiService = RetrofitClient.weatherApiService
    private val locationHelper = LocationHelper(application)
    private val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

    private val _currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeather: LiveData<WeatherResponse> = _currentWeather

    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse> = _forecast

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchWeatherData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val location = locationHelper.getCurrentLocation()
                if (location != null) {
                    val currentWeatherResponse = weatherApiService.getCurrentWeather(
                        location.latitude,
                        location.longitude,
                        apiKey = apiKey
                    )

                    if (currentWeatherResponse.isSuccessful) {
                        _currentWeather.value = currentWeatherResponse.body()
                    }

                    val forecastResponse = weatherApiService.getForecast(
                        location.latitude,
                        location.longitude,
                        apiKey = apiKey
                    )

                    if (forecastResponse.isSuccessful) {
                        _forecast.value = forecastResponse.body()
                    }
                } else {
                    _error.value = "Impossible d'obtenir la localisation"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Erreur inconnue"
            } finally {
                _isLoading.value = false
            }
        }
    }
}