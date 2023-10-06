package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    suspend fun getLatestForecastData(lat: Double, lng: Double): Flow<Result<WeatherData>>
}