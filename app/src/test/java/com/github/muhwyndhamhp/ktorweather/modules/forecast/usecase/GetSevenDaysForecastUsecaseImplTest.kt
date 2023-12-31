package com.github.muhwyndhamhp.ktorweather.modules.forecast.usecase

import com.github.muhwyndhamhp.ktorweather.dtos.HourlyData
import com.github.muhwyndhamhp.ktorweather.dtos.Weather
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherHourlyUnits
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherUnit
import com.github.muhwyndhamhp.ktorweather.models.DayCode
import com.github.muhwyndhamhp.ktorweather.models.WeatherCode
import com.github.muhwyndhamhp.ktorweather.modules.forecast.ForecastRepository
import com.github.muhwyndhamhp.ktorweather.utils.CalendarProvider
import com.github.muhwyndhamhp.ktorweather.utils.Constants.TIME_FORMAT
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@ExtendWith(MockKExtension::class)
class GetSevenDaysForecastUsecaseImplTest {

    @MockK
    lateinit var repository: ForecastRepository

    @MockK
    lateinit var calendarProvider: CalendarProvider

    @InjectMockKs
    lateinit var getSevenDaysForecastUsecase: GetSevenDaysForecastUsecaseImpl

    @Test
    fun `get current weather, today's hourly weather, and next 7 day's daily weather given valid weather data input`() {
        coEvery { repository.getLatestForecastData(-7.5, 110.875) } returns flowOf(
            Result.success(
                responseMock
            )
        )

        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)
        cal.time = dateFormat.parse("2023-10-07T00:00")!!
        every { calendarProvider.getInstance() } returns cal

