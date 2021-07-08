package com.example.icm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CitasAnteriores extends AppCompatActivity {

    Button regresar, buscar,fecha1, fecha2;
    TextView primerDia, segundoDia;
    ProyectoFinalBD conn;
    ArrayList<String> info;
    ArrayList<Cita> citas;
    ListView tabla;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_anteriores);

        regresar = (Button) findViewById(R.id.regresar);
        buscar = (Button) findViewById(R.id.buscarAnt);
        fecha1 = (Button) findViewById(R.id.escoger1);
        fecha2 = (Button) findViewById(R.id.escoger2);
        primerDia = (TextView) findViewById(R.id.dia1);
        segundoDia = (TextView) findViewById(R.id.dia2);
        tabla = (ListView) findViewById(R.id.listacitas);
        conn = new ProyectoFinalBD(getApplicationContext());
        info = new ArrayList<String>();
        info.add("");
        adapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1, info);
        tabla.setAdapter(adapter);
        tabla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetallesCita.class);
                intent.putExtra("ID", citas.get(i).getId()+"");
                startActivityForResult(intent, 21);
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CitasAnteriores.this, Menu.class);
                startActivity(intent);
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uno = primerDia.getText().toString();
                String dos = segundoDia.getText().toString();

                if (!uno.isEmpty() && dos.isEmpty()){
                    String fecha1 = ProyectoFinalBD.formatoFecha(uno);
                    String query = "SELECT * FROM agendar WHERE DIA = '" + fecha1 + "' AND STATUS = '1'";
                    listaCitas(query);
                }else if(!uno.isEmpty() && !dos.isEmpty()){
                    String fecha1 = ProyectoFinalBD.formatoFecha(uno);
                    String fecha2 = ProyectoFinalBD.formatoFecha(dos);
                    String query = "SELECT * FROM agendar WHERE DIA BETWEEN '" +fecha1 +"' AND '"+fecha2+"' AND STATUS = '1'";
                    listaCitas(query);
                }else{
                    Toast.makeText(getApplicationContext(), "Debe seleccionar ambas fechas o solo la primera", Toast.LENGTH_SHORT).show();
                }



            }
        });

        fecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(CitasAnteriores.this, R.style.DialogTheme,
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
                                primerDia.setText(fecha);
                            }
                        }, 2020, mes, dia); dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dpd.show();
            }
        });
        fecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(CitasAnteriores.this, R.style.DialogTheme,
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
                                segundoDia.setText(fecha);
                            }
                        }, 2020, mes, dia); dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dpd.show();
            }
        });
    }

    public void listaCitas(String query){
        info.clear();
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = null;
        citas = new ArrayList<Cita>();
        cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            Cita cita = new Cita();
            cita.setId(cursor.getInt(0));
            cita.setDia(ProyectoFinalBD.formatoFechaUI(cursor.getString(1)));
            cita.setHora(cursor.getString(2));
            cita.setNombre(cursor.getString(3));
            cita.setTelefono(cursor.getInt(4));
            cita.setCobro(cursor.getString(5));
            cita.setMetodo_p(cursor.getString(6));
            cita.setComentarios(cursor.getString(7));
            citas.add(cita);
        }

        cursor.close();
        db.close();
        adapter.clear();
        for (int i = 0; i < citas.size(); i++){
            adapter.add(citas.get(i).getId()+" - " + "\t\t" + citas.get(i).getNombre() + "\t\t" + citas.get(i).getDia() + "\t\t" +citas.get(i).getHora() + "\t\t$" +citas.get(i).getCobro());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }
}