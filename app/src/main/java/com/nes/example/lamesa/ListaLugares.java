package com.nes.example.lamesa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        ParseQueryAdapter.QueryFactory<ParseObject> factory =
                new ParseQueryAdapter.QueryFactory<ParseObject>() {
                    public ParseQuery create() {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery(ContratoBD.tablaLugar);
                        return query;
                    }
                };
        adaptador =new CustomAdapter(this,factory);
        adaptador.setTextKey(ContratoBD.lugarColNombre);

        listaLayout.setOnItemClickListener(adaptador);
        listaLayout.setAdapter(adaptador);
    }

    private class CustomAdapter extends ParseQueryAdapter<ParseObject> implements AdapterView.OnItemClickListener{

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

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LugarPost lugar=(LugarPost) adaptador.getItem(position);
            Toast.makeText(getApplicationContext(), "Click en Lugar", Toast.LENGTH_SHORT).show();
            if (lugar!=null){
                Intent intent=new Intent(getApplicationContext(),MapActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString(MapActivity.KEY_MAP_BUNDLE,lugar.getLocation().getLatitude()+","+lugar.getLocation().getLongitude());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}
