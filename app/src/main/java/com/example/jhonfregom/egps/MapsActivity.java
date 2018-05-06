package com.example.jhonfregom.egps;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

private GoogleMap mMap;
        double latitud=0, longitud=0;
        String direccion;
        String ciudad;
        String ciudadn;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();

        try{

        latitud = Double.parseDouble(extras.getString("latitud"));
        longitud = Double.parseDouble(extras.getString("longitud"));
        ciudad = extras.getString("ciudad");

        }catch (Exception e){
        Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();

        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        }


@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng miubiaacion = new LatLng(latitud,longitud);

        mMap.addMarker(new MarkerOptions().position(miubiaacion).title(ciudad).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miubiaacion,17));





        }

@Override
public void onMapClick(LatLng latLng) {
        Toast.makeText(this, "Nueva ubicacion",Toast.LENGTH_LONG).show();
        try {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direcciones = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

        if (!direcciones.isEmpty()) {
        Address dir = direcciones.get(0);
        direccion = dir.getAddressLine(0) + " Ciudad " + dir.getLocality();
        ciudadn = dir.getLocality();


        TextView txtubicacion = (TextView)  findViewById(R.id.txtubicacionmap);

        txtubicacion.setText(direccion);
        }

        } catch (IOException e) {
        e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title(ciudadn).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        }



        }