        runBlocking {
            val result = getSevenDaysForecastUsecase.flows(-7.5, 110.875).first()
            assert(result.isSuccess)
            assert(result.getOrNull() != null)
            result.getOrNull()?.let { dailyWeather ->
                assert(dailyWeather.currentWeather.weatherCode == WeatherCode.SlightRainShower)
                assert(dailyWeather.currentWeather.isDay == DayCode.Night)
                assert(dailyWeather.nextWeathers.size == 7)
                assert(dailyWeather.todayWeathers.size == 23)
            }
        }
    }

    @Test
    fun `returns result failure given failed weather data result`() {
        coEvery { repository.getLatestForecastData(-7.5, 110.875) } returns flowOf(
            Result.failure(Exception("Data Empty"))
        )

        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)
        cal.time = dateFormat.parse("2023-10-07T00:00")!!
        every { calendarProvider.getInstance() } returns cal

        runBlocking {
            val result = getSevenDaysForecastUsecase.flows(-7.5, 110.875).first()
            assert(result.isFailure)
            assert(result.getOrNull() == null)
            assert(result.exceptionOrNull() != null)
            result.exceptionOrNull()?.let { exception ->
                assert(exception.message == "No Data")
            }
        }
    }

    private val responseMock =
        WeatherData(
            lat = -7.5,
            lng = 110.875,
            timeZone = "Asia/Bangkok",
            timeZoneAbbr = "+07",
            elevation = 100,
            weatherIntSeconds = 900,
            weatherUnit = WeatherUnit(
                time = "iso8601",
                temperature = "° C",
                windSpeed = "km / h",
                windDirection = "°",
                isDay = "0",
                weatherCode = null,
            ), currentWeather = Weather(
                temperature = 26.6f,
                windSpeed = 8.8f,
                windDirection = 199,
                isDay = 0,
                weatherCode = 80,
                time = "2023-10-07T20:45"
            ), hourlyUnits = WeatherHourlyUnits(
                relativeHumidity = "%",
                temperature = "°C",
                time = "iso8601",
                weatherCode = "wmo code",
                windDirection = null,
                windSpeed = "km / h",
            ), hourly = HourlyData(
                relativeHumidity = listOf(
                    80,
                    81,
                    83,
                    84,
                    85,
                    85,
                    84,
                    75,
                    64,
                    55,
                    46,
                    40,
                    35,
                    35,
                    34,
                    43,
                    46,
                    52,
                    56,
                    61,
                    69,
                    75,
                    78,
                    80,
                    81,
                    83,
                    85,
                    86,
                    87,
                    88,
                    87,
                    79,
                    68,
                    56,
                    47,
                    41,
                    37,
                    32,
                    26,
                    22,
                    31,
                    53,
                    59,
                    57,
                    61,
                    64,
                    68,
                    73,
                    78,
                    82,
                    83,
                    83,
                    82,
                    82,
                    81,
                    73,
                    62,
                    52,
                    42,
                    35,
                    31,
                    28,
                    25,
                    25,
                    24,
                    29,
                    50,
                    55,
                    57,
                    62,
                    68,
                    71,
                    72,
                    74,
                    75,
                    77,
                    79,
                    81,
                    80,
                    72,
                    62,
                    54,
                    45,
                    38,
                    33,
                    30,
                    31,
                    37,
                    43,
                    54,
                    57,
                    59,
                    63,
                    66,
                    70,
                    74,
                    79,
                    82,
                    85,
                    86,
                    86,
                    83,
                    78,
                    71,
                    59,
                    45,
                    33,
                    26,
                    23,
                    22,
                    26,
                    33,
                    40,
                    47,
                    54,
                    60,
                    66,
                    71,
                    75,
                    77,
                    78,
                    79,
                    80,
                    81,
                    81,
                    79,
                    76,
                    70,
                    59,
                    46,
                    35,
                    30,
                    28,
                    27,
                    30,
                    37,
                    45,
                    53,
                    61,
                    67,
                    71,
                    73,
                    75,
                    78,
                    80,
                    82,
                    84,
                    86,
                    86,
                    83,
                    78,
                    71,
                    62,
                    50,
                    41,
                    34,
                    29,
                    27,
                    31,
                    38,
                    46,
                    54,
                    63,
                    69,
                    72,
                    73,
                    74,
                    76
                ),
                temperature = listOf(
                    25.9f,
                    25.7f,
                    25.4f,
                    25.3f,
                    25.1f,
                    25.0f,
                    25.3f,
                    27.2f,
                    29.3f,
                    31.4f,
                    33.6f,
                    35.4f,
                    36.5f,
                    36.7f,
                    36.8f,
                    34.6f,
                    33.1f,
                    31.3f,
                    29.6f,
                    28.4f,
                    27.2f,
                    26.5f,
                    25.9f,
                    25.6f,
                    25.4f,
                    25.1f,
                    24.9f,
                    24.7f,
                    24.6f,
                    24.5f,
                    24.7f,
                    26.1f,
                    28.3f,
                    30.8f,
                    33.1f,
                    34.7f,
                    35.9f,
                    37.0f,
                    37.9f,
                    38.4f,
                    36.4f,
                    32.4f,
                    30.3f,
                    29.8f,
                    29.3f,
                    28.7f,
                    27.8f,
                    27.0f,
                    26.0f,
                    25.2f,
                    24.7f,
                    24.3f,
                    24.1f,
                    23.9f,
                    24.1f,
                    26.4f,
                    28.7f,
                    31.3f,
                    33.8f,
                    35.8f,
                    36.9f,
                    37.6f,
                    38.1f,
                    38.0f,
                    37.7f,
                    36.2f,
                    32.0f,
                    30.9f,
                    30.2f,
                    29.4f,
                    28.5f,
                    27.9f,
                    27.2f,
                    26.6f,
                    26.2f,
                    25.7f,
                    25.4f,
                    25.2f,
                    25.4f,
                    27.0f,
                    29.1f,
                    31.4f,
                    33.5f,
                    35.3f,
                    36.7f,
                    37.7f,
                    37.0f,
                    34.2f,
                    32.6f,
                    29.9f,
                    28.1f,
                    27.3f,
                    26.5f,
                    26.1f,
                    25.6f,
                    25.1f,
                    24.5f,
                    24.0f,
                    23.6f,
                    23.2f,
                    23.1f,
                    23.5f,
                    24.1f,
                    25.4f,
                    27.7f,
                    30.5f,
                    33.0f,
                    35.0f,
                    36.7f,
                    37.3f,
                    36.4f,
                    34.5f,
                    32.5f,
                    30.5f,
                    28.5f,
                    26.9f,
                    26.0f,
                    25.6f,
                    25.2f,
                    25.0f,
                    24.8f,
                    24.7f,
                    24.4f,
                    24.2f,
                    24.2f,
                    24.5f,
                    25.0f,
                    26.2f,
                    28.5f,
                    31.4f,
                    33.9f,
                    35.5f,
                    36.7f,
                    37.4f,
                    36.6f,
                    34.8f,
                    32.9f,
                    31.0f,
                    28.9f,
                    27.4f,
                    26.6f,
                    26.3f,
                    26.0f,
                    25.7f,
                    25.3f,
                    25.0f,
                    24.7f,
                    24.4f,
                    24.4f,
                    24.7f,
                    25.3f,
                    26.5f,
                    28.8f,
                    31.6f,
                    34.0f,
                    35.9f,
                    37.3f,
                    37.8f,
                    37.1f,
                    35.4f,
                    33.6f,
                    31.6f,
                    29.4f,
                    27.7f,
                    27.0f,
                    26.8f,
                    26.6f,
                    26.3f
                ),
                time = listOf(
                    "2023-10-07T00:00",
                    "2023-10-07T01:00",
                    "2023-10-07T02:00",
                    "2023-10-07T03:00",
                    "2023-10-07T04:00",
                    "2023-10-07T05:00",
                    "2023-10-07T06:00",
                    "2023-10-07T07:00",
                    "2023-10-07T08:00",
                    "2023-10-07T09:00",
                    "2023-10-07T10:00",
                    "2023-10-07T11:00",
                    "2023-10-07T12:00",
                    "2023-10-07T13:00",
                    "2023-10-07T14:00",
                    "2023-10-07T15:00",
                    "2023-10-07T16:00",
                    "2023-10-07T17:00",
                    "2023-10-07T18:00",
                    "2023-10-07T19:00",
                    "2023-10-07T20:00",
                    "2023-10-07T21:00",
                    "2023-10-07T22:00",
                    "2023-10-07T23:00",
                    "2023-10-08T00:00",
                    "2023-10-08T01:00",
                    "2023-10-08T02:00",
                    "2023-10-08T03:00",
                    "2023-10-08T04:00",
                    "2023-10-08T05:00",
                    "2023-10-08T06:00",
                    "2023-10-08T07:00",
                    "2023-10-08T08:00",
                    "2023-10-08T09:00",
                    "2023-10-08T10:00",
                    "2023-10-08T11:00",
                    "2023-10-08T12:00",
                    "2023-10-08T13:00",
                    "2023-10-08T14:00",
                    "2023-10-08T15:00",
                    "2023-10-08T16:00",
                    "2023-10-08T17:00",
                    "2023-10-08T18:00",
                    "2023-10-08T19:00",
                    "2023-10-08T20:00",
                    "2023-10-08T21:00",
                    "2023-10-08T22:00",
                    "2023-10-08T23:00",
                    "2023-10-09T00:00",
                    "2023-10-09T01:00",
                    "2023-10-09T02:00",
                    "2023-10-09T03:00",
                    "2023-10-09T04:00",
                    "2023-10-09T05:00",
                    "2023-10-09T06:00",
                    "2023-10-09T07:00",
                    "2023-10-09T08:00",
                    "2023-10-09T09:00",
                    "2023-10-09T10:00",
                    "2023-10-09T11:00",
                    "2023-10-09T12:00",
                    "2023-10-09T13:00",
                    "2023-10-09T14:00",
                    "2023-10-09T15:00",
                    "2023-10-09T16:00",
                    "2023-10-09T17:00",
                    "2023-10-09T18:00",
                    "2023-10-09T19:00",
                    "2023-10-09T20:00",
                    "2023-10-09T21:00",
                    "2023-10-09T22:00",
                    "2023-10-09T23:00",
                    "2023-10-10T00:00",
                    "2023-10-10T01:00",
                    "2023-10-10T02:00",
                    "2023-10-10T03:00",
                    "2023-10-10T04:00",
                    "2023-10-10T05:00",
                    "2023-10-10T06:00",
                    "2023-10-10T07:00",
                    "2023-10-10T08:00",
                    "2023-10-10T09:00",
                    "2023-10-10T10:00",
                    "2023-10-10T11:00",
                    "2023-10-10T12:00",
                    "2023-10-10T13:00",
                    "2023-10-10T14:00",
                    "2023-10-10T15:00",
                    "2023-10-10T16:00",
                    "2023-10-10T17:00",
                    "2023-10-10T18:00",
                    "2023-10-10T19:00",
                    "2023-10-10T20:00",
                    "2023-10-10T21:00",
                    "2023-10-10T22:00",
                    "2023-10-10T23:00",
                    "2023-10-11T00:00",
                    "2023-10-11T01:00",
                    "2023-10-11T02:00",
                    "2023-10-11T03:00",
                    "2023-10-11T04:00",
                    "2023-10-11T05:00",
                    "2023-10-11T06:00",
                    "2023-10-11T07:00",
                    "2023-10-11T08:00",
                    "2023-10-11T09:00",
                    "2023-10-11T10:00",
                    "2023-10-11T11:00",
                    "2023-10-11T12:00",
                    "2023-10-11T13:00",
                    "2023-10-11T14:00",
                    "2023-10-11T15:00",
                    "2023-10-11T16:00",
                    "2023-10-11T17:00",
                    "2023-10-11T18:00",
                    "2023-10-11T19:00",
                    "2023-10-11T20:00",
                    "2023-10-11T21:00",
                    "2023-10-11T22:00",
                    "2023-10-11T23:00",
                    "2023-10-12T00:00",
                    "2023-10-12T01:00",
                    "2023-10-12T02:00",
                    "2023-10-12T03:00",
                    "2023-10-12T04:00",
                    "2023-10-12T05:00",
                    "2023-10-12T06:00",
                    "2023-10-12T07:00",
                    "2023-10-12T08:00",
                    "2023-10-12T09:00",
                    "2023-10-12T10:00",
                    "2023-10-12T11:00",
                    "2023-10-12T12:00",
                    "2023-10-12T13:00",
                    "2023-10-12T14:00",
                    "2023-10-12T15:00",
                    "2023-10-12T16:00",
                    "2023-10-12T17:00",
                    "2023-10-12T18:00",
                    "2023-10-12T19:00",
                    "2023-10-12T20:00",
                    "2023-10-12T21:00",
                    "2023-10-12T22:00",
                    "2023-10-12T23:00",
                    "2023-10-13T00:00",
                    "2023-10-13T01:00",
                    "2023-10-13T02:00",
                    "2023-10-13T03:00",
                    "2023-10-13T04:00",
                    "2023-10-13T05:00",
                    "2023-10-13T06:00",
                    "2023-10-13T07:00",
                    "2023-10-13T08:00",
                    "2023-10-13T09:00",
                    "2023-10-13T10:00",
                    "2023-10-13T11:00",
                    "2023-10-13T12:00",
                    "2023-10-13T13:00",
                    "2023-10-13T14:00",
                    "2023-10-13T15:00",
                    "2023-10-13T16:00",
                    "2023-10-13T17:00",
                    "2023-10-13T18:00",
                    "2023-10-13T19:00",
                    "2023-10-13T20:00",
                    "2023-10-13T21:00",
                    "2023-10-13T22:00",
                    "2023-10-13T23:00"
                ),
                weatherCode = listOf(
                    2,
                    2,
                    1,
                    2,
                    2,
                    2,
                    2,
                    2,
                    1,
                    1,
                    1,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    0,
                    2,
                    3,
                    80,
                    80,
                    80,
                    3,
                    3,
                    2,
                    2,
                    2,
                    2,
                    2,
                    3,
                    2,
                    2,
                    2,
                    1,
                    2,
                    1,
                    2,
                    2,
                    2,
                    3,
                    1,
                    1,
                    2,
                    2,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    0,
                    1,
                    1,
                    2,
                    0,
                    0,
                    0,
                    1,
                    1,
                    2,
                    1,
                    2,
                    1,
                    0,
                    3,
                    2,
                    2,
                    3,
                    3,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    3,
                    2,
                    1,
                    1,
                    0,
                    0,
                    1,
                    1,
                    1,
                    2,
                    3,
                    2,
                    2,
                    2,
                    3,
                    3,
                    3,
                    3,
                    3,
                    2,
                    2,
                    2,
                    1,
                    1,
                    1,
                    2,
                    2,
                    2,
                    0,
                    0,
                    0,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    1,
                    1,
                    1,
                    0,
                    0,
                    0,
                    2,
                    2,
                    2,
                    1,
                    1,
                    1,
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    3,
                    2,
                    2,
                    2,
                    3,
                    3,
                    3,
                    1,
                    1,
                    1,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    2,
                    1
                ),
                windDirection = null,
                windSpeed = listOf(
                    8.1f,
                    8.9f,
                    8.4f,
                    7.9f,
                    7.5f,
                    7.3f,
                    7.8f,
                    11.3f,
                    12.9f,
                    12.8f,
                    11.6f,
                    9.4f,
                    6.2f,
                    6.1f,
                    6.9f,
                    18.4f,
                    19.4f,
                    20.4f,
                    11.2f,
                    8.7f,
                    9.2f,
                    8.6f,
                    9.3f,
                    8.5f,
                    7.9f,
                    8.0f,
                    7.8f,
                    7.9f,
                    7.7f,
                    7.6f,
                    7.4f,
                    10.0f,
                    10.8f,
                    9.7f,
                    6.4f,
                    3.1f,
                    6.5f,
                    7.1f,
                    8.4f,
                    5.8f,
                    11.6f,
                    16.1f,
                    10.3f,
                    5.6f,
                    1.8f,
                    2.2f,
                    1.8f,
                    1.8f,
                    2.7f,
                    3.8f,
                    4.3f,
                    4.1f,
                    4.3f,
                    4.1f,
                    3.8f,
                    7.4f,
                    9.0f,
                    8.3f,
                    7.4f,
                    4.7f,
                    2.3f,
                    7.9f,
                    11.4f,
                    12.4f,
                    5.8f,
                    4.2f,
                    7.4f,
                    3.3f,
                    1.8f,
                    3.6f,
                    0.7f,
                    3.1f,
                    2.8f,
                    3.2f,
                    5.2f,
                    7.9f,
                    8.8f,
                    8.7f,
                    8.9f,
                    10.5f,
                    9.8f,
                    10.4f,
                    12.4f,
                    13.4f,
                    13.8f,
                    14.3f,
                    19.7f,
                    24.7f,
                    24.3f,
                    23.2f,
                    19.2f,
                    14.0f,
                    12.2f,
                    10.7f,
                    10.0f,
                    9.5f,
                    9.5f,
                    9.2f,
                    8.9f,
                    8.5f,
                    8.7f,
                    9.5f,
                    10.6f,
                    11.3f,
                    11.0f,
                    10.2f,
                    9.3f,
                    8.0f,
                    6.6f,
                    7.3f,
                    12.3f,
                    19.5f,
                    23.9f,
                    23.1f,
                    20.2f,
                    17.9f,
                    16.6f,
                    15.6f,
                    14.6f,
                    13.8f,
                    13.3f,
                    12.5f,
                    11.2f,
                    10.2f,
                    9.7f,
                    10.5f,
                    12.4f,
                    13.8f,
                    15.0f,
                    15.8f,
                    15.7f,
                    13.3f,
                    9.2f,
                    6.5f,
                    11.4f,
                    19.1f,
                    23.6f,
                    22.2f,
                    18.4f,
                    15.8f,
                    14.6f,
                    13.9f,
                    13.2f,
                    12.2f,
                    11.2f,
                    10.2f,
                    9.4f,
                    8.8f,
                    9.0f,
                    10.3f,
                    12.2f,
                    13.1f,
                    13.7f,
                    13.4f,
                    11.9f,
                    6.2f,
                    4.8f,
                    7.4f,
                    5.4f,
                    16.7f,
                    25.3f,
                    24.5f,
                    19.9f,
                    16.8f,
                    15.9f,
                    14.8f,
                    13.8f,
                    12.8f
                ),
            )
        )
}