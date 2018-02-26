package com.example.efe.map_n;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.text.Html;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import static com.example.efe.map_n.R.id.map;
import static com.example.efe.map_n.R.id.text;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback    {

    private GoogleMap mMap;
    private Function.placeIdTask asyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        setContentView (R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ().findFragmentById (map);
        mapFragment.getMapAsync (this);

        final TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

        Typeface weatherFont;


        weatherFont = Typeface.createFromAsset (getApplicationContext ().getAssets (), "fonts/weathericons-regular-webfont.ttf");

        cityField = findViewById (R.id.city_field);
        updatedField = findViewById (R.id.updated_field);
        detailsField = findViewById (R.id.details_field);
        currentTemperatureField = findViewById (R.id.current_temperature_field);
        humidity_field = findViewById (R.id.humidity_field);
        pressure_field = findViewById (R.id.pressure_field);
        weatherIcon = findViewById (R.id.weather_icon);
        weatherIcon.setTypeface (weatherFont);


        Function.placeIdTask asyncTask = new Function.placeIdTask (new Function.AsyncResponse () {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                cityField.setText (weather_city);
                updatedField.setText (weather_updatedOn);
                detailsField.setText (weather_description);
                currentTemperatureField.setText (weather_temperature);
                humidity_field.setText ("Humidity: " + weather_humidity);
                pressure_field.setText ("Pressure: " + weather_pressure);
                weatherIcon.setText (Html.fromHtml (weather_iconText));

            }
        });



           // asyncTask.execute ( "41","29"); //  asyncTask.execute("Latitude", "Longitude")

    }





    public void onMapSearch(View view) {


        switch (view.getId ()) {
            case R.id.search_button:

            final EditText locationSearch = findViewById (R.id.editText);
            final String location = locationSearch.getText ().toString ();
            List<Address> addressList = null;

            if (location != null || !location.equals ("")) {
                Geocoder geocoder = new Geocoder (this);
                try {
                    addressList = geocoder.getFromLocationName (location, 1);

                } catch (IOException e) {
                    e.printStackTrace ();
                }
                final Address address = addressList.get (0);
                final LatLng latLng = new LatLng (address.getLatitude (), address.getLongitude ());
////////////////////////////////////////
                final TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;

                Typeface weatherFont;


                weatherFont = Typeface.createFromAsset (getApplicationContext ().getAssets (), "fonts/weathericons-regular-webfont.ttf");

                cityField = findViewById (R.id.city_field);
                updatedField = findViewById (R.id.updated_field);
                detailsField = findViewById (R.id.details_field);
                currentTemperatureField = findViewById (R.id.current_temperature_field);
                humidity_field = findViewById (R.id.humidity_field);
                pressure_field = findViewById (R.id.pressure_field);
                weatherIcon = findViewById (R.id.weather_icon);
                weatherIcon.setTypeface (weatherFont);


                final List <Address> finalAddressList = addressList;
                Function.placeIdTask asyncTask = new Function.placeIdTask (new Function.AsyncResponse () {
                    public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {

                        cityField.setText (weather_city);
                        updatedField.setText (weather_updatedOn);
                        detailsField.setText (weather_description);
                        currentTemperatureField.setText (weather_temperature);
                        humidity_field.setText ("Humidity: " + weather_humidity);
                        pressure_field.setText ("Pressure: " + weather_pressure);
                        weatherIcon.setText (Html.fromHtml (weather_iconText));

                        /////////////////////////////


                        String a = (String) cityField.getText ();
                        String b = (String) currentTemperatureField.getText ();
                        String c = (String) detailsField.getText ();



                        mMap.addMarker (new MarkerOptions ().position (latLng).title (a +" "+b+ " "+c));

                        ///
                        mMap.animateCamera (CameraUpdateFactory.newLatLngZoom (latLng,18));
                        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow (locationSearch.getWindowToken (),0);

                    }
                });

                asyncTask.execute (String.valueOf (address.getLongitude ()), String.valueOf (address.getLatitude ()));





            }
            case map:
                mMap.clear ();
                return;

        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    public int getString() {
        return R.string.ara;
    }
}