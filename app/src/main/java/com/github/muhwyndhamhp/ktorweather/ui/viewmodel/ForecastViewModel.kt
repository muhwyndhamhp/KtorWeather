package com.github.muhwyndhamhp.ktorweather.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.muhwyndhamhp.ktorweather.R
import com.github.muhwyndhamhp.ktorweather.models.DailyWeather
import com.github.muhwyndhamhp.ktorweather.modules.forecast.usecase.GetSevenDaysForecastUsecase
import com.github.muhwyndhamhp.ktorweather.utils.loadData
import com.github.muhwyndhamhp.ktorweather.utils.saveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ForecastViewModel(
    private val targetDispatcher: CoroutineContext,
    private val getSevenDaysForecastUsecase: GetSevenDaysForecastUsecase,
    private val sharedPref: SharedPreferences
) : ViewModel() {

    companion object {
        const val FORECAST_CACHE_KEY = "FORECAST_CACHE_KEY"
    }

    init {
        getForecastData()
    }

    private val _forecast =
        MutableStateFlow<ForecastState>(ForecastState.Loading(R.string.loading_forecast_data))

    val forecast = _forecast.asStateFlow()

    private fun getForecastData() {
        viewModelScope.launch(targetDispatcher) {
            val existingData = sharedPref.loadData<DailyWeather>(FORECAST_CACHE_KEY)
            existingData?.let {
                _forecast.value = ForecastState.Success(it)
                if (System.currentTimeMillis() - it.fetchTime < 15 * 60 * 1000) return@launch
            }

            getSevenDaysForecastUsecase.flows(-7.5561, 110.8317).collect {
                if (it.isSuccess) {
                    it.getOrNull()?.let { dailyWeather ->
                        sharedPref.saveData(FORECAST_CACHE_KEY, dailyWeather)
                        _forecast.value = ForecastState.Success(dailyWeather)
                    }
                } else {
                    _forecast.value = ForecastState.Error(it.exceptionOrNull()?.message ?: "")
                }
            }
        }
    }
}