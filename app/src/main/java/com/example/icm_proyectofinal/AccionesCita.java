package com.example.icm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AccionesCita extends AppCompatActivity {
    TextView tv;
    TextView tv2;
    Button regresar;
    Button borrar, guardar, hecho;
    EditText nombre;
    EditText telefono;
    EditText cobro;
    Spinner metodo_p;
    EditText comentarios;
    ProyectoFinalBD conn;
    String ID ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones_cita);
        tv = findViewById(R.id.textview_dia3);
        tv2 = findViewById(R.id.textview_hora3);
        regresar = findViewById(R.id.regresar3);
        nombre = findViewById(R.id.nombre_cliente3);
        telefono = findViewById(R.id.telefono3);
        cobro = findViewById(R.id.cobro3);
        metodo_p = findViewById(R.id.metodo_p2);
        comentarios = findViewById(R.id.comentarios3);
        borrar = (Button) findViewById(R.id.borrar);
        guardar = (Button) findViewById(R.id.guardar);
        hecho = (Button) findViewById(R.id.hecho);

        conn = new ProyectoFinalBD(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        //Toast.makeText(this, extras.getString("ID"), Toast.LENGTH_SHORT).show();
        ID = extras.getString("ID");
        buscarCita(ID);


        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(ID);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar(ID);
            }
        });
        hecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atender(ID);
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                setResult(RESULT_OK);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }
    //para abrir calendario gráfico en la aplicación
    public void abrirCalendario (View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(AccionesCita.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        int aux = month +1;
                        String mes = "";
                        String dia = "";
                        if ((aux) < 10){
                            mes = "0"+aux;
                        }else{
                            mes = aux+"";
                        }
                        if(dayofMonth < 10){
                            dia = "0" +dayofMonth;
                        }else {
                            dia = dayofMonth + "";
                        }
                        String fecha=dia + "-" + (mes) + "-" + year;
                        tv.setText(fecha);
                    }
                }, 2020, mes, dia); dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dpd.show();
    }
    public void abrirHora(View view) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog tmd = new TimePickerDialog(AccionesCita.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minutos ="";
                if (minute == 0){
                    minutos = "00";
                }else{
                    minutos = minute +"";
                }
                tv2.setText(hourOfDay + ":" + minutos);
            }
        }, hora, min, false);
        tmd.show();
    }
    private void buscarCita(String id){
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DIA, HORA, NOMBRE, TELEFONO, COBRO, METODO_P, COMENTARIOS FROM agendar WHERE ID = " + id, null);
        try {
            cursor.moveToFirst();
            tv.setText(ProyectoFinalBD.formatoFechaUI(cursor.getString(0)));
            tv2.setText(cursor.getString(1));
            nombre.setText(cursor.getString(2));
            telefono.setText(cursor.getString(3));
            cobro.setText(cursor.getString(4));
            int opcion = 0;
            if (cursor.getString(5).equals("Tarjeta")){
                opcion = 0;
            }else{
                opcion = 1;
            }
            metodo_p.setSelection(opcion);
            comentarios.setText(cursor.getString(6));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(this, "No se ha encontrado la informacion", Toast.LENGTH_SHORT).show();
            finish();
        }
        db.close();
    }

    private void eliminar(String id){
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {id};
        db.delete("agendar","ID=?", parametros);
        Toast.makeText(getApplicationContext(), "Se eimino la cita", Toast.LENGTH_SHORT).show();
        db.close();
        Intent i = new Intent();
        setResult(RESULT_OK);
        finish();
    }

    private void actualizar(String id){
        String dia = tv.getText().toString();
        String hora = tv2.getText().toString();
        String nombreC = nombre.getText().toString();
        String tel = telefono.getText().toString();
        String cob = cobro.getText().toString();
        String pago = metodo_p.getSelectedItem().toString();
        String coment = comentarios.getText().toString();
        if (nombreC.isEmpty() || tel.isEmpty() || cob.isEmpty() || pago.isEmpty()){
            Toast.makeText(getApplicationContext(), "Los campos son necesarios", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase db = conn.getReadableDatabase();
            String[] parametros = {id};
            ContentValues cv = new ContentValues();
            cv.put("DIA",ProyectoFinalBD.formatoFecha(dia));
            cv.put("HORA", hora);
            cv.put("NOMBRE", nombreC);
            cv.put("TELEFONO", tel);
            cv.put("COBRO", cob);
            cv.put("METODO_P", pago);
            cv.put("COMENTARIOS", coment);

            db.update("agendar", cv, "ID=?", parametros);
            Toast.makeText(getApplicationContext(), "Se actualizo el registro", Toast.LENGTH_SHORT).show();
            db.close();
            Intent i = new Intent();
            setResult(RESULT_OK);
            finish();

        }
    }
    public void atender(String id) {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {id};

        ContentValues cv = new ContentValues();
        cv.put("STATUS", 1);

        db.update("agendar", cv, "ID=?", parametros);
        Toast.makeText(getApplicationContext(), "Se realizo el cobro", Toast.LENGTH_SHORT).show();
        db.close();
        Intent i = new Intent();
        setResult(RESULT_OK);
        finish();
    }


}