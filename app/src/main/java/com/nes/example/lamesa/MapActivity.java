package com.nes.example.lamesa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity {

    private GoogleMap mGoogleMap;
    private Button btnConfirmar;
    private LatLng location;
    public static String KEY_MAP_BUNDLE="LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Getting reference to SupportMapFragment
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        btnConfirmar=(Button) findViewById(R.id.btn_confirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer(location);
            }
        });

        // Creating GoogleMap from SupportMapFragment
        mGoogleMap = fragment.getMap();

        // Enabling MyLocation button for the Google Map
        mGoogleMap.setMyLocationEnabled(true);

        // Setting OnClickEvent listener for the GoogleMap
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latlng) {
                addMarker(latlng);
            }
        });
    }

    // Adding marker on the GoogleMaps
    private void addMarker(LatLng latlng) {
        mGoogleMap.clear();
        location=latlng;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title(latlng.latitude + "," + latlng.longitude);
        mGoogleMap.addMarker(markerOptions);
    }

    // Invoking background thread to store the touched location in Remove MySQL server
    private void sendToServer(LatLng latlng) {
        Intent returnIntent = new Intent();
        Bundle bundle= new Bundle();
        bundle.putParcelable(KEY_MAP_BUNDLE, (Parcelable)latlng);
        returnIntent.putExtras(bundle);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
