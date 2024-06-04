package com.udistrital.myintensehorario.Service
import com.udistrital.myintensehorario.Repository.WeatherRetrofit
import retrofit2.Retrofit
import kotlinx.coroutines.runBlocking

interface WeatherRepository {
    fun getWeather();

}
class WeatherService2: WeatherRepository{


    override fun getWeather() {
        return runBlocking {
            val response = WeatherRetrofit.weatherApi.getWeather()
            //response
        }
    }

}