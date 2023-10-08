package com.github.muhwyndhamhp.ktorweather.utils

import org.koin.core.component.KoinComponent
import java.util.Calendar

class CalendarProvider() : KoinComponent{
    fun getInstance(): Calendar {
        return Calendar.getInstance()
    }

}