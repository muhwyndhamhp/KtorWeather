package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ForecastUsecaseImplTest {

    @MockK
    lateinit var repository: ForecastRepository

    @InjectMockKs
    lateinit var usecase: ForecastUsecase

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getSevenDaysForecast() {
        coEvery { repository.getLatestForecastData(-7.5, 110.875) } returns flowOf(
            Result.success(
                WeatherData(
                    id = null,
                    lat = null,
                    lng = null,
                    timeZone = null,
                    timeZoneAbbr = null,
                    elevation = null,
                    weatherUnit = null,
                    weatherIntSeconds = null,
                    currentWeather = null,
                    hourlyUnits = null,
                    hourly = null
                )
            )
        )
        runBlocking {
        }
    }
}