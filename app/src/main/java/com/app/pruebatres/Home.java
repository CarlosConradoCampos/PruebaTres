package com.app.pruebatres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Home extends AppCompatActivity {

    private ArrayAdapter<String> adaptador;
    private String[]tipos;
    private Spinner spnImportancia;
    private TextInputLayout tilTitulo, tilFecha, tilObservacion, tilLugar, tilDias;
    private Button btnRegistro, btnEliminarUsuario;
    private Aviso aviso;
    private int idRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        referencias();
        eventos();
    }

    private void eventos() {
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar();
            }
        });

    }

    private void verificar() {
        Boolean registrar = true;
        if(tilTitulo.getEditText().getText().toString().isEmpty()){
            tilTitulo.setError("Debe llenar este campo");
            registrar = false;
        }else{
            tilTitulo.setError(null);
        }
        if(tilObservacion.getEditText().getText().toString().isEmpty()){
            tilObservacion.setError("Debe llenar este campo");
            registrar = false;
        }else{
            tilObservacion.setError(null);
        }
        if(tilLugar.getEditText().getText().toString().isEmpty()){
            tilLugar.setError("debe llenar este campo");
            registrar= false;
        }else{
            tilLugar.setError(null);
        }
        if(tilFecha.getEditText().getText().toString().isEmpty()){
            tilFecha.setError("debe llenar este campo");
            registrar=false;
        }else{
            tilFecha.setError(null);
        }
        if(tilDias.getEditText().getText().toString().isEmpty()){
            tilDias.setError("debe llenar este campo");
            registrar = false;
        }else{
            tilDias.setError(null);
        }
        if(spnImportancia.getSelectedItem().toString().isEmpty()){
            Toast.makeText(this, "Debe Seleccionar La importancia del aviso", Toast.LENGTH_SHORT).show();
            registrar = false;
        }else if(spnImportancia.getSelectedItem().toString().equals("Seleccione Importancia")){
            Toast.makeText(this, "Debe Seleccionar La importancia del aviso", Toast.LENGTH_SHORT).show();
            registrar = false;
        }
        if(registrar){
            aviso = new Aviso();
            aviso.setIdUsuario(idRecibido);
            aviso.setFecha(tilFecha.getEditText().getText().toString());
            aviso.setImportancia(spnImportancia.getSelectedItem().toString());
            aviso.setTiempoAviso(tilDias.getEditText().getText().toString());
            aviso.setLugar(tilLugar.getEditText().getText().toString());
            aviso.setObservacion(tilObservacion.getEditText().getText().toString());
            aviso.setTitulo(tilTitulo.getEditText().getText().toString());
            registrarAviso();

        }
    }


    private void registrarAviso() {
        AdministradorAvisos db = new AdministradorAvisos(this, "Recordatorio", null, 2);
        try (SQLiteDatabase dataBase = db.getWritableDatabase()) {
            if (dataBase != null) {
                String[] parametros = new String[7];
                parametros[0] = String.valueOf(aviso.getIdUsuario());
                parametros[1] = aviso.getTitulo();
                parametros[2] = aviso.getObservacion();
                parametros[3] = aviso.getFecha();
                parametros[4] = aviso.getTiempoAviso();
                parametros[5] = aviso.getLugar();
                parametros[6] = aviso.getImportancia();
                dataBase.execSQL("insert into avisos (idUsuario,titulo,observacion,fecha,tiempoAviso,lugar,importancia) " +
                        "values(?,?,?,?,?,?,?)", parametros);
            }
            dataBase.close();
            limpiar();
        } catch (Exception e) {
            Toast.makeText(this, "Error:" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar() {
        tilTitulo.getEditText().setText("");
        tilDias.getEditText().setText("");
        tilFecha.getEditText().setText("");
        tilLugar.getEditText().setText("");
        tilObservacion.getEditText().setText("");
        spnImportancia.setSelection(0);
    }


    private void referencias() {
        Bundle extras = getIntent().getExtras();
        idRecibido = extras.getInt("id");
        tipos = new String[4];
        tipos[0]="Seleccione Importancia";
        tipos[1]="Baja";
        tipos[2]="Media";
        tipos[3]="Alta";
        spnImportancia = findViewById(R.id.spnImportancia);
        tilDias = findViewById(R.id.tilDias);
        tilFecha = findViewById(R.id.tilFecha);
        tilLugar = findViewById(R.id.tilLugar);
        tilObservacion = findViewById(R.id.tilObservacion);
        tilTitulo = findViewById(R.id.tilTitulo);
        btnEliminarUsuario = findViewById(R.id.btnEliminarUsuario);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnEliminarUsuario = findViewById(R.id.btnEliminarUsuario);


        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipos);
        spnImportancia.setAdapter(adaptador);

    }
}