package com.github.muhwyndhamhp.ktorweather.modules.forecast

import io.mockk.MockKAnnotations.init
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ForecastUsecaseImplTest {

    @MockK
    lateinit var repository: ForecastRepository

    @BeforeEach
    fun setUp() {
        return init(this, relaxUnitFun = false)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getSevenDaysForecast() {
    }
}