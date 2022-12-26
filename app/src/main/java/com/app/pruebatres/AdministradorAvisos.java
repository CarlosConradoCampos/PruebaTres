package com.app.pruebatres;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdministradorAvisos extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Recordatorio.db";
    private static final int DATABASE_VERSION = 2;

    private static final String NOMBRE_TABLA = "avisos";
    private static final String ID_USUARIO = "idUsuario";
    private static final String TITULO = "titulo";
    private static final String OBSERVACION = "observacion";
    private static final String FECHA = "fecha";
    private static final String TIEMPO_AVISO = "tiempoAviso";
    private static final String LUGAR = "lugar";
    private static final String IMPORTANCIA = "importancia";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + NOMBRE_TABLA + " (" +
                    ID_USUARIO + " TEXT, " +
                    TITULO + " TEXT, " +
                    OBSERVACION + " TEXT, " +
                    FECHA + " TEXT, " +
                    TIEMPO_AVISO + " TEXT, " +
                    LUGAR + " TEXT, " +
                    IMPORTANCIA + " TEXTO) ";

    public AdministradorAvisos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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