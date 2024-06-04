package com.udistrital.myintensehorario.Service
import com.udistrital.myintensehorario.Repository.WeatherApiSua
import com.udistrital.myintensehorario.Repository.WeatherRetrofit
import com.udistrital.myintensehorario.Repository.WeatherRetrofitSua
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




class WeatherServiceSua : WeatherApiSua{
    override suspend fun getWeather(): WeatherResponse {
        return try {
            WeatherRetrofitSua.weatherApi.getWeather()
        } catch (e: Exception) {
            println("Error al obtener el clima: ${e.message}")
            WeatherResponse(emptyList())
        }
    }
}


data class WeatherResponse(
    val weather: List<Weather>
) {
    data class Weather(
        val main: String
    )
}
