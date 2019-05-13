package tsisyk.roman.testapp.sunapplication

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.content_main.*

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import tsisyk.roman.testapp.sunapplication.BuildConfig.APPLICATION_ID
import tsisyk.roman.testapp.sunapplication.retrofit.SunResponse
import tsisyk.roman.testapp.sunapplication.retrofit.SunService
import java.util.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val PERMISSIONS_REQUEST_CODE = 34
    private var PLACE_PICKER_REQUEST = 1

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latitudeText: TextView
    private lateinit var longitudeText: TextView


    var baseUrl = "https://api.sunrise-sunset.org/"
    var const = "date=today"
    var lat = ""
    var lon = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bntGetData.setOnClickListener {
            if (checkBoxLocal.isChecked) {
                getLastLocation()
            } else (findNewLocation())
            getSunAPIData()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun findNewLocation() {
        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(this, data)
            lat = place.latLng.latitude.toString()
            lon = place.latLng.longitude.toString()
            textPlaceName.text = place.name.toString()
            textPlaseAdress.text = place.address.toString()
            getSunAPIData()

        }
    }


    private fun getSunAPIData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(SunService::class.java)
        val call = service.getSunData(lat, lon, const)
        call.enqueue(object : Callback<SunResponse> {
            override fun onResponse(call: Call<SunResponse>, response: Response<SunResponse>) {
                if (response.code() == 200) {
                    val sunResponse = response.body()!!

                    textSunrise.text = sunResponse.results.sunrise
                    textSunset.text = sunResponse.results.sunset
                }
            }

            override fun onFailure(call: retrofit2.Call<SunResponse>, t: Throwable) {

            }
        })
    }

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
/*
                    latitudeText.text = resources.getString(R.string.latitude_label, task.result!!.latitude)
                    longitudeText.text = resources.getString(R.string.longitude_label, task.result!!.longitude)*/

                    lat = task.result!!.latitude.toString()
                    lon = task.result!!.longitude.toString()

                    textPlaceName.text = "Current Location"
                    textPlaseAdress.text = "lat= " + task.result!!.latitude.toString() + ", lon=" + task.result!!.longitude.toString()

                } else {

                    Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_SHORT)
                }
            }
    }

    private fun showSnackbar(
        snackStrId: Int,
        actionStrId: Int = 0,
        listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content), getString(snackStrId),
            LENGTH_INDEFINITE
        )
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(ACCESS_COARSE_LOCATION),
            PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar(R.string.permission_rationale, android.R.string.ok, View.OnClickListener {
                // Request permission
                startLocationPermissionRequest()
            })

        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")
                (grantResults[0] == PERMISSION_GRANTED) -> getLastLocation()
                else -> {
                    showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        View.OnClickListener {
                            val intent = Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts("package", APPLICATION_ID, null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        })
                }
            }
        }
    }


}