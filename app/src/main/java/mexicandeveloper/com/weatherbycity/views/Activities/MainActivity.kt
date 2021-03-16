package mexicandeveloper.com.weatherbycity.views.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import mexicandeveloper.com.weatherbycity.Models.WeatherData
import mexicandeveloper.com.weatherbycity.R
import mexicandeveloper.com.weatherbycity.ViewModels.WeatherViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btMain.setOnClickListener(this)

        val weatherObserver = Observer<WeatherData> { newWeather ->
//            Toast.makeText(this, newWeather.toString(), Toast.LENGTH_LONG).show()
            if (newWeather == null) return@Observer
            val weatherIntent = Intent(this, WeatherActivity::class.java).apply {
                putExtra(WeatherActivity.CITY, newWeather.name)
            }
            startActivity(weatherIntent)
        }
        val errorObserver = Observer<String> { theError ->
            Toast.makeText(this, theError, Toast.LENGTH_LONG).show()
        }
        viewModel.currentWeather.observe(this, weatherObserver)
        viewModel.theError.observe(this, errorObserver)
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.btMain -> viewModel.loadWeather(etMain.text.trim().toString(), this)
        }
    }

}