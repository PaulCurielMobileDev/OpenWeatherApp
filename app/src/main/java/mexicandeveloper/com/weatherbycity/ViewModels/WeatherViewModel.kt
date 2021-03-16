package mexicandeveloper.com.weatherbycity.ViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import mexicandeveloper.com.weatherbycity.Models.ErrorWeather
import mexicandeveloper.com.weatherbycity.Models.WeatherData
import mexicandeveloper.com.weatherbycity.Network.VolleySingleton
import mexicandeveloper.com.weatherbycity.R

class WeatherViewModel() : ViewModel() {
    var city = ""
    var url =
        "http://api.openweathermap.org/data/2.5/weather?q={cityName}&appid="
    val currentWeather: MutableLiveData<WeatherData> by lazy {
        MutableLiveData<WeatherData>()
    }

    val theError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun loadWeather(cityName: String, ctx: Context) {
        if (!cityName.equals(city)) {
            city = cityName
            var urlSearch = url.replace("{cityName}", cityName)
            urlSearch += (ctx.getString(R.string.weather_id))
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, urlSearch, null,
                { response ->
                    var gson = Gson()
                    var weatherInfo =
                        gson.fromJson<WeatherData>(response.toString(), WeatherData::class.java)
                    currentWeather.value = weatherInfo
                },
                { error ->
                    city = ""
                    currentWeather.value = null
                    if (error.networkResponse.data != null) {
                        try {
                            var body = error.networkResponse.data.toString()
                            val errorValue = Gson().fromJson(
                                body,
                                ErrorWeather::class.java
                            )
                            theError.value = errorValue.message
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                            theError.value = error.toString()
                        }
                    }

                }
            )

            VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest)
        } else currentWeather.postValue(currentWeather.value)

    }
}