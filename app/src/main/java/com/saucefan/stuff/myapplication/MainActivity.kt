package com.saucefan.stuff.myapplication

import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.view.Menu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.saucefan.stuff.mapassign.Alphabetdraw
import com.saucefan.stuff.mapassign.MapDraw


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
   lateinit var mediaPlayer: MediaPlayer
    lateinit var mDraw:MapDraw
    lateinit var alphaDraw:Alphabetdraw
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
        //make map ready
        mapFragment.getMapAsync(this)
            //check permissions
        chkperms()
        //actionbar
        setSupportActionBar(findViewById(R.id.toolbar))
        //stupid mud sound effect
        mediaPlayer = MediaPlayer.create(this, R.raw.mud)



    }





    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

        mMap.setOnMapLongClickListener { latLng ->
            mMap.addMarker(MarkerOptions().position(latLng).title("whatever"))
        }
        //lets get stupid
        mDraw = MapDraw(mMap)
        alphaDraw=Alphabetdraw(mDraw)

        mMap.setOnMarkerClickListener {

            it.remove()
            true
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
            R.id.alpha->{


            true}

        R.id.circle->{
            val latLng =mMap.cameraPosition.target
            val maxzoom =mMap.maxZoomLevel
            val minzoom = mMap.minZoomLevel
            val zoom=mMap.cameraPosition.zoom
            Toast.makeText(this,"$maxzoom max + $minzoom min + $zoom current ", Toast.LENGTH_SHORT).show()

                mDraw.midpointCircle( latLng.latitude,latLng.longitude, 10.0/zoom)

            true}


        else -> {
            super.onOptionsItemSelected(item)
        }
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
                permissionsDenied()} // and we land here if not permissions

        }
    }
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            permissionsDenied()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.flushLocations()
         fusedLocationClient.lastLocation
            .addOnSuccessListener {
                myLocation =it
           // Toast.makeText(this,"it works!",Toast.LENGTH_SHORT).show()

        }

    }
   private fun permissionsDenied(){
        Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show()
    }
}
