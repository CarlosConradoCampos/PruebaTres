package com.app.pruebatres;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdministradorBaseDatos extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Recordatorio.db";
    private static final int DATABASE_VERSION = 1;

    private static final String NOMBRE_TABLA = "usuario";
    private static final String ID_USUARIO = "idUsuario";
    private static final String NOMBRE_USUARIO = "nombreUsuario";
    private static final String EDAD_USUARIO = "edadUsuario";
    private static final String CONTRASEÑA_USUARIO = "contraseñaUsuario";
    private static final String EMAIL_USUARIO = "emailUsuario";
    private static final String FECHA_REGISTRO = "fechaRegistroUsuario";
    private static final String PREGUNTA_RECUPERACION = "preguntaRecuperacion";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + NOMBRE_TABLA + " (" +
                    ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOMBRE_USUARIO + " TEXT, " +
                    EDAD_USUARIO + " INTEGER, " +
                    CONTRASEÑA_USUARIO + " TEXT, " +
                    EMAIL_USUARIO + " TEXT, " +
                    FECHA_REGISTRO + " DATE, " +
                    PREGUNTA_RECUPERACION + " TEXTO) ";

    public AdministradorBaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecuta la sentencia SQL para crear la tabla
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En este ejemplo, simplemente eliminamos la tabla y volvemos a crearla
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA);
        onCreate(db);
    }
}
