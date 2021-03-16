package mexicandeveloper.com.weatherbycity

import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import mexicandeveloper.com.weatherbycity.Models.WeatherData
import mexicandeveloper.com.weatherbycity.ViewModels.WeatherViewModel

class WeatherListFragment : ListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: WeatherViewModel by activityViewModels()
        val weatherObserver = Observer<WeatherData> { newWeather ->
            listAdapter = TheAdapter(newWeather)
        }
        viewModel.currentWeather.observe(viewLifecycleOwner, weatherObserver)
        viewModel.loadWeather(
            requireArguments().getString(WeatherActivity.CITY)!!,
            requireContext()
        )
        listAdapter = TheAdapter(viewModel.currentWeather.value)
    }

    inner class TheAdapter(val data: WeatherData?) : BaseAdapter(), View.OnClickListener {
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, convertView: View?, container: ViewGroup?): View {
            var convertView = convertView
            convertView = LayoutInflater.from(container?.context)
                .inflate(R.layout.row_weather, container, false)
            when (position) {
                0 -> {
                    convertView.findViewById<TextView>(R.id.tvRowTitle).text = "Clear"
                    convertView.findViewById<TextView>(R.id.tvRowTempe).text =
                        data?.main?.temp.toString()
                }
                1 -> {
                    convertView.findViewById<TextView>(R.id.tvRowTitle).text = "Cloudy"
                    convertView.findViewById<TextView>(R.id.tvRowTempe).text =
                        data?.main?.temp.toString()
                }
                2 -> {
                    convertView.findViewById<TextView>(R.id.tvRowTitle).text = "Rain"
                    convertView.findViewById<TextView>(R.id.tvRowTempe).text =
                        data?.main?.humidity.toString()
                }
            }
            convertView.setOnClickListener(this)

            return convertView
        }

        override fun onClick(p0: View?) {
            parentFragmentManager.commit {
                allowEnterTransitionOverlap = true
                replace(R.id.frLayout, WeatherDetailFragment())
                addToBackStack(WeatherActivity.CITY)
            }
        }

    }
}