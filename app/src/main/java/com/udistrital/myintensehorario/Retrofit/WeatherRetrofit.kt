package com.udistrital.myintensehorario.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(): List<Any>

}
object WeatherRetrofit{
        private const val BASE_URL = "https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=44.34&lon=10.99&appid=45f6b9217c3713da9475fedd3d3ce958"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val weatherApi: WeatherApi by lazy {
            retrofit.create(WeatherApi::class.java)
        }
    }