package com.example.jhonfregom.egps;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import locat.devazt.networking.HttpClient;
import locat.devazt.networking.OnHttpRequestComplete;
import locat.devazt.networking.Response;

public class GPSActivity extends AppCompatActivity implements View.OnClickListener {
    String ubicacion;
    double latitud, longitud;
    String direccion;
    String ciudad;
    TextView txtubicacion;
    Button verMapa;
    Button iniciogps, detenergps;
    private Button mLogoutBtn;
    TextView txtubicacion2;

    LocationManager locationManager;
    Localizacion local;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        mLogoutBtn = (Button) findViewById(R.id.logout);
        iniciogps = (Button) findViewById(R.id.inicio);
        detenergps = (Button) findViewById(R.id.detener);
        Handler handler = new Handler();

        txtubicacion2 = (TextView) findViewById(R.id.txtubicacion2);

        iniciogps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      //          getCoordenadas();
            }
        });


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(GPSActivity.this, MainActivity.class));
            }
        });


        txtubicacion = (TextView) findViewById(R.id.txtubicacion);
        verMapa = (Button) findViewById(R.id.verMapa);
        verMapa.setOnClickListener(this);
        lanzarLocalizacion();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            //------------------------------------------------------------------------------
            ActivityCompat.requestPermissions(GPSActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) local);

        txtubicacion.setText("Ubicacion agregada");

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(GPSActivity.this,
                            "permission was granted, :)",
                            Toast.LENGTH_LONG).show();
                    lanzarLocalizacion();
                } else {
                    Toast.makeText(GPSActivity.this,
                            "permission denied, ...:(",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();

            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> direcciones = geocoder.getFromLocation(latitud, longitud, 1);

                if (!direcciones.isEmpty()) {
                    Address dir = direcciones.get(0);
                    direccion = dir.getAddressLine(0) + " Ciudad " + dir.getLocality();
                    ciudad = dir.getLocality();
                    txtubicacion.setText(direccion);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("longitud", String.valueOf(longitud));
        i.putExtra("latitud", String.valueOf(latitud));
        i.putExtra("ciudad", ciudad);
        startActivity(i);
    }

    public void lanzarLocalizacion() {
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        local = new Localizacion();
        local.setGps(this);
    }
}









