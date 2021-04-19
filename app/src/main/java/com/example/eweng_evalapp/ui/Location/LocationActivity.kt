package com.example.eweng_evalapp.ui.Location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.example.ewen1.ui.Settings.Item.HistoriqueItem
import com.example.eweng_evalapp.BuildConfig
import com.example.eweng_evalapp.R
import com.example.eweng_evalapp.databinding.ActivityLocationBinding
import com.example.eweng_evalapp.ui.LocalPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.gson.Gson
import java.lang.Math.round
import java.util.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    // maniere en kotlin pour declarer du cde statique (sert à simplifier les interations pour lancer une activité)
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LocationActivity::class.java)
        }
    }

    val PERMISSION_REQUEST_LOCATION = 9999
    private lateinit var binding: ActivityLocationBinding // <-- Référence à notre ViewBinding
    private lateinit var maMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val eseo = LatLng(47.493071780139, -0.5513407472059)
    private val TAG = LocationActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            requestPermission()
        }

        supportActionBar?.apply {
            setTitle("Localisation")
            setDisplayHomeAsUpEnabled(true)
            //setDisplayShowHomeEnabled(true)
        }

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    private fun setMapStyle(map: GoogleMap) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                try {
                    // Customize the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = map.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this,
                            R.raw.map_style_light
                        )
                    )

                    if (!success) {
                        Log.e(TAG, "Style parsing failed.")
                    }
                } catch (e: Resources.NotFoundException) {
                    Log.e(TAG, "Can't find style. Error: ", e)
                }
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                try {
                    // Customize the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    val success = map.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            this,
                            R.raw.map_style_dark
                        )
                    )

                    if (!success) {
                        Log.e(TAG, "Style parsing failed.")
                    }
                } catch (e: Resources.NotFoundException) {
                    Log.e(TAG, "Can't find style. Error: ", e)
                }
            } // Night mode is active, we're using dark theme
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    MaterialDialog(this).show {
                        title(R.string.Titredemande)
                        message(R.string.messagedemande)
                        positiveButton {
                            startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                                )
                            )
                        }
                    }
                    // Permission non accepté, expliqué ici via une activité ou une dialog pourquoi nous avons besoin de la permission
                }
                return
            }
        }
    }

    @SuppressLint("ShowToast", "MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            )
                .addOnSuccessListener { geoCode(it) }
                .addOnFailureListener {
                    // Remplacer par un vrai bon message
                    Toast.makeText(this, "Localisation impossible", Toast.LENGTH_SHORT).show()
                }
        }
    }

//    private fun getLocation() {
//        if (hasPermission()) {
//            val locationManager =
//                applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager?
//            locationManager?.run {
//                locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)?.run {
//                    geoCode(this)
//                }
//            }
//        }
//    }

    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
        } else {
            getLocation()
        }
    }

    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun geoCode(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        if (results.isNotEmpty()) {

            val monAddress = LatLng(location.latitude, location.longitude)
            val distance = FloatArray(1)

            maMap.addMarker(MarkerOptions().title("MyPosition").position(monAddress))
            maMap.moveCamera(CameraUpdateFactory.newLatLngZoom(monAddress, 15f))
            maMap.animateCamera(CameraUpdateFactory.zoomTo(12f), 2000, null)
            maMap.mapType

            Location.distanceBetween(
                eseo.latitude,
                eseo.longitude,
                monAddress.latitude,
                monAddress.longitude,
                distance
            )
            distance[0] = distance[0] / 1000
            var distanceArrondi = round(distance[0] * 100) / 100

            var item = HistoriqueItem(
                results[0].getAddressLine(0),
                monAddress.latitude.toString() + ";" + monAddress.longitude.toString()
            )
            //var item = HistoriqueItem(results[0].getAddressLine(0))
            val itemInJson: String = Gson().toJson(item)

            LocalPreferences.getInstance(this).addToHistory(itemInJson)

            binding.TxtLocalisation.text = results[0].getAddressLine(0)
            binding.txtCoordonnees.text =
                "Coordonnées : " + monAddress.latitude.toString() + ";" + monAddress.longitude.toString()
            binding.txtDistance.text = "À " + distanceArrondi.toString() + "km de l'ESEO"

        }
    }


    override fun onMapReady(p0: GoogleMap) {
        maMap = p0
        setMapStyle(maMap)
        maMap.addMarker(MarkerOptions().title("ESEO").position(eseo))
//        maMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eseo,15f))
//        maMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eseo,15f))
//        maMap.animateCamera(CameraUpdateFactory.zoomTo(12f),2000,null)
        requestPermission() //a ne pas mettre si je veux cliquer pour lancer recherche position
    }

}