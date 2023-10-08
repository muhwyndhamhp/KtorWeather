package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.datasource.OpenMeteoServicePipe
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class ForecastRepositoryImpl(private val service: OpenMeteoServicePipe) : ForecastRepository,
    KoinComponent {
    override suspend fun getLatestForecastData(
        lat: Double,
        lng: Double
    ): Flow<Result<WeatherData>> {
        val networkFlow = service.getForecast(lat, lng)

        return networkFlow.map {
            Result.success(it ?: return@map Result.failure(Exception("Data Empty")))
        }

    }
}