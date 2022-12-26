package com.app.pruebatres;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private TextInputLayout tilNombreUsuario, tilEdad, tilContraseña, tilReingreseContraseña, tilEmail, tilPregunta;
    private Button btnAceptar, btnRegistro;
    private TextView tvRecuperar;
    private Calendar calendar;
    private Date fecha;
    private CardView cvRegistro;
    Usuario user;
    private DateFormat formato;
    private String fechaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        referencia();
        eventos();

    }

    private void eventos() {
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDatos();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tilPregunta.getEditText().getText().toString().isEmpty()) {
                    tilPregunta.setError("Debe Responder esta Pregunta");
                } else {
                    tilPregunta.setError(null);
                    user.setPregunta(tilPregunta.getEditText().getText().toString());
                    crearBD();
                }
            }
        });

    }

    private void verificarDatos() {
        Boolean ingresar = true;
        if (tilNombreUsuario.getEditText().getText().toString().isEmpty()) {
            tilNombreUsuario.setError("Debe ingresar su Nombre");
            ingresar = false;
        } else {
            tilNombreUsuario.setError(null);
        }
        if (tilEdad.getEditText().getText().toString().isEmpty()) {
            tilEdad.setError("Ingrese su Edad");
            ingresar = false;
        } else {
            tilEdad.setError(null);
        }
        if (tilEmail.getEditText().getText().toString().isEmpty()) {
            tilEmail.setError("Debe ingresar su Email");
            ingresar = false;
        } else {
            if (verificarEmail()) {
                tilEmail.setError(null);
            } else {
                tilEmail.setError("Email no es Valido");
                ingresar = false;
            }
        }
        if (tilContraseña.getEditText().getText().toString().isEmpty()) {
            tilContraseña.setError("Debe ingresar una contraseña");
            ingresar = false;
        }
        if (tilReingreseContraseña.getEditText().getText().toString().isEmpty()) {
            tilReingreseContraseña.setError("Debe reingresar la Contraseña");
            ingresar = false;
        }
        if (tilContraseña.getEditText().getText().toString().equals(tilReingreseContraseña.getEditText().getText().toString())) {
            if (ingresar) {
                if(verificarNombreUsuario()){
                    tilNombreUsuario.setError(null);
                    tilContraseña.setError(null);
                    tilReingreseContraseña.setError(null);
                    user = new Usuario();
                    user.setNombre(tilNombreUsuario.getEditText().getText().toString());
                    user.setEdad(Integer.parseInt(tilEdad.getEditText().getText().toString()));
                    user.setContraseña(tilContraseña.getEditText().getText().toString());
                    user.setEmail(tilEmail.getEditText().getText().toString());
                    user.setFechaRegistro(fechaFinal);
                    cvRegistro.setVisibility(View.INVISIBLE);
                    btnRegistro.setVisibility(View.INVISIBLE);
                    cvRegistro.setEnabled(false);
                    btnRegistro.setEnabled(false);
                    btnAceptar.setEnabled(true);
                    btnAceptar.setVisibility(View.VISIBLE);
                    tilPregunta.setVisibility(View.VISIBLE);
                    tilPregunta.setEnabled(true);
                    tvRecuperar.setVisibility(View.VISIBLE);
                }


            }
        } else {
            tilContraseña.setError("Las Contraseñas no son iguales");
            tilReingreseContraseña.setError("Las Contraseñas no son iguales");
        }
    }

    private Boolean verificarNombreUsuario() {
        Boolean registrar = false;
        String name = tilNombreUsuario.getEditText().getText().toString();
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

            Cursor cursor = dataBase.query(
                    "usuario",  // La tabla a consultar
                    projection,   // Las columnas a devolver
                    selection,    // La cláusula WHERE
                    selectionArgs,// Los parámetros de la cláusula WHERE
                    null,         // No agrupar los resultados
                    null,         // No filtrar por un grupo de resultados
                    null          // No ordenar los resultados
            );

            if(cursor.getColumnCount() == -1){
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


                }
                if(name.equals(user.getNombre())){
                    tilNombreUsuario.setError("Este Nombre de Usuario ya Existe");
                }else{
                    registrar = true;
                }
                cursor.close();

            }else{
                registrar = true;
            }


        }

        return registrar;

    }

    private Boolean verificarEmail() {
        Pattern email = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher validar = email.matcher(tilEmail.getEditText().getText().toString());
        return (validar.find());

    }


    private void referencia() {
        tilNombreUsuario = findViewById(R.id.tilNombre);
        tilEdad = findViewById(R.id.tilEdad);
        tilContraseña = findViewById(R.id.tilContraseña);
        tilReingreseContraseña = findViewById(R.id.tilReingreseContraseña);
        tilEmail = findViewById(R.id.tilEmail);
        btnRegistro = findViewById(R.id.btnRegistrar);
        calendar = Calendar.getInstance();
        fecha = calendar.getTime();
        formato = new SimpleDateFormat("dd/mm/yy");
        fechaFinal = formato.format(fecha);
        cvRegistro = findViewById(R.id.cvRegistro);


        tilPregunta = findViewById(R.id.tilPregunta);
        tilPregunta.setVisibility(View.INVISIBLE);
        tilPregunta.setEnabled(false);
        tvRecuperar = findViewById(R.id.tvRecuperar);
        tvRecuperar.setVisibility(View.INVISIBLE);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnAceptar.setVisibility(View.INVISIBLE);
        btnAceptar.setEnabled(false);

    }

    private void crearBD() {
        AdministradorBaseDatos db = new AdministradorBaseDatos(this, "Recordatorio", null, 1);
        try (SQLiteDatabase dataBase = db.getWritableDatabase()) {
            if (dataBase != null) {
                String[] parametros = new String[6];
                parametros[0] = user.getNombre();
                parametros[1] = String.valueOf(user.getEdad());
                parametros[2] = user.getContraseña();
                parametros[3] = user.getEmail();
                parametros[4] = user.getFechaRegistro();
                parametros[5] = user.getPregunta();
                dataBase.execSQL("insert into usuario (nombreUsuario,edadUsuario,contraseñaUsuario,emailUsuario,fechaRegistroUsuario,preguntaRecuperacion) " +
                        "values(?,?,?,?,?,?)", parametros);
            }
            dataBase.close();
            //ejecutarSQL();
            Intent intent = new Intent(Registro.this, Login.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error:" + e, Toast.LENGTH_SHORT).show();
        }
    }

}
