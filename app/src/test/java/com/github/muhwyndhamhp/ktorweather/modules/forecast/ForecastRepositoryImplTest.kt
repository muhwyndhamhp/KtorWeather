package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.datasource.OpenMeteoServicePipe
import com.github.muhwyndhamhp.ktorweather.datasource.provideOkHTTPClient
import com.github.muhwyndhamhp.ktorweather.datasource.provideOpenMeteoService
import com.github.muhwyndhamhp.ktorweather.datasource.provideOpenMeteoServicePipe
import com.github.muhwyndhamhp.ktorweather.datasource.provideRetrofit
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ForecastRepositoryImplTest {
    private lateinit var service: OpenMeteoServicePipe

    private lateinit var server: MockWebServer

    private lateinit var repo: ForecastRepository

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `returns weather data given success network result`() {
        server.enqueue(MockResponse().setBody(jsonResp))
        server.start()
        val httpUrl = server.url("")
        val retro = provideRetrofit(provideOkHTTPClient(), httpUrl)
        val retroService = provideOpenMeteoService(retro)
        service = provideOpenMeteoServicePipe(retroService)

        repo = provideForecastRepo(service)

        runBlocking {
            val results = mutableListOf<Result<WeatherData>>()
            repo.getLatestForecastData(-7.5, 110.875).take(1).toCollection(results)
            assert(results[0].isSuccess)
            assert(results[0].getOrNull() != null)
            assert(results[0].getOrNull()?.currentWeather != null)
            assert(results[0].getOrNull()?.weatherUnit != null)
            assert(results[0].getOrNull()?.hourly != null)
            assert(results[0].getOrNull()?.hourlyUnits != null)
        }
    }

    val jsonResp = """     
{
  "latitude": -7.5,
  "longitude": 110.875,
  "generationtime_ms": 0.07998943328857422,
  "utc_offset_seconds": 25200,
  "timezone": "Asia/Bangkok",
  "timezone_abbreviation": "+07",
  "elevation": 100,
  "current_weather_units": {
    "time": "iso8601",
    "temperature": "°C",
    "windspeed": "km/h",
    "winddirection": "°",
    "is_day": "",
    "weathercode": "wmo code"
  },
  "current_weather_interval_seconds": 900,
  "current_weather": {
    "time": "2023-10-07T20:45",
    "temperature": 26.6,
    "windspeed": 8.8,
    "winddirection": 199,
    "is_day": 0,
    "weathercode": 80
  },
  "hourly_units": {
    "time": "iso8601",
    "temperature_2m": "°C",
    "relativehumidity_2m": "%",
    "weathercode": "wmo code",
    "windspeed_10m": "km/h"
  },
  "hourly": {
    "time": [
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
    ],
    "temperature_2m": [
      25.9,
      25.7,
      25.4,
      25.3,
      25.1,
      25,
      25.3,
      27.2,
      29.3,
      31.4,
      33.6,
      35.4,
      36.5,
      36.7,
      36.8,
      34.6,
      33.1,
      31.3,
      29.6,
      28.4,
      27.2,
      26.5,
      25.9,
      25.6,
      25.4,
      25.1,
      24.9,
      24.7,
      24.6,
      24.5,
      24.7,
      26.1,
      28.3,
      30.8,
      33.1,
      34.7,
      35.9,
      37,
      37.9,
      38.4,
      36.4,
      32.4,
      30.3,
      29.8,
      29.3,
      28.7,
      27.8,
      27,
      26,
      25.2,
      24.7,
      24.3,
      24.1,
      23.9,
      24.1,
      26.4,
      28.7,
      31.3,
      33.8,
      35.8,
      36.9,
      37.6,
      38.1,
      38,
      37.7,
      36.2,
      32,
      30.9,
      30.2,
      29.4,
      28.5,
      27.9,
      27.2,
      26.6,
      26.2,
      25.7,
      25.4,
      25.2,
      25.4,
      27,
      29.1,
      31.4,
      33.5,
      35.3,
      36.7,
      37.7,
      37,
      34.2,
      32.6,
      29.9,
      28.1,
      27.3,
      26.5,
      26.1,
      25.6,
      25.1,
      24.5,
      24,
      23.6,
      23.2,
      23.1,
      23.5,
      24.1,
      25.4,
      27.7,
      30.5,
      33,
      35,
      36.7,
      37.3,
      36.4,
      34.5,
      32.5,
      30.5,
      28.5,
      26.9,
      26,
      25.6,
      25.2,
      25,
      24.8,
      24.7,
      24.4,
      24.2,
      24.2,
      24.5,
      25,
      26.2,
      28.5,
      31.4,
      33.9,
      35.5,
      36.7,
      37.4,
      36.6,
      34.8,
      32.9,
      31,
      28.9,
      27.4,
      26.6,
      26.3,
      26,
      25.7,
      25.3,
      25,
      24.7,
      24.4,
      24.4,
      24.7,
      25.3,
      26.5,
      28.8,
      31.6,
      34,
      35.9,
      37.3,
      37.8,
      37.1,
      35.4,
      33.6,
      31.6,
      29.4,
      27.7,
      27,
      26.8,
      26.6,
      26.3
    ],
    "relativehumidity_2m": [
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
    ],
    "weathercode": [
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
    ],
    "windspeed_10m": [
      8.1,
      8.9,
      8.4,
      7.9,
      7.5,
      7.3,
      7.8,
      11.3,
      12.9,
      12.8,
      11.6,
      9.4,
      6.2,
      6.1,
      6.9,
      18.4,
      19.4,
      20.4,
      11.2,
      8.7,
      9.2,
      8.6,
      9.3,
      8.5,
      7.9,
      8,
      7.8,
      7.9,
      7.7,
      7.6,
      7.4,
      10,
      10.8,
      9.7,
      6.4,
      3.1,
      6.5,
      7.1,
      8.4,
      5.8,
      11.6,
      16.1,
      10.3,
      5.6,
      1.8,
      2.2,
      1.8,
      1.8,
      2.7,
      3.8,
      4.3,
      4.1,
      4.3,
      4.1,
      3.8,
      7.4,
      9,
      8.3,
      7.4,
      4.7,
      2.3,
      7.9,
      11.4,
      12.4,
      5.8,
      4.2,
      7.4,
      3.3,
      1.8,
      3.6,
      0.7,
      3.1,
      2.8,
      3.2,
      5.2,
      7.9,
      8.8,
      8.7,
      8.9,
      10.5,
      9.8,
      10.4,
      12.4,
      13.4,
      13.8,
      14.3,
      19.7,
      24.7,
      24.3,
      23.2,
      19.2,
      14,
      12.2,
      10.7,
      10,
      9.5,
      9.5,
      9.2,
      8.9,
      8.5,
      8.7,
      9.5,
      10.6,
      11.3,
      11,
      10.2,
      9.3,
      8,
      6.6,
      7.3,
      12.3,
      19.5,
      23.9,
      23.1,
      20.2,
      17.9,
      16.6,
      15.6,
      14.6,
      13.8,
      13.3,
      12.5,
      11.2,
      10.2,
      9.7,
      10.5,
      12.4,
      13.8,
      15,
      15.8,
      15.7,
      13.3,
      9.2,
      6.5,
      11.4,
      19.1,
      23.6,
      22.2,
      18.4,
      15.8,
      14.6,
      13.9,
      13.2,
      12.2,
      11.2,
      10.2,
      9.4,
      8.8,
      9,
      10.3,
      12.2,
      13.1,
      13.7,
      13.4,
      11.9,
      6.2,
      4.8,
      7.4,
      5.4,
      16.7,
      25.3,
      24.5,
      19.9,
      16.8,
      15.9,
      14.8,
      13.8,
      12.8
    ]
  }
}
    """.trimIndent()
}