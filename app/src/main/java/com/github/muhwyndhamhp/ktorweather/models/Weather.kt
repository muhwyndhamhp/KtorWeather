package com.github.muhwyndhamhp.ktorweather.models

import com.github.muhwyndhamhp.ktorweather.dtos.HourlyData
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherHourlyUnits
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherUnit
import com.github.muhwyndhamhp.ktorweather.utils.CalendarProvider
import com.github.muhwyndhamhp.ktorweather.utils.Generics.Companion.getEnumByValue
import kotlin.math.roundToInt
import com.github.muhwyndhamhp.ktorweather.dtos.Weather as DtosWeather

data class Weather(
    val time: String,
    val temperature: String,
    val windSpeed: String,
    val windDirection: String,
    val isDay: DayCode,
    val weatherCode: WeatherCode
) {
    companion object {
        fun extractFromIndexes(
            indexes: List<Int>,
            hourlyData: HourlyData,
            hourlyUnit: WeatherHourlyUnits?,
            byHour: Boolean
        ): List<Weather> {
            return indexes.map { i ->
                Weather(
                    time =
                    if (byHour)
                        CalendarProvider().getShortHour(hourlyData.time?.get(i) ?: "")
                    else
                        CalendarProvider().getShortDate(hourlyData.time?.get(i) ?: ""),
                    temperature = "${(hourlyData.temperature?.get(i) ?: 0.0f).roundToInt()}${hourlyUnit?.temperature}",
                    windSpeed = "${(hourlyData.windSpeed?.get(i) ?: 0.0f).roundToInt()}${hourlyUnit?.windSpeed}",
                    windDirection = "${hourlyData.windDirection?.get(i) ?: 0}${hourlyUnit?.windDirection}",
                    isDay = DayCode.Invalid,
                    weatherCode = getEnumByValue<WeatherCode, Int?>
                        (value = hourlyData.weatherCode?.get(i) ?: -1) { it.code }
                        ?: WeatherCode.Invalid
                )
            }
        }

        fun getMockData(): Weather {
            return Weather(
                time = "07:00",
                temperature = "${23.7f.roundToInt()}°C",
                windSpeed = "8km/h",
                windDirection = "198°",
                isDay = DayCode.Day,
                weatherCode = WeatherCode.ClearSky
            )
        }

        fun fromDtoWeather(currentWeather: DtosWeather, units: WeatherUnit?): Weather {
            return Weather(
                time = CalendarProvider().getDetailed(currentWeather.time ?: ""),
                temperature = "${(currentWeather.temperature ?: 0.0f).roundToInt()}${units?.temperature}",
                windSpeed = "${(currentWeather.windSpeed ?: 0.0f).roundToInt()}${units?.windSpeed}",
                windDirection = "${currentWeather.windDirection ?: 0}${units?.windDirection}",
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


