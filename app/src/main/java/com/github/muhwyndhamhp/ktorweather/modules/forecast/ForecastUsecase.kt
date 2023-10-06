package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.models.DailyWeather
import kotlinx.coroutines.flow.Flow

interface ForecastUsecase {
    suspend fun getSevenDaysForecast(lat: Double, lng: Double): Flow<Result<DailyWeather>>
}