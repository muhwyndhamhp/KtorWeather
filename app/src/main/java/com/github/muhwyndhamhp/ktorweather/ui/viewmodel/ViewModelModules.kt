package com.github.muhwyndhamhp.ktorweather.ui.viewmodel

import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModules = module {
    viewModel { ForecastViewModel(Dispatchers.IO, get()) }
}