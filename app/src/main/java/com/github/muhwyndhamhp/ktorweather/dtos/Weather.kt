package com.github.muhwyndhamhp.ktorweather.dtos

import androidx.room.Entity
import com.squareup.moshi.Json


data class WeatherData(
    @field:Json(name = "latitude")
    val lat: Double?,
    @field:Json(name = "longitude")
    val lng: Double?,
    @field:Json(name = "timezone")
    val timeZone: String?,
    @field:Json(name = "timezone_abbreviation")
    val timeZoneAbbr: String?,
    @field:Json(name = "elevation")
    val elevation: Int?,
    @field:Json(name = "current_weather_interval_seconds")
    val weatherIntSeconds: Long?,
    @field:Json(name = "current_weather_units")
    val weatherUnit: WeatherUnit?,
    @field:Json(name = "current_weather")
    val currentWeather: Weather?,
    @field:Json(name = "hourly_units")
    val hourlyUnits: WeatherHourlyUnits?,
    @field:Json(name = "hourly")
    val hourly: HourlyData?
)

@Entity
data class WeatherHourlyUnits(
    @field:Json(name = "relativehumidity_2m")
    val relativeHumidity: String?,
    @field:Json(name = "temperature_2m")
    val temperature: String?,
    @field:Json(name = "time")
    val time: String?,
    @field:Json(name = "weathercode")
    val weatherCode: String?,
    @field:Json(name = "winddirection_10m")
    val windDirection: String?,
    @field:Json(name = "windspeed_10m")
    val windSpeed: String?,
)

data class HourlyData(
    @field:Json(name = "relativehumidity_2m")
    val relativeHumidity: List<Int?>?,
    @field:Json(name = "temperature_2m")
    val temperature: List<Float?>?,
    @field:Json(name = "time")
    val time: List<String?>?,
    @field:Json(name = "weathercode")
    val weatherCode: List<Int?>?,
    @field:Json(name = "winddirection_10m")
    val windDirection: List<Int?>?,
    @field:Json(name = "windspeed_10m")
    val windSpeed: List<Float?>?,
)

data class WeatherUnit(
    @field:Json(name = "time")
    val time: String?,
    @field:Json(name = "temperature")
    val temperature: String?,
    @field:Json(name = "windspeed")
    val windSpeed: String?,
    @field:Json(name = "winddirection")
    val windDirection: String?,
    @field:Json(name = "is_day")
    val isDay: String?,
    @field:Json(name = "weathercode")
    val weatherCode: String?,
)

data class Weather(
    @field:Json(name = "time")
    val time: String?,
    @field:Json(name = "temperature")
    val temperature: Float?,
    @field:Json(name = "windspeed")
    val windSpeed: Float?,
    @field:Json(name = "winddirection")
    val windDirection: Int?,
    @field:Json(name = "is_day")
    val isDay: Int?,
    @field:Json(name = "weathercode")
    val weatherCode: Int?,
)