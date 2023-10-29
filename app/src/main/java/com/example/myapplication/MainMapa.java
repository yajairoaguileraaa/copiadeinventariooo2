package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;


public class MainMapa extends AppCompatActivity {

    MapView map = null;
    MapController mapController = null;
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        setContentView(R.layout.activity_main_mapa);

        map = (MapView) findViewById(R.id.mapaOSM);
        map.setMultiTouchControls(true);

        mapController = (MapController) map.getController();
        GeoPoint startPoint = new GeoPoint(-33.4489, -70.6693);
        mapController.setCenter(startPoint);

        mapController.setZoom(14);

        addMarker(-33.4489, -70.6693, "Juan Pérez", "+56 9 1234 5678");
        addMarker(-33.45, -70.67, "María González", "+56 9 8765 4321");
        addMarker(-33.46, -70.68, "Pedro Rodríguez", "+56 9 1122 3344");
        addMarker(-33.47, -70.69, "Ana Sánchez", "+56 9 5566 7788");
        addMarker(-33.48, -70.70, "Carlos Martínez", "+56 9 9900 8811");

        Button volverbutton = (Button) findViewById(R.id.volverButton);
        volverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMapa.this, MainMenuDeActividades.class));
            }
        });


        Button locationButton = (Button) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                } else {
                    showLocation();
                }
            }
        });
    }

    private void addMarker(double lat, double lon, String name, String phoneNumber) {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(lat, lon));
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        marker.setTitle(name + "\n" + phoneNumber);

        map.getOverlays().add(marker);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocation();
            } else {

            }
        }
    }

    private void showLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            GeoPoint geoPoint = new GeoPoint(latitude, longitude);

            // Agrega un marcador en la ubicación actual
            Marker marker = new Marker(map);
            marker.setPosition(geoPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle("Estoy aquí");
            map.getOverlays().add(marker);

            // Anima el zoom y el centro del mapa
            map.getController().animateTo(geoPoint);
            map.getController().zoomTo(14);
        } else {
            Toast.makeText(this, "No se puede encontrar la ubicación actual", Toast.LENGTH_SHORT).show();
        }
    }



}

