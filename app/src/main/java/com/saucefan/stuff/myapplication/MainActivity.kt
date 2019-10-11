package com.saucefan.stuff.myapplication

import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CircleOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
   lateinit var mediaPlayer: MediaPlayer
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        const val FINE_LOCATION_REQUEST_CODE =5
    }
    private lateinit var myLocation:Location
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        chkperms()
        setSupportActionBar(findViewById(R.id.toolbar))

         mediaPlayer = MediaPlayer.create(this, R.raw.mud)

    }

   private fun chkperms() {
        //check permission
        if (ContextCompat.checkSelfPermission
                (
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )  //or whatever permission is in question
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_LOCATION_REQUEST_CODE
            )
        } else {
            getLocation()
        }

}



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

//            myLocation = mMap.myLocation


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.setOnMapLongClickListener { latLng ->
            mMap.addMarker(MarkerOptions().position(latLng).title("whatever"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.mainmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.center -> {


           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(myLocation.latitude,myLocation.longitude),14f))
            getLocation()
            mediaPlayer.start()
         /*   fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude,it.longitude),14f))
                }*/
         /*   if (myLocation != null) {

            }*/
            true
        }

        R.id.pin -> {
            val latlng = mMap.cameraPosition.target
            mMap.addMarker(MarkerOptions().position(latlng))
        /*    fusedLocationClient.lastLocation
                .addOnSuccessListener {
                    mMap.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)))
                }*/
            mediaPlayer.start()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==FINE_LOCATION_REQUEST_CODE) {
            if (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if we get here we are absolutely sure we have permissions
                getLocation()
                Toast.makeText(this,"${mMap.isMyLocationEnabled} + ${mMap.myLocation}",Toast.LENGTH_SHORT).show()
            }else {
                permissiionDeniend()} // and we land here if not permissions

        }
    }
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            permissiionDeniend()
            Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show()
                }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.flushLocations()
         fusedLocationClient.lastLocation
            .addOnSuccessListener {
                myLocation =it
           // Toast.makeText(this,"it works!",Toast.LENGTH_SHORT).show()

        }

    }
    fun permissiionDeniend() {
        //todo 1
    }
}
