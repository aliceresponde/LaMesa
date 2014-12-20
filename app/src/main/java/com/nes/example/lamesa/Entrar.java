package com.nes.example.lamesa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nes.example.android.LaMesaActivity;


public class Entrar extends LaMesaActivity {

    private RelativeLayout contenedor;
    private EditText contrasena;
    private EditText nombre;
    private TextView nombreLabel;
    private TextView contrasenaLabel;
    private Button btnIngresar;
    private String TAG=this.getClass().getName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrar_activity);
        setParse();
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

       /* ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();*/
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
}
