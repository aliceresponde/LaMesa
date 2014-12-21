package com.nes.example.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.nes.example.parser.LugarPost;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by n.diazgranados on 20/12/2014.
 */

public class LaMesaActivity extends Activity {

    private String TAG=this.getClass().getName();
    private static Boolean habilitarParse=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "ON CREATE");
        setParse();
    }

    public void setParse(){
        if (!habilitarParse) {
            ParseObject.registerSubclass(LugarPost.class);
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "L00Wa4XTNKc9UbDSsiXwNdfZVldhoXKDxjc1PFPT", "W3APJFSjRX6US0bvLzyoqwn4fvifileFXOtPsvwb");
            habilitarParse=true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "ON RESUME");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "ON START");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "ON PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "ON STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "ON DESTROY");
    }
}





