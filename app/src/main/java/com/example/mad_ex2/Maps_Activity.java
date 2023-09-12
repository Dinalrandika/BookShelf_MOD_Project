// Maps_Activity.java
package com.example.mad_ex2;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); // Set your layout file here

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map); // Make sure your layout includes a fragment with the ID "map"
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Create an array of LatLng positions for your markers
        LatLng[] locations = {
                new LatLng(6.801165348, 79.920655489), // Colombo
                new LatLng(6.802345, 79.921234),       // Galle
                new LatLng(6.803456, 79.922345),       // Matara
                new LatLng(6.804567, 79.923456),       // Kandy
                new LatLng(6.805678, 79.924567)        // Kurunegala
        };

        // Add markers for each LatLng in the array
        for (LatLng location : locations) {
            mMap.addMarker(new MarkerOptions().position(location).title("Shop"));
        }

        // Move the camera to the first marker's position
        if (locations.length > 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 15)); // Zoom level adjusted
        }
    }
}
