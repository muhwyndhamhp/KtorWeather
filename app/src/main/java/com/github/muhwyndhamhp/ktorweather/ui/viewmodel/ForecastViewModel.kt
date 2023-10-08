package com.github.muhwyndhamhp.ktorweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.muhwyndhamhp.ktorweather.R
import com.github.muhwyndhamhp.ktorweather.modules.forecast.usecase.GetSevenDaysForecastUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ForecastViewModel(
    private val targetDispatcher: CoroutineContext,
    private val getSevenDaysForecastUsecase: GetSevenDaysForecastUsecase
) : ViewModel() {
    init {
        getForecastData()
    }

    private val _forecast =
        MutableStateFlow<ForecastState>(ForecastState.Loading(R.string.loading_forecast_data))

    val forecast = _forecast.asStateFlow()

    private fun getForecastData() {
        viewModelScope.launch(targetDispatcher) {
            getSevenDaysForecastUsecase.flows(-7.5561, 110.8317).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { dailyWeather ->
                        _forecast.value = ForecastState.Success(dailyWeather)
                    }
                } else {
                    _forecast.value = ForecastState.Error(it.exceptionOrNull()?.message ?: "")
                }
            }
        }
    }
}