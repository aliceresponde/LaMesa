package com.nes.example.android;

/**
 * Created by user on 20/12/2014.
 * Este codigo contirne la informacion de las tablas que se encuentran en Parse. 
 * Con el fin de poder acceder a los nombres de los campos de este, a partir de constantes q
 * Referencian sus nombres tal como esta en Parse
 */
public class ContratoBD {

    //Tabla Usuario
    public final static String tablaUsuario="User";
    public final static String usuarioColNombre="username";
    public final static String usuarioColContrasena="password";
    public final static String usuarioColEmail="email";
    public final static String usuarioColCheckMail="emailVerified";

    //Tabla Lugares
    public final static String tablaLugar="Lugar";
    public final static String lugarColNombre="nombre";
    public final static String lugarColDireccion="direccion";
    public final static String lugarColGeopoint="location";
    public final static String lugarRelUserId="userId";

}
