package com.example.icm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class Agendar extends AppCompatActivity {
    TextView tv;
    TextView tv2;
    Button regresar;
    Button listo;
    EditText id;
    EditText nombre;
    EditText telefono;
    EditText cobro;
    Spinner metodo_p;
    EditText comentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar);
        tv = findViewById(R.id.textview_dia);
        tv2 = findViewById(R.id.textview_hora);
        regresar = findViewById(R.id.regresar);
        listo = findViewById(R.id.listo);
        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre_cliente);
        telefono = findViewById(R.id.telefono);
        cobro = findViewById(R.id.cobro);
        metodo_p = findViewById(R.id.metodo_p);
        comentarios = findViewById(R.id.comentarios);

        final ProyectoFinalBD conn = new ProyectoFinalBD(getApplicationContext());

        listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tv.getText().toString().isEmpty() || tv2.getText().toString().isEmpty() || id.getText().toString().isEmpty() || nombre.getText().toString().isEmpty() || telefono.getText().toString().isEmpty() ||
                        cobro.getText().toString().isEmpty() || metodo_p.isSelected() || comentarios.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();


                } else{

                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] args = {id.getText().toString()};
                    Cursor cursor = db.rawQuery("SELECT * FROM agendar WHERE ID = ?", args);
                    if (cursor.getCount() > 0){
                        Toast.makeText(getApplicationContext(), "Ya existe una cita con esa referencia", Toast.LENGTH_SHORT).show();
                        cursor.close();
                        db.close();
                    }else{
                        conn.agregarCitas(id.getText().toString(),ProyectoFinalBD.formatoFecha(tv.getText().toString()),tv2.getText().toString(),nombre.getText().toString(),telefono.getText().toString(),cobro.getText().toString(),metodo_p.getSelectedItem().toString(),comentarios.getText().toString());
                        Toast.makeText(getApplicationContext(), "SE AGENDO LA CITA", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        setResult(RESULT_OK);
                        finish();
                    }

                }

            }
        });



        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Agendar.this, Menu.class);
                startActivity(intent);
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

        DatePickerDialog dpd = new DatePickerDialog(Agendar.this, R.style.DialogTheme,
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

    //para abrir la hora de forma gráfica en la aplicación
    public void abrirHora(View view) {
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog tmd = new TimePickerDialog(Agendar.this, new TimePickerDialog.OnTimeSetListener() {
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



}