package com.github.muhwyndhamhp.ktorweather.utils

import org.koin.dsl.module

val utilsModule = module {
    factory { provideCalendarProvider() }
}

fun provideCalendarProvider(): CalendarProvider {
    return CalendarProvider()
}