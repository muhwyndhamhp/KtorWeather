package com.github.muhwyndhamhp.ktorweather.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.muhwyndhamhp.ktorweather.models.DailyWeather
import com.github.muhwyndhamhp.ktorweather.models.Weather
import com.github.muhwyndhamhp.ktorweather.models.WeatherCode
import com.github.muhwyndhamhp.ktorweather.ui.theme.KtorWeatherTheme
import com.github.muhwyndhamhp.ktorweather.ui.viewmodel.ForecastState
import com.github.muhwyndhamhp.ktorweather.ui.viewmodel.ForecastViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForecastActivity : ComponentActivity() {

    private val viewModel: ForecastViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorWeatherTheme {
                ForecastAct(viewModel = viewModel, LocalConfiguration.current.orientation)
            }
        }
    }
}

@Composable
fun ForecastAct(viewModel: ForecastViewModel, orientation: Int) {
    when (val state = viewModel.forecast.collectAsState().value) {
        is ForecastState.Error -> Unit
        is ForecastState.Loading -> ForecastLoading(message = state.loadingMessage)
        is ForecastState.Success -> Forecast(state.dailyWeather, orientation)
    }
}

@Composable
fun ForecastLoading(@StringRes message: Int) {
    
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 60.dp, end = 60.dp, top = 30.dp),
    ) {
        LinearProgressIndicator(
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = message),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun Forecast(dailyWeather: DailyWeather, orientation: Int) {
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 30.dp),
        ) {
            ForecastBody(dailyWeather = dailyWeather, orientation = orientation)
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(start = 10.dp, bottom = 10.dp, top = 10.dp),
        ) {
            ForecastBody(dailyWeather = dailyWeather, orientation = orientation)
        }
    }
}

@Composable
fun ForecastBody(dailyWeather: DailyWeather, orientation: Int) {
    val shape = if (orientation == Configuration.ORIENTATION_PORTRAIT)
        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    else
        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)

    CurrentWeather(currentWeather = dailyWeather.currentWeather)
    Column(
        modifier = Modifier
            .clip(shape = shape)
            .background(MaterialTheme.colorScheme.surfaceDim)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 20.dp)
    ) {
        TodayWeather(todayWeather = dailyWeather.todayWeathers, true)
        DailyWeather(dailyWeather = dailyWeather.nextWeathers, true)
    }
}

@Composable
fun CurrentWeather(currentWeather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 80.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = currentWeather.temperature,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp),
        )
        Text(
            text = stringResource(id = currentWeather.weatherCode.message),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(15.dp),
        )
        Text(
            text = currentWeather.time,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayWeather(todayWeather: List<Weather>, isPortrait: Boolean) {
    val state = rememberLazyListState()
    Column(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Text(
            text = "Today's Weathers",
            color = MaterialTheme.colorScheme.tertiary,
        )
        LazyRow(
            modifier = if (isPortrait) Modifier.fillMaxWidth() else Modifier,
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
        ) {
            items(todayWeather.size) { index ->
                SnippetWeather(weather = todayWeather[index])
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DailyWeather(dailyWeather: List<Weather>, isPortrait: Boolean) {
    val state = rememberLazyListState()
    Column {
        Text(
            text = "Weather's for the Week",
            color = MaterialTheme.colorScheme.tertiary,
        )
        LazyRow(
            modifier = if (isPortrait) Modifier.fillMaxWidth() else Modifier.fillMaxHeight(),
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
        ) {
            items(dailyWeather.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SnippetWeather(weather = dailyWeather[index])
                }
            }
        }
    }
}

@Composable
fun SnippetWeather(weather: Weather) {
    Column(
        modifier = Modifier.padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = weather.time,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = weather.temperature,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 5.dp)
        )
        if (weather.weatherCode != WeatherCode.Invalid) {
            Text(
                text = stringResource(id = weather.weatherCode.message),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastPreview() {
    KtorWeatherTheme {
        Forecast(dailyWeather = mockDailyWeather, Configuration.ORIENTATION_PORTRAIT)
    }
}

private val mockDailyWeather = DailyWeather.getMockDailyWeather()