package com.example.icm_proyectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CitasPendi extends AppCompatActivity {

    private ListView table;
    ArrayList<String> listaInfo;
    ArrayList<Cita> listaCitas;
    ProyectoFinalBD conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_pendi);
        table = (ListView) findViewById(R.id.tabla);
        conn = new ProyectoFinalBD(getApplicationContext());
        consultarCitas();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfo);
        table.setAdapter(adapter);
        table.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AccionesCita.class);
                intent.putExtra("ID", listaCitas.get(i).getId()+"");
                startActivityForResult(intent, 21);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 21 && resultCode == RESULT_OK){
            Intent inte = new Intent(CitasPendi.this, CitasPendi.class);
            startActivity(inte);
            finish();
        }
    }

    private void consultarCitas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Cita cita = null;
        listaCitas = new ArrayList<Cita>();

        Cursor cursor = db.rawQuery("SELECT * FROM agendar WHERE STATUS = 0", null);
        while (cursor.moveToNext()){
            cita = new Cita();
            cita.setId(cursor.getInt(0));
            cita.setDia(ProyectoFinalBD.formatoFechaUI(cursor.getString(1)));
            cita.setHora(cursor.getString(2));
            cita.setNombre(cursor.getString(3));
            cita.setTelefono(cursor.getInt(4));
            cita.setCobro(cursor.getString(5));
            cita.setMetodo_p(cursor.getString(6));
            cita.setComentarios(cursor.getString(7));
            listaCitas.add(cita);
        }
        cursor.close();
        db.close();
        listaInfo =  new ArrayList<String>();
        for (int i=0; i<listaCitas.size(); i++){
            listaInfo.add(listaCitas.get(i).getId() + " - " + listaCitas.get(i).getNombre() + " \t" + listaCitas.get(i).getDia()+ " \t" + listaCitas.get(i).getHora() + " \t$"+listaCitas.get(i).getCobro());
        }

    }



}