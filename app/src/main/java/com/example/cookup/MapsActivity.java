package com.example.cookup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener, GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    protected LocationManager locationManager;
    public double lat, lon;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {

        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(getString(R.string.latitud), (int) location.getLatitude());
        editor.putInt(getString(R.string.latitud), (int) location.getLongitude());
        editor.commit();

        lat = location.getLatitude();
        lon = location.getLongitude();


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        Toast.makeText(getApplicationContext(),"" + lat + lon, Toast.LENGTH_LONG).show();
        LatLng actualPosition = new LatLng(lat, lon);
        map.addMarker(new MarkerOptions().position(actualPosition).title("Marker in your Actual Position"));
        map.moveCamera(CameraUpdateFactory.newLatLng(actualPosition));
    }

    public void backScreen(View view){
        //String lat = "Latitude: " + location.getLatitude();
        //String lon = "Longitude: " + location.getLongitude();
        Intent intent = new Intent();
        intent.putExtra("Lat", lat);
        intent.putExtra("Lon", lon);
        setResult(RESULT_OK, intent);
        finish();
    }



}