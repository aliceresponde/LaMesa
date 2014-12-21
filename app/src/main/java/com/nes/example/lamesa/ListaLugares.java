package com.nes.example.lamesa;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nes.example.android.ContratoBD;
import com.nes.example.android.LaMesaActivity;
import com.nes.example.parser.LugarPost;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class ListaLugares extends LaMesaActivity{

    private String TAG=this.getClass().getName();
    private ListView listaLayout;
    private CustomAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_lugares);

        listaLayout = (ListView) findViewById(R.id.listaUbicaciones);

        /*adaptador = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listaDatos);*/

        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery create() {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery(ContratoBD.tablaLugar);
                        return query;
                    }
                };
        adaptador =new CustomAdapter(this,factory);
        adaptador.setTextKey(ContratoBD.lugarColNombre);


        listaLayout.setAdapter(adaptador);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class CustomAdapter extends ParseQueryAdapter<ParseObject> {

        private Context context;

        public CustomAdapter(
                Context context,
                com.parse.ParseQueryAdapter.QueryFactory<ParseObject> queryFactory) {
            super(context, queryFactory);
            this.context = context;
        }

        @Override
        public View getItemView(ParseObject object, View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(this.context, R.layout.list_row, null);
            }

            TextView textNombre = (TextView) v.findViewById(R.id.nombreLugar);
            TextView textDireccion = (TextView) v.findViewById(R.id.direccionLugar);
            //ImageView imageLugar = (ImageView) v.findViewById(R.id.imagenLugar);

            LugarPost lugar = (LugarPost) object;
            textNombre.setText(lugar.getNombre());
            textDireccion.setText(lugar.getDir());
            //ParseImageView imageView  = (ParseImageView) v.findViewById(R.id.imagenLugar);
            //imageView.setParseFile((ParseFile) photo.get("image"));
            //imageView.loadInBackground();

            return v;
        }
    }
}
