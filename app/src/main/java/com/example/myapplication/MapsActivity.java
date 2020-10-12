package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity{

    SupportMapFragment supportMapFragment;
    private Button trackBtn;
    private FusedLocationProviderClient fusedLocationProviderclient;
    String getlatitude="", getlongtitude="";
    private String Post_key,current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       supportMapFragment = (SupportMapFragment)getSupportFragmentManager()
       .findFragmentById(R.id.map);
        trackBtn = (Button) findViewById(R.id.trackBtn);
        fusedLocationProviderclient = LocationServices.getFusedLocationProviderClient(this);

        Post_key = getIntent().getExtras().get("PostKey").toString();
        getlatitude = getIntent().getExtras().get("PostLatitude").toString();
        getlongtitude = getIntent().getExtras().get("PostLongtitude").toString();

        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderclient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {

                            final Location location = task.getResult();
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(MapsActivity.this,
                                        Locale.getDefault());
                                if(location!=null){
                                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(GoogleMap googleMap) {
                                            LatLng latLng = new LatLng(Double.parseDouble(getlatitude),Double.parseDouble(getlongtitude));
                                            getlatitude= String.valueOf(getlatitude);
                                            getlongtitude= String.valueOf(getlongtitude);
                                            Log.d("latlong", "onComplete: " + getlongtitude + "  " + getlatitude);
                                            MarkerOptions options = new MarkerOptions().position(latLng).title("You Are Here");
                                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                                            googleMap.addMarker(options);

                                        }
                                    });
                                }

                            }
                        }
                    });

                } else {
                    ActivityCompat.requestPermissions(MapsActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });

    }


    /**
     *  Geocoder geocoder = new Geocoder(MapsActivity.this,
     *                                         Locale.getDefault());
     *
     *                                 try {
     *                                     List<Address> addresses = geocoder.getFromLocation(
     *                                             location.getLatitude(), location.getLongitude(), 1
     *                                     );
     *                                     //
     *
     *                                     getlongtitude = String.valueOf(addresses.get(0).getLongitude());
     *                                     getlatitude = String.valueOf(addresses.get(0).getLatitude());
     *
     *                                     Log.d("latlong", "onComplete: " + getlongtitude + "  " + getlatitude);
     *
     *                                     LatLng sydney = new LatLng(Double.parseDouble(getlongtitude),Double.parseDouble(getlatitude) );
     *                                     mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
     *                                     mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
     *                                 } catch (IOException e) {
     *                                     e.printStackTrace();
     *                                 }
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     * trackBtn.setOnClickListener(new View.OnClickListener() {
     *             @Override
     *             public void onClick(View view) {
     *                 if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
     *                     fusedLocationProviderclient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
     *                         @Override
     *                         public void onComplete(@NonNull Task<Location> task) {
     *
     *                             final Location location = task.getResult();
     *                             if (location != null) {
     *                                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
     *                                    @Override
     *                                    public void onMapReady(GoogleMap googleMap) {
     *                                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
     *                                        getlatitude= String.valueOf(location.getLatitude());
     *                                        getlongtitude= String.valueOf(location.getLongitude());
     *                                        Log.d("latlong", "onComplete: " + getlongtitude + "  " + getlatitude);
     *                                        MarkerOptions options = new MarkerOptions().position(latLng).title("You Are Here");
     *                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
     *                                        googleMap.addMarker(options);
     *                                    }
     *                                });
     *                             }
     *                         }
     *                     });
     *
     *                 } else {
     *                     ActivityCompat.requestPermissions(MapsActivity.this
     *                             , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
     *                 }
     *
     *             }
     *         });
     *
     *     }
     */

}