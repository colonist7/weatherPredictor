package com.example.weather

import android.accounts.AuthenticatorDescription
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.ITemToday.ItemToday
import com.example.weather.ITemToday.ItemTodayAdapter
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var json : WeatherX
    lateinit var  json2 : List<FiveDays>
    var list = mutableListOf<ItemToday>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getWeather = GetWeather()


        getWeather.execute("http://api.openweathermap.org/data/2.5/weather?q=Tbilisi&appid=e36d7e5af4e061f888ca4260e39a9e7d")

        GetFD().execute("http://api.openweathermap.org/data/2.5/forecast?q=Tbilisi&appid=e36d7e5af4e061f888ca4260e39a9e7d")

    }

    override fun onResume() {
        super.onResume()
    }

    inner class GetWeather() : AsyncTask<String, Any, String>() {

        override fun doInBackground(vararg params: String?): String {
            Thread.sleep(1000L)
            val url = URL(params[0])
            val urlConnection = url.openConnection()

            val stream = BufferedInputStream(urlConnection.inputStream)
            val bufferedReader = BufferedReader(InputStreamReader(stream))
            val builder = StringBuilder()

            var chunk = bufferedReader.readLine()
            while (chunk != null) {
                builder.append(chunk)
                chunk = bufferedReader.readLine()
            }

            return builder.toString()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            json =  toWeather(JSONObject(result))
            setToday(json)
        }
    }

    fun toWeather (obj : JSONObject) : WeatherX {
        val weather = toWeatherList(obj.getJSONArray("weather"))
        val main = parseMain(obj.getJSONObject("main"))

        val base = obj.getString("base")
        val visibility = obj.getInt("visibility")
        val coord = obj.getJSONObject("coord")
        val wind = obj.getJSONObject("wind")
        val clouds = obj.getJSONObject("clouds")
        val dt = obj.getInt("dt")
        val sys = obj.getJSONObject("sys")
        val timezone = obj.getInt("timezone")
        val id = obj.getInt("id")
        val name = obj.getString("name")
        val cod = obj.getInt("cod")

        return WeatherX(coord, weather,base,main,visibility,wind,clouds,dt,sys,timezone,id,name,cod)
    }

    fun toWeatherList (arr : JSONArray) : List<WeatherXX> {
        val list = mutableListOf<WeatherXX>()
        for (i in 0 until arr.length()) {
            val item = parseWeather(arr.getJSONObject(i))
            list.add(item)
        }
        return list
    }

    fun parseWeather (obj : JSONObject) : WeatherXX {
        val id = obj.getInt("id")
        val main = obj.getString("main")
        val description = obj.getString("description")
        val icon = obj.getString("icon")

        return WeatherXX(id, main, description, icon)
    }

    fun parseMain (obj : JSONObject) : Main {
        val temp = obj.getDouble("temp")
        val feelslike = obj.getDouble("feels_like")
        val tempMin = obj.getDouble("temp_min")
        val tempMax = obj.getDouble("temp_max")
        val pressure = obj.getInt("pressure")
        val humidity: Int = obj.getInt("humidity")

        return Main(temp,feelslike,tempMin,tempMax,pressure,humidity)
    }

    fun setToday (obj : WeatherX) {
        val todayBig = findViewById(R.id.todayBig) as TextView
        val status = findViewById(R.id.now_status) as TextView
        val bigImg = findViewById(R.id.imageView2) as ImageView

        val degrees = (obj.main.temp - 273.15).toInt()

        todayBig.setText(degrees.toString() + "°C")
        status.setText(obj.weather[0].description.capitalize())

        val icon = obj.weather[0].main.toString().toLowerCase()


        when (icon) {
            "clouds" -> bigImg.setImageResource(R.drawable.clouds)
        }



    }



    inner class GetFD() : AsyncTask<String, Any, String>() {

        override fun doInBackground(vararg params: String?): String {
            Thread.sleep(3000L)
            val url = URL(params[0])
            val urlConnection = url.openConnection()

            val stream = BufferedInputStream(urlConnection.inputStream)
            val bufferedReader = BufferedReader(InputStreamReader(stream))
            val builder = StringBuilder()

            var chunk = bufferedReader.readLine()
            while (chunk != null) {
                builder.append(chunk)
                chunk = bufferedReader.readLine()
            }

            return builder.toString()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            json2 =  parseFiveDaysList(JSONObject(result))
            println(json2)
            setFD(json2)
        }
    }

    data class FiveDays(
        val weather: List<FDWeather>,
        val main: FDMain,
        val wind: JSONObject,
        val clouds: JSONObject,
        val dt: Int,
        val dt_txt: String,
        val sys: JSONObject
    )

    data class FDMain (
        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Int,
        val sea_level: Int,
        val grnd_level: Int,
        val humidity: Int,
        val temp_kf: Double
    )

    data class FDWeather (
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    fun toFiveDay (obj : JSONObject) : FiveDays {
        val weather = parseToFDweatherList(obj.getJSONArray("weather"))
        val main = parseFDMain(obj.getJSONObject("main"))
        val wind = obj.getJSONObject("wind")
        val clouds = obj.getJSONObject("clouds")
        val dt = obj.getInt("dt")
        val dt_txt = obj.getString("dt_txt")
        val sys = obj.getJSONObject("sys");

        return FiveDays(weather, main, wind, clouds, dt, dt_txt, sys);
    }

    fun parseFiveDaysList(obj : JSONObject) : List<FiveDays> {
        val list = mutableListOf<FiveDays>()
        val arr = obj.getJSONArray("list")
        for (i in 0 until arr.length()) {
            val item = toFiveDay(arr.getJSONObject(i))
            list.add(item)
        }
        return list
    }

    fun toFDWeather (obj : JSONObject) : FDWeather {
        val id: Int = obj.getInt("id")
        val main: String = obj.getString("main")
        val description: String = obj.getString("description")
        val icon: String = obj.getString("icon")

        return FDWeather(id, main, description, icon)
    }

    fun parseFDMain (obj : JSONObject) : FDMain {
        val temp: Double = obj.getDouble("temp")
        val feels_like: Double = obj.getDouble("feels_like")
        val temp_min: Double = obj.getDouble("temp_min")
        val temp_max: Double = obj.getDouble("temp_max")
        val pressure: Int = obj.getInt("pressure")
        val sea_level: Int = obj.getInt("sea_level")
        val grnd_level: Int = obj.getInt("grnd_level")
        val humidity: Int = obj.getInt("humidity")
        val temp_kf: Double = obj.getDouble("temp_kf")

        return FDMain(temp, feels_like, temp_min, temp_max, pressure, sea_level, grnd_level, humidity, temp_kf)
    }

    fun parseToFDweatherList(arr : JSONArray) : List<FDWeather> {
        val list = mutableListOf<FDWeather>()
        for (i in 0 until arr.length()) {
            val item = toFDWeather(arr.getJSONObject(i))
            list.add(item)
        }
        return list
    }

    fun setFD(obj : List<FiveDays>) {
        val curDay = getTimeObject(obj[0].dt_txt).day
            var recyclerView = findViewById(R.id.weather_today_block) as RecyclerView
            var j = 0
            var current = 0
            for (item in obj) {
                println(item)
                val date = getTimeObject(item.dt_txt)
                var h = ""
                if(date.hours.toString().length == 1) {
                    h = "0" + date.hours.toString()
                } else {
                    h = date.hours.toString()
                }

                if(current == 9) {
                    h = "12"
                }

                if(curDay != date.day || j == 5) {
                    break
                }
                list.add(
                    ItemToday(
                        round(item.main.temp - 273.15).toString() + "°C",
                        item.weather[0].icon,
                        h + " : 00"
                    )
                )
                current = getTimeObject(item.dt_txt).hours
                j++
            }
            println(list)


            val adapter = ItemTodayAdapter(list)

            println(adapter)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.adapter = adapter

        }

    data class dateObject (
        val year : Int,
        val month : Int,
        val day : Int,
        val hours : Int,
        val minutes : Int
    )

    fun getTimeObject (str : String = "2020-01-19 03:00:00") : dateObject {
        val date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(str)
        val year = date.year + 1900
        val month = date.month + 1
        val day = date.day
        val hours = date.hours
        val minutes = date.minutes
        return dateObject(year,month,day,hours,minutes)
    }
}
