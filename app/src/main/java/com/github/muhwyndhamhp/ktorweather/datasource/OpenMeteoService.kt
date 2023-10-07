package com.github.muhwyndhamhp.ktorweather.datasource

import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("hourly") paramsHourly: String = "temperature_2m,relativehumidity_2m,windspeed_10m",
    ): WeatherData?
}