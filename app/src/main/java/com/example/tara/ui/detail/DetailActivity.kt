package com.example.tara.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tara.R
import com.example.tara.data.TouristAttraction
import com.example.tara.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDetailBinding
    lateinit var data: TouristAttraction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_TOURIST_ATTRACTION_DATA, TouristAttraction::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_TOURIST_ATTRACTION_DATA)!!
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        val location = LatLng(data.lat, data.lon)
        mMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(data.locationName)
                .snippet("${data.rating}/5 (${data.userRatingsTotal} users)")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    companion object {
        const val EXTRA_TOURIST_ATTRACTION_DATA = "Tourist Attraction Detail"
    }
}