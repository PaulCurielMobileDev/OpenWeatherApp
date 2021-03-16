package mexicandeveloper.com.weatherbycity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_detailweather.*
import mexicandeveloper.com.weatherbycity.Models.WeatherData
import mexicandeveloper.com.weatherbycity.ViewModels.WeatherViewModel

class WeatherDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(container?.context)
            .inflate(R.layout.fragment_detailweather, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: WeatherViewModel by activityViewModels()
        val weatherObserver = Observer<WeatherData> { newWeather ->
            initViews(newWeather)
        }
        viewModel.currentWeather.observe(viewLifecycleOwner, weatherObserver)
        initViews(viewModel.currentWeather.value!!)
    }

    private fun initViews(value: WeatherData) {

        tvDetailTemp.text = value.main.temp.toString()
        tvDetailFeel.text = "Feels like:" + value.main.feels_like.toString()
        val weather = value.weather.get(0)
        tvDetailWeather.text = weather.main
        tvDetailDescription.text = weather.description


    }

}