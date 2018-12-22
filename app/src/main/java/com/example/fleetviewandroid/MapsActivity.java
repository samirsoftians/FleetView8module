package com.example.fleetviewandroid;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String vdate,vtime,vlat,vlng,vlocation,vno;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // Get the server reponse through the intent and assign to the lat and long

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent1=getIntent();

        vdate=intent1.getStringExtra("VDATE");
        vtime=intent1.getStringExtra("VTIME");
        vlat=intent1.getStringExtra("VLAT");
        vlng=intent1.getStringExtra("VLNG");
        vlocation=intent1.getStringExtra("VLOCATION");
        vno=intent1.getStringExtra("VNUM");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng sydney = new LatLng(Double.parseDouble(vlat),Double.parseDouble(vlng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
        mMap.addMarker(new MarkerOptions().position(sydney).title(" "+vlocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }
}
