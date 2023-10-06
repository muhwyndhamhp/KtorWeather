package com.github.muhwyndhamhp.ktorweather.models

data class DailyWeather(
    val currentWeather: Weather,
    val nextWeathers: List<Weather>,
    val todayWeathers: List<Weather>,
)
