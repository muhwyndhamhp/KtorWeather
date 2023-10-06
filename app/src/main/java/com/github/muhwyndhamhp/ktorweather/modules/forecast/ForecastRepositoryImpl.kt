package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.datasource.KtorWeatherDB
import com.github.muhwyndhamhp.ktorweather.datasource.OpenMeteoService
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import com.github.muhwyndhamhp.ktorweather.dtos.WeatherDataEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit

class ForecastRepositoryImpl(private val service: OpenMeteoService, private val db: KtorWeatherDB) : ForecastRepository, KoinComponent {
    @OptIn(FlowPreview::class)
    override suspend fun getLatestForecastData(lat: Double, lng: Double): Flow<Result<WeatherData>> {
        val dbFlow = db.weatherDao().getLastWeatherData()
        val networkFlow = service.GetForecast(lat, lng)

        return dbFlow.flatMapConcat { dbData ->
            if (dbData == null) {
                collectNetworkToDB(networkFlow)
            } else {
                val format = SimpleDateFormat("yyyy-MM-ddTHH:mm", java.util.Locale.ENGLISH)
                val lastWeatherTime = format.parse(dbData.currentWeather?.time ?: "")
                val now = Calendar.getInstance().time

                if (now.time - (lastWeatherTime?.time ?: 0) > TimeUnit.MINUTES.toMillis(30)) {
                    collectNetworkToDB(networkFlow)
                }
            }
            val data = dbData?.weatherData?.copy(
                weatherUnit = dbData.weatherUnit,
                currentWeather = dbData.currentWeather,
                hourlyUnits = dbData.hourlyUnits,
                hourly = dbData.hourly,
            )
            flowOf(Result.success(data ?: return@flatMapConcat flowOf(Result.failure(Exception("")))))
        }
    }

    private suspend fun collectNetworkToDB(networkFlow: Flow<WeatherData?>) {
        networkFlow.collect { networkData ->
            val entity = WeatherDataEntity(
                weatherData = networkData,
                weatherUnit = networkData?.weatherUnit,
                currentWeather = networkData?.currentWeather,
                hourlyUnits = networkData?.hourlyUnits,
                hourly = networkData?.hourly
            )
            db.weatherDao().insertWeatherDataEntity(entity)
        }
    }
}