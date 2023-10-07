package com.github.muhwyndhamhp.ktorweather.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.muhwyndhamhp.ktorweather.dtos.HourlyData
import com.github.muhwyndhamhp.ktorweather.dtos.Weather
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherHourlyUnits
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherUnit

@TypeConverters(TypeConverterHelper::class)
@Database(entities = [WeatherData::class, WeatherUnit::class, Weather::class, WeatherHourlyUnits::class, HourlyData::class], version = 1)
abstract class KtorWeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}