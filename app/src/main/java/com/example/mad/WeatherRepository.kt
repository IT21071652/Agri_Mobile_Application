package com.example.mad

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class WeatherRepository {

    fun fetchCurrent(location: FarmLocation): CurrentWeather {
        val latitude = location.latitude.toString()
        val longitude = location.longitude.toString()
        val timezone = URLEncoder.encode("Asia/Colombo", Charsets.UTF_8.name())
        val address = URL(
            "https://api.open-meteo.com/v1/forecast" +
                "?latitude=$latitude&longitude=$longitude" +
                "&current=temperature_2m,relative_humidity_2m,apparent_temperature," +
                "precipitation,wind_speed_10m,weather_code&timezone=$timezone"
        )
        val connection = address.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 8_000
        connection.readTimeout = 8_000
        connection.setRequestProperty("Accept", "application/json")

        try {
            val status = connection.responseCode
            if (status !in 200..299) {
                throw IOException("Weather service returned HTTP $status")
            }
            val response = connection.inputStream.bufferedReader(Charsets.UTF_8).use {
                it.readText()
            }
            val current = JSONObject(response).getJSONObject("current")
            return CurrentWeather(
                temperature = current.getDouble("temperature_2m"),
                feelsLike = current.getDouble("apparent_temperature"),
                humidity = current.getInt("relative_humidity_2m"),
                precipitation = current.getDouble("precipitation"),
                windSpeed = current.getDouble("wind_speed_10m"),
                code = current.getInt("weather_code")
            )
        } finally {
            connection.disconnect()
        }
    }
}
