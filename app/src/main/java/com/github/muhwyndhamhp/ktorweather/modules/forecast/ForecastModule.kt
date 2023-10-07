package com.github.muhwyndhamhp.ktorweather.modules.forecast

import com.github.muhwyndhamhp.ktorweather.datasource.KtorWeatherDB
import com.github.muhwyndhamhp.ktorweather.datasource.OpenMeteoServicePipe
import org.koin.dsl.module


val forecastModule = module {
    factory { provideForecastRepo(get(), get()) }
    factory { provideForecastUsecase(get()) }
}

fun provideForecastRepo(service: OpenMeteoServicePipe, db: KtorWeatherDB): ForecastRepository {
    return ForecastRepositoryImpl(service, db)
}

fun provideForecastUsecase(repo: ForecastRepository): ForecastUsecase {
    return ForecastUsecaseImpl(repo)
}
