package com.github.muhwyndhamhp.ktorweather.models

import androidx.annotation.StringRes
import com.github.muhwyndhamhp.ktorweather.R

@Suppress("unused")
enum class WeatherCode(val code: Int, @StringRes val message: Int) {
    ClearSky(0, R.string.clear_sky),
    MainlyClear(1, R.string.mainly_clear),
    PartlyCloudy(2, R.string.partly_cloudy),
    Overcast(3, R.string.overcast),
    Fog(45, R.string.fog),
    RimeFogDeposit(48, R.string.depositing_rime_fog),
    LightDrizzle(51, R.string.light_drizzle),
    ModerateDrizzle(53, R.string.moderate_drizzle),
    DenseDrizzle(55, R.string.dense_drizzle),
    LightFreezingDrizzle(56, R.string.light_freezing_drizzle),
    DenseFreezingDrizzle(57, R.string.dense_freezing_drizzle),
    SlightlyRain(61, R.string.slightly_raining),
    ModerateRain(63, R.string.moderately_raining),
    HeavyRain(65, R.string.heavy_raining),
    LightFreezingRain(66, R.string.light_freezing_rain),
    HeavyFreezingRain(67, R.string.heavy_freezing_rain),
    SlightSnowfall(71, R.string.slight_snow_falling),
    ModerateSnowfall(73, R.string.moderate_snow_falling),
    HeavySnowfall(75, R.string.heavy_snow_falling),
    SnowGrains(77, R.string.snow_grains),
    SlightRainShower(80, R.string.slight_rain_shower),
    ModerateRainShower(81, R.string.moderate_rain_shower),
    ViolentRainShower(82, R.string.violent_rain_shower),
    SlightSnowShower(85, R.string.slight_snow_shower),
    HeavySnowShower(86, R.string.heavy_snow_shower),
    Thunderstorm(96, R.string.thunderstorm),
    Invalid(-1, R.string.unknown)
}
