package com.github.muhwyndhamhp.ktorweather.models

import com.github.muhwyndhamhp.ktorweather.dtos.HourlyData
import com.github.muhwyndhamhp.ktorweather.utils.Generics.Companion.getEnumByValue
import com.github.muhwyndhamhp.ktorweather.dtos.Weather as DtosWeather

data class Weather(
    val time: String,
    val temperature: Float,
    val windSpeed: Float,
    val windDirection: Int,
    val isDay: DayCode,
    val weatherCode: WeatherCode
) {
    companion object {
        fun extractFromIndexes(indexes: List<Int>, hourlyData: HourlyData): List<Weather> {
            return indexes.map { i ->
                Weather(
                    time = hourlyData.time?.get(i) ?: "",
                    temperature = hourlyData.temperature?.get(i) ?: 0.0f,
                    windSpeed = hourlyData.windSpeed?.get(i) ?: 0.0f,
                    windDirection = hourlyData.windDirection?.get(i) ?: 0,
                    isDay = DayCode.Invalid,
                    weatherCode = getEnumByValue<WeatherCode, Int?>
                        (value = hourlyData.weatherCode?.get(i) ?: -1) { it.code }
                        ?: WeatherCode.Invalid
                )
            }
        }

        fun getMockData(): Weather {
            return Weather(
                time = "2023-10-07T00:00",
                temperature = 23.7f,
                windSpeed = 8.0f,
                windDirection = 198,
                isDay = DayCode.Day,
                weatherCode = WeatherCode.ClearSky
            )
        }

        fun fromDtoWeather(currentWeather: DtosWeather): Weather {
            return Weather(
                time = currentWeather.time ?: "",
                temperature = currentWeather.temperature ?: 0.0f,
                windSpeed = currentWeather.windSpeed ?: 0.0f,
                windDirection = currentWeather.windDirection ?: 0,
                isDay = getEnumByValue<DayCode, Int>
                    (value = currentWeather.isDay ?: 2) { it.code }
                    ?: DayCode.Invalid,
                weatherCode = getEnumByValue<WeatherCode, Int?>
                    (value = currentWeather.weatherCode ?: -1) { it.code }
                    ?: WeatherCode.Invalid
            )
        }
    }
}


