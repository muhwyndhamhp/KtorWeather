package com.github.muhwyndhamhp.ktorweather.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.muhwyndhamhp.ktorweather.models.DailyWeather
import com.github.muhwyndhamhp.ktorweather.models.Weather
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
                ForecastAct(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CurrentWeather(currentWeather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${currentWeather.temperature}",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp)
        )
        Text(
            text = stringResource(id = currentWeather.weatherCode.message),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun SnippetWeather(weather: Weather) {
    Text(
        text = "${weather.temperature}",
        fontSize = 20.sp,
        modifier = Modifier
            .padding(15.dp)
    )
}

@Composable
fun TodayWeather(todayWeather: List<Weather>) {
    Column {
        Text(text = "Today's Weathers")
        LazyRow {
            items(todayWeather.size) { index ->
                SnippetWeather(weather = todayWeather[index])
            }
        }
    }
}

@Composable
fun DailyWeather(dailyWeather: List<Weather>) {
    Column {
        Text(text = "Weather's for the Week")
        LazyRow {
            items(dailyWeather.size) { index ->
                SnippetWeather(weather = dailyWeather[index])
            }
        }
    }
}

@Composable
fun ForecastAct(viewModel: ForecastViewModel) {
    when (val state = viewModel.forecast.collectAsState().value) {
        is ForecastState.Error -> Unit
        is ForecastState.Loading -> Unit
        is ForecastState.Success -> {
            Forecast(state.dailyWeather)
        }
    }
}

@Composable
fun Forecast(dailyWeather: DailyWeather) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CurrentWeather(currentWeather = dailyWeather.currentWeather)
        TodayWeather(todayWeather = dailyWeather.todayWeathers)
        DailyWeather(dailyWeather = dailyWeather.nextWeathers)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorWeatherTheme {
        Forecast(dailyWeather = mockDailyWeather)
    }
}

private val mockDailyWeather = DailyWeather.getMockDailyWeather()