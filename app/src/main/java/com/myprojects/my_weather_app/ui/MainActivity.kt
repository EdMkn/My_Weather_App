package com.myprojects.my_weather_app.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.myprojects.my_weather_app.R
import com.myprojects.my_weather_app.data.model.ForecastItem
import com.myprojects.my_weather_app.data.model.WeatherResponse
import com.myprojects.my_weather_app.databinding.ActivityMainBinding
import com.myprojects.my_weather_app.ui.adapter.ForecastAdapter
import com.myprojects.my_weather_app.ui.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        checkLocationPermission()
        setupObservers()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            viewModel.fetchWeatherData()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.fetchWeatherData()
            } else {
                binding.tvError.text = "Permission de localisation refusée"
                binding.tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun setupObservers() {
        viewModel.currentWeather.observe(this) { weather ->
            weather?.let { updateCurrentWeather(it) }
        }

        viewModel.forecast.observe(this) { forecast ->
            forecast?.let { updateForecast(it.list) }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.scrollView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                binding.tvError.text = it
                binding.tvError.visibility = View.VISIBLE
                binding.scrollView.visibility = View.GONE
            }
        }
    }

    private fun updateCurrentWeather(weather: WeatherResponse) {
        binding.tvCity.text = weather.name
        binding.tvTemp.text = "${weather.main.temp.toInt()}°C"
        binding.tvWeatherDesc.text = weather.weather[0].description
        binding.tvTempRange.text = "Min: ${weather.main.temp_min.toInt()}°C / Max: ${weather.main.temp_max.toInt()}°C"

        val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
        Glide.with(this)
            .load(iconUrl)
            .into(binding.ivWeatherIcon)
    }

    private fun updateForecast(forecastItems: List<ForecastItem>) {
        // On ne garde qu'un élément par jour pour simplifier
        val dailyForecasts = forecastItems.filter { it.dt_txt.contains("12:00:00") }

        binding.rvForecast.layoutManager = LinearLayoutManager(this)
        binding.rvForecast.adapter = ForecastAdapter(dailyForecasts)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}