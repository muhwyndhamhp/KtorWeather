package com.github.muhwyndhamhp.ktorweather.modules.forecast.usecase

import com.github.muhwyndhamhp.ktorweather.models.DailyWeather
import kotlinx.coroutines.flow.Flow

interface GetSevenDaysForecastUsecase {
    suspend fun flows(lat: Double, lng: Double): Flow<Result<DailyWeather>>
}