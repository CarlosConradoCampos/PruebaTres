package com.app.pruebatres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RestablecerContrasena extends AppCompatActivity {

    int idRecibido;
    Usuario user;
    Button btnRestablecer, btnAceptar;
    EditText edtRespuesta, edtContraseña, edtRepitaContraseña;
    TextView tvTituloRestablecer, tvPregunta;
    SQLiteDatabase dataBase;
    AdministradorBaseDatos db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contrasena);
        referencias();
        eventos();
        ejecutarSQL();
        tvTituloRestablecer.setText(user.getNombre()+ " para Restablecer la contraseña\nresponda la pregunta.");
    }

    private void eventos() {
        btnRestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtRespuesta.getText().toString().isEmpty()){
                    edtRespuesta.setError("Debe Ingresar una respuesta");
                }else if(edtRespuesta.getText().toString().equals(user.getPregunta())){
                    edtRespuesta.setError(null);
                    edtRespuesta.setVisibility(View.INVISIBLE);
                    tvPregunta.setVisibility(View.INVISIBLE);
                    tvTituloRestablecer.setText("Ingrese una Nueva Contraseña");
                    btnAceptar.setEnabled(true);
                    btnAceptar.setVisibility(View.VISIBLE);
                    btnRestablecer.setVisibility(View.INVISIBLE);
                    edtContraseña.setVisibility(View.VISIBLE);
                    edtRepitaContraseña.setVisibility(View.VISIBLE);


                }else{
                    edtRespuesta.setError("Respuesta Incorrecta");
                }
            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtContraseña.getText().toString().equals(edtRepitaContraseña.getText().toString())){
                    edtContraseña.setError(null);
                    edtRepitaContraseña.setError(null);
                    String contraseña = edtContraseña.getText().toString();
                    AdministradorBaseDatos db = new AdministradorBaseDatos(RestablecerContrasena.this, "Recordatorio", null, 1);
                    try(SQLiteDatabase database = db.getReadableDatabase()) {
                        String sql = "UPDATE usuario SET contraseñaUsuario = '"+contraseña+"'";
                        database.execSQL(sql);
                        Toast.makeText(RestablecerContrasena.this, "Contraseña Actualizada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RestablecerContrasena.this, Login.class);
                        startActivity(intent);
                        finish();
                        database.close();
                    }
                }else{
                    edtContraseña.setError("Las Contraseñas no son Iguales");
                    edtRepitaContraseña.setError("Las contraseñas no son Iguales");
                }
            }
        });

    }

    private void ejecutarSQL() {

        int id = idRecibido;
        AdministradorBaseDatos db = new AdministradorBaseDatos(this, "Recordatorio", null, 1);
        try (SQLiteDatabase dataBase = db.getWritableDatabase()) {
            String[] projection = {
                    "idUsuario",
                    "nombreUsuario",
                    "edadUsuario",
                    "contraseñaUsuario",
                    "emailUsuario",
                    "fechaRegistroUsuario",
                    "preguntaRecuperacion"
            };

            String selection = "idUsuario = ?";
            String[] selectionArgs = {String.valueOf(id)};

            Cursor cursor = dataBase.query(
                    "usuario",  // La tabla a consultar
                    projection,   // Las columnas a devolver
                    selection,    // La cláusula WHERE
                    selectionArgs,// Los parámetros de la cláusula WHERE
                    null,         // No agrupar los resultados
                    null,         // No filtrar por un grupo de resultados
                    null          // No ordenar los resultados
            );

            while (cursor.moveToNext()) {
                int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"));
                int edad = cursor.getInt(cursor.getColumnIndexOrThrow("edadUsuario"));
                String contraseña = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("emailUsuario"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistroUsuario"));
                String pregunta = cursor.getString(cursor.getColumnIndexOrThrow("preguntaRecuperacion"));
                user = new Usuario();
                user.setNombre(nombre);
                user.setIdUsuario(idUsuario);
                user.setPregunta(pregunta);
            }
            cursor.close();
        }
    }




    private void referencias() {
        Bundle extras = getIntent().getExtras();
        idRecibido = extras.getInt("id");
        btnRestablecer = findViewById(R.id.btnRestablecer);
        edtRespuesta = findViewById(R.id.edtRespuesta);
        tvTituloRestablecer = findViewById(R.id.tvTituloRestablecer);
        edtContraseña = findViewById(R.id.edtContraseña);
        edtRepitaContraseña = findViewById(R.id.edtRepitaContraseña);
        edtContraseña.setVisibility(View.INVISIBLE);
        edtRepitaContraseña.setVisibility(View.INVISIBLE);
        tvPregunta = findViewById(R.id.tvPregunta);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnAceptar.setVisibility(View.INVISIBLE);

    }
}