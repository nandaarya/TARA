package com.example.tara.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tara.R
import com.example.tara.ViewModelFactory
import com.example.tara.data.DataDummy
import com.example.tara.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private var city: String = ""
    private val interval: Long = 300000 //  5 minutes
    private val fastestInterval: Long = 30000 // 30 seconds
    private lateinit var mLastLocation: Location
    private lateinit var mLocationRequest: LocationRequest
    private val requestPermissionCode = 999

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestBackgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestBackgroundLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {                Toast.makeText(this, "Background permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Background Location Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                // All permissions are granted, proceed with location updates.
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

                startLocationUpdates()
            } else {
                Toast.makeText(this, "Izinkan Aplikasi Mengakses Lokasi", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

//        mainViewModel.getSession().observe(this) { user ->
//            if (!user.isLogin) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//        }

        setOptionMenu()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationRequest = LocationRequest.create()

        checkGPSIsEnabled()

        checkForPermission(this)
        startLocationUpdates()

        val defaultLocation = Location("userLocation")
        defaultLocation.latitude = latitude
        defaultLocation.longitude = longitude
        mainViewModel.saveUserLocation(defaultLocation)

        mainViewModel.userLocation.observe(this, Observer {
            setTouristAttractionList(it)
        })

//        binding.btnLogout.setOnClickListener {
//            mainViewModel.logout()
//        }
    }

    private fun setTouristAttractionList(userLocation: Location) {
        val rvTouristAttractionAdapter = TouristAttractionAdapter(userLocation)

        binding.rvTouristAttractions.layoutManager = LinearLayoutManager(this)
        binding.rvTouristAttractions.adapter = rvTouristAttractionAdapter

        rvTouristAttractionAdapter.addTouristAttraction(DataDummy.touristAttractionList)
    }

    private fun setOptionMenu() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_option -> {
//                    val intent = Intent(this, SettingActivity::class.java)
//                    startActivity(intent)
                    true
                }
                R.id.menu_logout -> {
                    mainViewModel.logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun startLocationUpdates() {
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = interval
        mLocationRequest.fastestInterval = fastestInterval

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback, Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            Log.d("MainActivity", "callback: $latitude $longitude")
            locationResult.lastLocation?.let {
                locationChanged(it)
                mainViewModel.saveUserLocation(it)
            }
            latitude = locationResult.lastLocation?.latitude ?: 0.0
            longitude = locationResult.lastLocation?.longitude ?: 0.0
            city = getCityName(latitude, longitude)
//            binding.longitudeText.text = "Longitude: $longitude"
//            binding.latitudeText.text = "Latitude: $latitude"
            binding.tvCity.text = "City: $city"
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkForPermission(context: Context) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permissions are already granted, proceed with location updates.
            startLocationUpdates()
        } else {
            // Request permissions.
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun checkGPSIsEnabled() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("The location permission is disabled. Do you want to enable it?")
                .setCancelable(true)
                .setPositiveButton("Yes") { _, _ ->
                    startActivityForResult(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 10
                    )
                }.setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }

    fun locationChanged(location: Location) {
        mLastLocation = location
        longitude = mLastLocation.longitude
        latitude = mLastLocation.latitude
        city = getCityName(latitude, longitude)
//        binding.longitudeText.text = "Longitude: $longitude"
//        binding.latitudeText.text = "Latitude: $latitude"
        binding.tvCity.text = city
        Log.d("MainActivity", "function: $latitude $longitude, City: ")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestPermissionCode) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // All permissions are granted, proceed with location updates.
                startLocationUpdates()
            } else {
                Toast.makeText(this, "Permission Denied 2", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCityName(lat: Double,long: Double): String {
        val cityName: String?
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat,long,3)

        cityName = address?.get(0)?.adminArea ?: "Kota Tidak Terdeteksi"
        return cityName
    }
}