package com.github.muhwyndhamhp.ktorweather.datasource

import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

class OpenMeteoServicePipeImpl(private val service: OpenMeteoService) : OpenMeteoServicePipe, KoinComponent {
    override fun getForecast(lat: Double, lng: Double, currentWeather: Boolean, paramsHourly: String): Flow<WeatherData?> {
        return flow {
            emit(service.getForecast(lat, lng, currentWeather, paramsHourly))
        }
    }
}