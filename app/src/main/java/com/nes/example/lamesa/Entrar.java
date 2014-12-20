package com.nes.example.lamesa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Entrar extends Activity {

    private RelativeLayout contenedor;
    private EditText contrasena;
    private EditText nombre;
    private TextView nombreLabel;
    private TextView contrasenaLabel;
    private Button btnIngresar;

    public String TAG="ENTRADA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrar_activity);

        Log.v(TAG, "ON CREATE");

        contenedor= (RelativeLayout) findViewById(R.id.contenedor);
        contrasenaLabel= (TextView) findViewById(R.id.textViewContrasena);
        nombreLabel= (TextView) findViewById(R.id.textViewNombre);
        nombre= (EditText) findViewById(R.id.editTextNombre);
        contrasena= (EditText) findViewById(R.id.editTextContrasenia);
        btnIngresar= (Button) findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals("pepito") &&
                        contrasena.getText().toString().equals("pepito")){
                    Intent intent = new Intent(getApplicationContext(), Opciones.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Credenciales inválidas",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sabado_frio, menu);
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
