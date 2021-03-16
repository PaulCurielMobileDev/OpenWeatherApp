package mexicandeveloper.com.weatherbycity

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : FragmentActivity() {

    companion object {
        const val CITY = "CityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        toolBar.title = intent.extras?.getString(CITY)

        toolBar.setNavigationOnClickListener { view ->
            onBackPressed()
        }

        Toast.makeText(this, intent.extras?.getString(CITY), Toast.LENGTH_LONG).show()
        val bundle = Bundle()
        bundle.putString(CITY, intent.extras?.getString(CITY))
        val fragment = WeatherListFragment()
        fragment.arguments = bundle
        supportFragmentManager.commit {
            add(R.id.frLayout, fragment)
            addToBackStack(CITY)
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}