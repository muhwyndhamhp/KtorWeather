package com.github.muhwyndhamhp.ktorweather.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.github.muhwyndhamhp.ktorweather.dtos.HourlyData
import com.github.muhwyndhamhp.ktorweather.dtos.Weather
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherDataEntity
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherHourlyUnits
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherUnit
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao {
    @Transaction
    @Query("SELECT * FROM WeatherData ORDER BY id DESC LIMIT 1")
    abstract fun getLastWeatherData(): Flow<WeatherDataEntity?>

    @Query("SELECT * FROM WeatherData ORDER BY id DESC LIMIT 1")
    abstract suspend fun getLatest(): WeatherData?

    @Insert
    abstract fun insertWeatherData(data: WeatherData): Long

    @Insert
    abstract fun insertWeatherUnit(data: WeatherUnit): Long

    @Insert
    abstract fun insertWeather(data: Weather): Long

    @Insert
    abstract fun insertWeatherHourlyUnits(data: WeatherHourlyUnits): Long

    @Insert
    abstract fun insertHourlyData(data: HourlyData): Long

    fun insertWeatherDataEntity(data: WeatherDataEntity) {
        val weatherDataID = insertWeatherData(data.weatherData ?: return)

        data.weatherUnit?.weatherDataID = weatherDataID
        data.currentWeather?.weatherDataID = weatherDataID
        data.hourlyUnits?.weatherDataID = weatherDataID
        data.hourly?.weatherDataID = weatherDataID

        insertWeatherUnit(data.weatherUnit ?: return)
        insertWeather(data.currentWeather ?: return)
        insertWeatherHourlyUnits(data.hourlyUnits ?: return)
        insertHourlyData(data.hourly ?: return)
    }
}