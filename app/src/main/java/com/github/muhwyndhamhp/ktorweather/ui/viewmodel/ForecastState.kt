package com.github.muhwyndhamhp.ktorweather.ui.viewmodel

import androidx.annotation.StringRes
import com.github.muhwyndhamhp.ktorweather.models.DailyWeather

sealed class ForecastState {
    @Suppress("unused")
    class Loading(@StringRes val loadingMessage: Int) : ForecastState()

    class Success(val dailyWeather: DailyWeather) : ForecastState()

    @Suppress("unused")
    class Error(val errorMessage: String) : ForecastState()

}
