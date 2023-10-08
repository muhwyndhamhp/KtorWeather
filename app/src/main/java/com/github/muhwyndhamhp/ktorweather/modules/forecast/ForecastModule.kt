package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.datasource.OpenMeteoServicePipe
import com.github.muhwyndhamhp.ktorweather.modules.forecast.usecase.GetSevenDaysForecastUsecaseImpl
import com.github.muhwyndhamhp.ktorweather.modules.forecast.usecase.GetSevenDaysForecastUsecase
import com.github.muhwyndhamhp.ktorweather.utils.CalendarProvider
import org.koin.dsl.module


val forecastModule = module {
    factory { provideForecastRepo(get()) }
    factory { provideGetSevenDaysForecastUsecase(get(), get()) }
}

fun provideForecastRepo(service: OpenMeteoServicePipe): ForecastRepository {
    return ForecastRepositoryImpl(service)
}

fun provideGetSevenDaysForecastUsecase(repo: ForecastRepository, calendar : CalendarProvider): GetSevenDaysForecastUsecase {
    return GetSevenDaysForecastUsecaseImpl(repo, calendar)
}
