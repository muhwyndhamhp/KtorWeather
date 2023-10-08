package com.github.muhwyndhamhp.ktorweather.models

data class DailyWeather(
    val currentWeather: Weather,
    val nextWeathers: List<Weather>,
    val todayWeathers: List<Weather>,
) {
    companion object {
        fun getMockDailyWeather(): DailyWeather {
            return DailyWeather(
                currentWeather = Weather.getMockData(),
                nextWeathers = listOf(
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData()
                ),
                todayWeathers = listOf(
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData(),
                    Weather.getMockData()
                )
            )
        }
    }
}