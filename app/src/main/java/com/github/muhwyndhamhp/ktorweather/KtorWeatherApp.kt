package com.github.muhwyndhamhp.ktorweather

import android.app.Application
import com.github.muhwyndhamhp.ktorweather.datasource.dataSourceModule
import com.github.muhwyndhamhp.ktorweather.modules.forecast.forecastModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KtorWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            this.androidLogger()
            modules(dataSourceModule, forecastModule)
        }
    }
}