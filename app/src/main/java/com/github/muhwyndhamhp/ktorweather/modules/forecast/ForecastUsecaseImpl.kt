package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.dtos.WeatherData
import com.github.muhwyndhamhp.ktorweather.models.DailyWeather
import com.github.muhwyndhamhp.ktorweather.models.Weather
import com.github.muhwyndhamhp.ktorweather.utils.CalendarProvider
import com.github.muhwyndhamhp.ktorweather.utils.Constants.TIME_FORMAT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.github.muhwyndhamhp.ktorweather.dtos.Weather as DtosWeather

class ForecastUsecaseImpl(
    private val repo: ForecastRepository,
    private val calendar: CalendarProvider
) : ForecastUsecase, KoinComponent {

    companion object {
    }

    override suspend fun getSevenDaysForecast(
        lat: Double,
        lng: Double
    ): Flow<Result<DailyWeather>> {
        return repo.getLatestForecastData(lat, lng).map { result ->
            if (result.isSuccess) {
                return@map extractDailyWeather(result)
            } else {
                Result.failure<DailyWeather>(
                    result.exceptionOrNull() ?: Exception("No Data")
                )
            }
            Result.failure(Exception("No Data"))
        }
    }

    private fun extractDailyWeather(result: Result<WeatherData>): Result<DailyWeather> {
        result.getOrNull()?.let { data ->
            data.hourly?.time?.let { times ->
                val todayIndexes = getTodayIndexes(times)
                val dailyIndexes = getDailyIndexes(times, data.currentWeather)
                val todayWeather = Weather.extractFromIndexes(
                    todayIndexes,
                    data.hourly
                )
                val dailyWeather = Weather.extractFromIndexes(
                    dailyIndexes,
                    data.hourly
                )
                return Result.success(
                    DailyWeather(
                        currentWeather = Weather.fromDtoWeather(
                            data.currentWeather
                                ?: return Result.failure(Exception("current weather not found"))
                        ),
                        nextWeathers = dailyWeather,
                        todayWeathers = todayWeather
                    )
                )
            }
        }
        return Result.failure(Exception("result is null"))
    }

    private fun getDailyIndexes(times: List<String?>, currentWeather: DtosWeather?): List<Int> {
        val format = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = format.parse(currentWeather?.time ?: "") ?: cal.time

        cal.set(Calendar.MINUTE, 0)
        cal.add(Calendar.HOUR, 1)

        return times.mapIndexed { index: Int, time ->
            val wTime = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)
            wTime.time = dateFormat.parse(time ?: "") ?: wTime.time

            if (wTime.get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY)) {
                index
            } else {
                null
            }
        }.filterNotNull()
    }

    private fun getTodayIndexes(it: List<String?>): List<Int> {
        val datePairs = getRelevantDates()
        val startDay = datePairs.first
        val nextDay = datePairs.second

        // This is a convenient code rather than efficient code!
        // The "more efficient" way to do this is to stop iterating after the last index is found
        // because the data is ordered in ascending order.
        return it.mapIndexed { index, time ->
            val format = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)
            val dateTime = format.parse(time ?: "")
            if (dateTime?.after(startDay) == true && dateTime.before(nextDay)) {
                index
            } else {
                null
            }
        }.filterNotNull()
    }

    private fun getRelevantDates(): Pair<Date, Date> {
        val cal = calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val startDay = cal.time
        cal.add(Calendar.DATE, 1)
        val nextDay = cal.time

        return Pair(startDay, nextDay)
    }
}