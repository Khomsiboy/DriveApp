package com.example.location

import android.content.pm.PackageManager
import android.location.LocationProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.util.jar.Manifest
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val REQUEST_LOCATION = 1
    lateinit var locationProvider: FusedLocationProviderClient
    var locationRequest :LocationRequest? = null
    lateinit var locationCallback : LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        locationProvider = LocationServices.getFusedLocationProviderClient(this)


        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return

                for (location in locationResult.locations){
                    Log.d("!!!","Lat: ${location.latitude}   Long: ${location.longitude}")
                }
            }
        }


        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Log.d("!!!","No permission")
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_LOCATION)
        }else{
            locationProvider.lastLocation.addOnSuccessListener {location ->
                if (location != null){
                    val lat = location.latitude
                    val long = location.longitude

                    Log.d("!!!","Lat: $lat, Long: $long")
                }
            }
            startLocationUpdates()
        }

        locationRequest = createLocationRequest()



    }


    fun startLocationUpdates(){

         if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
             locationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
         }

    }

    fun stopLocationUpdates(){

    }

    fun createLocationRequest() =
        LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_LOCATION){
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("!!!","Permission granted")
                startLocationUpdates()
            }
            else{
                Log.d("!!!","Permission denied")
                stopLocationUpdates()
            }
        }
    }



}


//LocationManager
//FusedLoactionProvider - google play service