package com.github.muhwyndhamhp.ktorweather.dtos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.squareup.moshi.Json


data class WeatherDataEntity(
    @Embedded
    val weatherData: WeatherData?,
    @Relation(
        parentColumn = "id",
        entityColumn = "weatherDataID"
    )
    val weatherUnit: WeatherUnit?,
    @Relation(
        parentColumn = "id",
        entityColumn = "weatherDataID"
    )
    val currentWeather: Weather?,
    @Relation(
        parentColumn = "id",
        entityColumn = "weatherDataID"
    )
    val hourlyUnits: WeatherHourlyUnits?,
    @Relation(
        parentColumn = "id",
        entityColumn = "weatherDataID"
    )
    val hourly: HourlyData?,
)

@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
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
    @Ignore
    @field:Json(name = "current_weather_units")
    val weatherUnit: WeatherUnit?,
    @field:Json(name = "current_weather_interval_seconds")
    val weatherIntSeconds: Long?,
    @Ignore
    @field:Json(name = "current_weather")
    val currentWeather: Weather?,
    @Ignore
    @field:Json(name = "hourly_units")
    val hourlyUnits: WeatherHourlyUnits?,
    @Ignore
    @field:Json(name = "hourly")
    val hourly: HourlyData?,
) {
    constructor(
        id: Long?,
        lat: Double?,
        lng: Double?,
        timeZone: String?,
        timeZoneAbbr: String?,
        elevation: Int?,
        weatherIntSeconds: Long?,
    ) : this(
        0,
        0.0,
        0.0,
        "",
        "",
        0,
        null,
        0,
        null,
        null,
        null
    )
}

@Entity
data class WeatherHourlyUnits(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
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
    var weatherDataID: Long?,
)

@Entity
data class HourlyData(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
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
    var weatherDataID: Long?,
)

@Entity
data class WeatherUnit(
    @PrimaryKey
    val id: Long?,
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
    @field:Json(name = "weather_code")
    val weatherCode: String?,
    var weatherDataID: Long?,
)

@Entity
data class Weather(
    @PrimaryKey
    val id: Long?,
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
    @field:Json(name = "weather_code")
    val weatherCode: Int?,
    var weatherDataID: Long?,
)