package com.github.muhwyndhamhp.ktorweather.datasource

import android.content.Context
import androidx.room.Room
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataSourceModule = module {
    factory { provideOpenMeteoService(get()) }
    single { provideOkHTTPClient() }
    single { provideRetrofit(get(), "https://api.open-meteo.com/".toHttpUrlOrNull()!!) }
    single {
        synchronized(this) {
            provideKtorWeatherDB(get())
        }
    }
    single { provideOpenMeteoServicePipe(get()) }
}

fun provideOpenMeteoServicePipe(service: OpenMeteoService): OpenMeteoServicePipe {
    return OpenMeteoServicePipeImpl(service)
}

fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: HttpUrl): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun provideOkHTTPClient(): OkHttpClient {
    val client = OkHttpClient().newBuilder()
    return client.build()
}

fun provideOpenMeteoService(retrofit: Retrofit): OpenMeteoService =
    retrofit.create(OpenMeteoService::class.java)

fun provideKtorWeatherDB(applicationContext: Context): KtorWeatherDB =
    Room.databaseBuilder(
        applicationContext,
        KtorWeatherDB::class.java,
        "ktor_weather_db"
    ).build()