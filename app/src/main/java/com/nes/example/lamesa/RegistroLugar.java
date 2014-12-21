package com.nes.example.lamesa;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    // Reference to the LocationManager and LocationListener
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private static Location mBestReading;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;
    private static final long ONE_MIN = 1000 * 60;
    private static final long ONE_HOUR = ONE_MIN * 5;
    private String bestProvider;
    // default minimum time between new readings ms
    private long mMinTime = 5000;
    // default minimum distance between old and new readings mts.
    private float mMinDistance = 10.0f;


    @Override
    protected void onResume() {
        super.onResume();
        // Get best last location measurement
        mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, ONE_HOUR);
        if (mBestReading!=null) {
            inputLatitud.setText(mBestReading.getLatitude()+"");
            inputLongitud.setText(mBestReading.getLongitude()+"");
        }
        // Register for network location updates
        if (bestProvider!=null && mLocationManager.getProvider(bestProvider)!=null) {
            mLocationManager.requestLocationUpdates(
                    bestProvider, mMinTime,
                    mMinDistance, this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lugar);

        btnRegistrar=(Button) findViewById(R.id.btn_registrar_action);
        inputNombre=(EditText) findViewById(R.id.editTexNombre);
        inputDireccion=(EditText) findViewById(R.id.editTextDir);
        inputLatitud=(EditText) findViewById(R.id.editTextLat);
        inputLongitud=(EditText) findViewById(R.id.editTextLon);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBestReading!=null) {
                    //1
                    LugarPost lugar = new LugarPost();
                    lugar.setNombre(inputNombre.getText().toString());
                    lugar.setDir(inputDireccion.getText().toString());
                    ParseGeoPoint geopoint=new ParseGeoPoint(mBestReading.getLatitude(), mBestReading.getLongitude());
                    lugar.setLocation(geopoint);
                    lugar.setUser(ParseUser.getCurrentUser());

                    // 2
                    ParseACL acl = new ParseACL();
                    acl.setPublicReadAccess(true);
                    lugar.setACL(acl);

                    // 3
                    lugar.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Guardado exitosamente!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"¿No se encuentra su ubicación.. tiene internet?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Acquire reference to the LocationManager
        if (null == (mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE)))
            finish();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_lugar, menu);
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

    @Override
    public void onLocationChanged(Location currentLocation) {
        if (mBestReading==null){
            mBestReading=currentLocation;

        }else if (currentLocation.getTime()>mBestReading.getTime()){
            mBestReading=currentLocation;
        }
        if (mBestReading!=null) {
            inputLatitud.setText(mBestReading.getLatitude()+"");
            inputLongitud.setText(mBestReading.getLongitude()+"");
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
