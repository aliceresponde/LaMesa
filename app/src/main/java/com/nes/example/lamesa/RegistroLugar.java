package com.nes.example.lamesa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nes.example.android.LaMesaActivity;
import com.nes.example.parser.LugarPost;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class RegistroLugar extends LaMesaActivity implements LocationListener  {

    private EditText inputNombre;
    private EditText inputDireccion;
    private EditText inputLatitud;
    private EditText inputLongitud;
    private Button btnRegistrar;
    private Button btnVerificarLugar;
    private static int REQUEST_CODE_MAP_ACTIVITY=16843564;

    // Reference to the LocationManager and LocationListener
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private static Location mBestReading;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;
    private static final long ONE_MIN = 1000 * 60;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private String bestProvider;
    // default minimum time between new readings ms
    private long mMinTime = 5000;
    // default minimum distance between old and new readings mts.
    private float mMinDistance = 10.0f;
    private static LatLng locationFromMap;
    private static boolean errorLocationFromMap=false;

    @Override
    protected void onResume() {
        super.onResume();

        // Get best last location measurement
        if(errorLocationFromMap) {
            mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);
        }
        if (mBestReading!=null) {
            setLatitudLongitudLugar(mBestReading.getLatitude() + "", mBestReading.getLongitude() + "");
        }
        if(errorLocationFromMap) {
            setListenerLocation();
        }
    }

    public void setListenerLocation(){
        if (bestProvider!=null && mLocationManager.getProvider(bestProvider)!=null) {
            mLocationManager.requestLocationUpdates(
                    bestProvider, mMinTime,
                    mMinDistance, this);
        }
    }

    public void setLatitudLongitudLugar(String lat, String lon){
        inputLatitud.setText(lat);
        inputLongitud.setText(lon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lugar);

        btnRegistrar=(Button) findViewById(R.id.btn_registrar_action);
        btnVerificarLugar=(Button) findViewById(R.id.btn_check_place_map);
        inputNombre=(EditText) findViewById(R.id.editTexNombre);
        inputDireccion=(EditText) findViewById(R.id.editTextDir);
        inputLatitud=(EditText) findViewById(R.id.editTextLat);
        inputLongitud=(EditText) findViewById(R.id.editTextLon);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputLongitud!=null && inputLongitud.getText().length()>0 &&
                     inputLatitud!=null && inputLatitud.getText().length()>0 ) {
                    //1
                    LugarPost lugar = new LugarPost();
                    lugar.setNombre(inputNombre.getText().toString());
                    lugar.setDir(inputDireccion.getText().toString());
                    ParseGeoPoint geopoint=new ParseGeoPoint(
                            Double.valueOf(inputLatitud.getText().toString()),
                            Double.valueOf(inputLongitud.getText().toString()));
                    lugar.setLocation(geopoint);
                    lugar.setUser(ParseUser.getCurrentUser());

                    // 2   ESTOY  INGRESANDO EL TIPO DE SEGURIDAD DE TABLA (LRCTURA)
                    ParseACL acl = new ParseACL();
                    acl.setPublicReadAccess(true);
                    lugar.setACL(acl);

                    // 3 GUARDO EN EL SERVIDOR (PARSE) EL OBJETO, CORRESPÓNDIENTE AL LUGAR
                    lugar.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Guardado exitosamente!", Toast.LENGTH_SHORT).show();
                                setListenerLocation();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Pulse Seleccionar Ubicación en Mapa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // QUIENO UBICAR EN EL MAPA LA POSICION DEL LUGAR QUE QUIERO REGISTAR
        btnVerificarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_MAP_ACTIVITY);
                }catch (Exception e){
                    errorLocationFromMap=true;
                    recreate();
                }
            }
        });
        // Acquire reference to the LocationManager
        if (null == (mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE)))
            finish();
    }

    @Override
    protected void onPause() {
        mLocationManager.removeUpdates(this);
        super.onStop();
    }
    // Get the last known location from all providers
    // return best reading that is as accurate as minAccuracy and
    // was taken no longer then minAge milliseconds ago. If none,
    // return null.

    private Location bestLastKnownLocation(float minAccuracy, long maxAge) {
        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestAge = Long.MIN_VALUE;
        List<String> matchingProviders = mLocationManager.getAllProviders();

        for (String provider : matchingProviders) {
            Location location = mLocationManager.getLastKnownLocation(provider);
            if (location != null) {
                float accuracy = location.getAccuracy();
                long time = location.getTime();
                if (accuracy < bestAccuracy) {
                    bestResult = location;
                    bestAccuracy = accuracy;
                    bestAge = time;
                    bestProvider=provider;
                }
            }
        }

        // Return best reading or null
        if (bestAccuracy > minAccuracy
                || (System.currentTimeMillis() - bestAge) > maxAge) {
            return null;
        } else {
            return bestResult;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_MAP_ACTIVITY && resultCode== Activity.RESULT_OK){
            Bundle bundle=data.getExtras();
            locationFromMap=(LatLng)bundle.getParcelable(MapActivity.KEY_MAP_BUNDLE);
            if (locationFromMap!=null){
                setLatitudLongitudLugar(locationFromMap.latitude + "", locationFromMap.longitude + "");
            }
        }
    }

    @Override
    public void onLocationChanged(Location currentLocation) {
        if (mBestReading==null){
            mBestReading=currentLocation;

        }else if (currentLocation.getTime()>mBestReading.getTime()){
            mBestReading=currentLocation;
        }
        if (mBestReading!=null) {
            setLatitudLongitudLugar(mBestReading.getLatitude() + "", mBestReading.getLongitude() + "");
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // not implemented
    }

    @Override
    public void onProviderEnabled(String provider) {
        // not implemented
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // not implemented
    }
}
