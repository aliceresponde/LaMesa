package com.nes.example.parser;

import com.nes.example.android.ContratoBD;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by user on 21/12/2014.
 */
@ParseClassName(ContratoBD.tablaLugar)
public class LugarPost extends ParseObject {

    public LugarPost() { }

    public String getNombre() {
        return getString(ContratoBD.lugarColNombre);
    }

    public void setNombre(String nombre) {
        put(ContratoBD.lugarColNombre, nombre);
    }

    public String getDir() {
        return getString(ContratoBD.lugarColNombre);
    }

    public void setDir(String dir) {
        put(ContratoBD.lugarColNombre, dir);
    }

    public ParseUser getUser() {
        return getParseUser(ContratoBD.lugarRelUserId);
    }

    public void setUser(ParseUser value) {
        put(ContratoBD.lugarRelUserId, value);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(ContratoBD.lugarColGeopoint);
    }

    public void setLocation(ParseGeoPoint value) {
        put(ContratoBD.lugarColGeopoint, value);
    }

    public static ParseQuery<LugarPost> getQuery() {
        return ParseQuery.getQuery(LugarPost.class);
    }
}