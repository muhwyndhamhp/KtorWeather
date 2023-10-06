package com.github.muhwyndhamhp.ktorweather.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.muhwyndhamhp.ktorweather.dtos.Weather
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherUnit

@Database(entities = [WeatherData::class, WeatherUnit::class, Weather::class], version = 1)
abstract class KtorWeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}