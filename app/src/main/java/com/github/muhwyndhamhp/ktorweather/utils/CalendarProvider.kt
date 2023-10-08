package com.github.muhwyndhamhp.ktorweather.utils

import com.github.muhwyndhamhp.ktorweather.utils.Constants.TIME_FORMAT
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarProvider() : KoinComponent {
    fun getInstance(): Calendar {
        return Calendar.getInstance()
    }

    fun getDetailed(date: String): String {
        val cal = getCalendarFromDateString(date)
        val sdf = SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm", Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun getShortDate(date: String): String {
        val cal = getCalendarFromDateString(date)
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun getShortHour(date: String): String {
        val cal = getCalendarFromDateString(date)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(cal.time)
    }

    fun getCalendarFromDateString(date: String): Calendar {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        cal.time = sdf.parse(date) ?: cal.time
        return cal
    }

}