package com.nes.example.lamesa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
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
        // Creating GoogleMap from SupportMapFragment
        mGoogleMap = fragment.getMap();
        // Enabling MyLocation button for the Google Map
        mGoogleMap.setMyLocationEnabled(true);

        Intent intent=getIntent();
        if (intent!=null && intent.getExtras()!=null && intent.getExtras().getString(KEY_MAP_BUNDLE)!=null){
            btnConfirmar.setVisibility(View.GONE);
            verLugar(intent.getStringExtra(KEY_MAP_BUNDLE));
        }else{
            seleccionarLugar();
        }

    }

    public void verLugar(String latLong){
        String[] values= latLong.split(",");

        LatLng location=new LatLng(Double.valueOf(values[0]),Double.valueOf(values[1]));
        addMarker(location);
    }

    public void seleccionarLugar(){

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retornarMarcador(location);
            }
        });

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
    private void retornarMarcador(LatLng latlng) {
        Intent returnIntent = new Intent();
        Bundle bundle= new Bundle();
        bundle.putParcelable(KEY_MAP_BUNDLE, (Parcelable)latlng);
        returnIntent.putExtras(bundle);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

}
