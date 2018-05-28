package com.example.jhonfregom.egps;

import android.Manifest;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSActivity extends AppCompatActivity implements View.OnClickListener {
    String ubicacion;
    String direccion;
    String ciudad;
    double longitud, latitud;
    TextView txtubicacion;
    Button verMapa;
    Button iniciogps, detenergps, guardargps, mostrarrutas;
    private Button mLogoutBtn;
    TextView txtubicacion2;
    TextView txtubicacion3;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth firebaseAuth;
    private RequestQueue mPosicion;
    private RequestQueue mPosicionF;

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
        guardargps = (Button) findViewById(R.id.guardar);
        mostrarrutas = (Button)findViewById(R.id.btnRutas);
        Handler handler = new Handler();

        txtubicacion2 = (TextView) findViewById(R.id.txtubicacion2);
        txtubicacion3 = (TextView) findViewById(R.id.txtubicacion3);

        mPosicion = Volley.newRequestQueue(this);
        mPosicionF = Volley.newRequestQueue(this);

        mostrarrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GPSActivity.this, MostrarRutas.class));
            }
        });

        guardargps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarcoord();
            }
        });

        iniciogps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });


        detenergps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParseF();
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

    private void guardarcoord() {
        //final String num = "0.25555";
       // final String ubicacion =
        final String mPosicionIni = txtubicacion2.getText().toString().trim();
        final String mPosicionfin = txtubicacion3.getText().toString().trim();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
      //FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        //    try{
        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference databaseReference = mDatabase.child(mAuth.getCurrentUser().getUid()).child("ubicacion").push();

        //final DatabaseReference databaseReference = mDatabase.getReference().child(mAuth.getCurrentUser().getUid()).child("ubicacion");

        // Re
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(GPSActivity.this, "Ruta grabada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // databaseReference.child("FUT").setValue(num);
      //  databaseReference.child("ubicacion").setValue(ubicacion);
//        Ubicacion ubicacion = new Ubicacion("Posicion Inicial","Posicion Final");
        databaseReference.child("Posicion Inicial").setValue(mPosicionIni);
        databaseReference.child("Posicion Final").setValue(mPosicionfin);
        databaseReference.push();

        /*mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    databaseReference.child("FUT").setValue(num);
                    databaseReference.child("Posicion Inicial").setValue(mPosicionIni);
                    databaseReference.child("Posicion Final").setValue(mPosicionfin);
                    Toast.makeText(GPSActivity.this, "Ubicación grabada", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(GPSActivity.this, "Error al grabar", Toast.LENGTH_SHORT).show();
            }
        };//}catch(Exception e){
          //  e.printStackTrace();
        //}*/
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
            this.latitud = loc.getLatitude();
            longitud = loc.getLongitude();

            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> direcciones = geocoder.getFromLocation(this.latitud, longitud, 1);

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

    public RequestQueue getmPosicionF() {
        return mPosicionF;
    }

    public void setmPosicionF(RequestQueue mPosicionF) {
        this.mPosicionF = mPosicionF;
    }

    public RequestQueue getmPosicion() {
        return mPosicion;
    }

    public void setmPosicion(RequestQueue mPosicion) {
        this.mPosicion = mPosicion;
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
        i.putExtra("latitud", String.valueOf(txtubicacion2.getText().toString().split(",")[0]));
        i.putExtra("longitud", String.valueOf(txtubicacion2.getText().toString().split(",")[1]));
        i.putExtra("ciudad", ciudad);


        double latInicial, latFinal, lonInicial, lonFinal;

        latInicial = Double.parseDouble(txtubicacion2.getText().toString().split(",")[0]);
        lonInicial = Double.parseDouble(txtubicacion2.getText().toString().split(",")[1]);

        latFinal = Double.parseDouble(txtubicacion3.getText().toString().split(",")[0]);
        lonFinal = Double.parseDouble(txtubicacion3.getText().toString().split(",")[1]);

        i.putExtra("Inicial", new double[]{latInicial, lonInicial});
        i.putExtra("Final", new double[]{latFinal, lonFinal});
        startActivity(i);
    }

    public void lanzarLocalizacion() {
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        local = new Localizacion();
        local.setGps(this);
    }


    private void jsonParse() {
        //   String url = "http://192.168.43.109/gpio/1";
        // String url = "http://192.168.0.17/gpio/1";
        String url = "https://api.myjson.com/bins/113ufm";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("coordenadas");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject coordi = jsonArray.getJSONObject(i);
                                String latitud = coordi.getString("latitud");
                                String longitud = coordi.getString("longitud");
                                txtubicacion2.append(latitud + "," + longitud + " ");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mPosicion.add(request);
    }

    private void jsonParseF() {
        //  String url = "http://192.168.43.109/gpio/1";
        //   String  url= "https://api.myjson.com/bins/ib6pm";
        //   String url = "http://192.168.0.17/gpio/1";
        String url = "https://api.myjson.com/bins/g9o9e";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("coordenadas");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject coordf = jsonArray.getJSONObject(i);
                                String latitud = coordf.getString("latitud");
                                String longitud = coordf.getString("longitud");
                                txtubicacion3.append(latitud + "," + longitud + " ");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mPosicionF.add(request);
    }

}
