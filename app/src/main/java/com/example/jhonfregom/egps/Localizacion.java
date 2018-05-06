package com.example.jhonfregom.egps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class Localizacion implements LocationListener {
    GPSActivity gps;
    public Localizacion(){

    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        String datoubicacion = "Mi ubicacion actual es: Latitud"+location.getLatitude()+" Longitud:"+location.getLongitude();
        gps.setUbicacion(datoubicacion);
        gps.setLocation(location);
    }


    public GPSActivity getGps() {
        return gps;
    }

    public void setGps(GPSActivity gps) {
        this.gps = gps;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
