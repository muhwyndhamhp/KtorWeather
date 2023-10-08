package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.datasource.OpenMeteoServicePipe
import com.github.muhwyndhamhp.ktorweather.utils.CalendarProvider
import org.koin.dsl.module


val forecastModule = module {
    factory { provideForecastRepo(get()) }
    factory { provideForecastUsecase(get(), get()) }
}

fun provideForecastRepo(service: OpenMeteoServicePipe): ForecastRepository {
    return ForecastRepositoryImpl(service)
}

fun provideForecastUsecase(repo: ForecastRepository, calendar : CalendarProvider): ForecastUsecase {
    return ForecastUsecaseImpl(repo, calendar)
}
