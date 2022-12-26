package com.app.pruebatres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {


    private TextView tvOlvide;
    private Button btnIngresar, btnRegistrar;
    private TextInputLayout tilUsuario, tilContraseña;
    private SharedPreferences sharedPreferences;
    private EditText edtUsuario;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        referencias();




        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
                finish();
            }
        });



        tvOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RestablecerContrasena.class);
                intent.putExtra("id", user.getIdUsuario());
                startActivity(intent);
                finish();
            }
        });



        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("usuario", tilUsuario.getEditText().getText().toString());
                editor.apply();

            }
        });




    }




    private void validarUsuario(){
        if(tilUsuario.getEditText().getText().toString().isEmpty()){
            tilUsuario.setError("Debe ingresar su Usuario");
        }else if(tilContraseña.getEditText().getText().toString().isEmpty()){
            tilContraseña.setError("Debe ingresar su contraseña");
        }else{
            ejecutarSQL();
        }
    }


    private void ejecutarSQL() {

        String name = tilUsuario.getEditText().getText().toString();
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

            String selection = "nombreUsuario = ?";
            String[] selectionArgs = {name};

            Cursor cursor = dataBase.query("usuario",projection,selection,selectionArgs,null,null,null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"));
                int edad = cursor.getInt(cursor.getColumnIndexOrThrow("edadUsuario"));
                String contraseña = cursor.getString(cursor.getColumnIndexOrThrow("contraseñaUsuario"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("emailUsuario"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fechaRegistroUsuario"));
                String pregunta = cursor.getString(cursor.getColumnIndexOrThrow("preguntaRecuperacion"));
                user = new Usuario();
                user.setNombre(nombre);
                user.setIdUsuario(id);
                if(contraseña.equals(tilContraseña.getEditText().getText().toString())){
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.putExtra("id",user.getIdUsuario());
                    startActivity(intent);
                    finish();
                }else{
                    tvOlvide.setVisibility(View.VISIBLE);
                    tvOlvide.setEnabled(true);
                }

            }
            cursor.close();
        }
    }

    private void referencias() {
        tvOlvide = findViewById(R.id.tvOlvide);
        tvOlvide.setVisibility(View.INVISIBLE);
        tvOlvide.setEnabled(false);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tilContraseña = findViewById(R.id.tilContraseña);
        tilUsuario = findViewById(R.id.tilUsuario);
        sharedPreferences = getSharedPreferences(tilUsuario.getEditText().getText().toString(),MODE_PRIVATE);
        edtUsuario = tilUsuario.getEditText();
        edtUsuario.setText(sharedPreferences.getString("usuario", "Usuario"));

    }
}