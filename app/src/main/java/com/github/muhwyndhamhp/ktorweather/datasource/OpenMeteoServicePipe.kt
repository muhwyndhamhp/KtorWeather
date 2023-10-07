package com.github.muhwyndhamhp.ktorweather.datasource

import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import kotlinx.coroutines.flow.Flow

interface OpenMeteoServicePipe {
    fun getForecast(
        lat: Double,
        lng: Double,
        currentWeather: Boolean = true,
        paramsHourly: String = "temperature_2m,relativehumidity_2m,windspeed_10m",
    ): Flow<WeatherData?>
}