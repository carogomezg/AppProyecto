package com.example.icm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetallesCita extends AppCompatActivity {
    TextView dia, hora, cliente, telefono, cobro, pago, comentarios;
    Button regreso;
    ProyectoFinalBD conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conn = new ProyectoFinalBD(getApplicationContext());
        setContentView(R.layout.activity_detalles_cita);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("ID");
        dia = (TextView) findViewById(R.id.detFecha);
        hora = (TextView) findViewById(R.id.detHora);
        cliente = (TextView) findViewById(R.id.detNom);
        telefono = (TextView) findViewById(R.id.detTel);
        cobro = (TextView) findViewById(R.id.detCob);
        pago = (TextView) findViewById(R.id.detPag);
        comentarios = (TextView) findViewById(R.id.detCom);
        regreso = (Button) findViewById(R.id.detRegr);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DIA, HORA, NOMBRE, TELEFONO, COBRO, METODO_P, COMENTARIOS FROM agendar WHERE ID = " + id, null);
        try {
            cursor.moveToFirst();
            dia.setText(ProyectoFinalBD.formatoFechaUI(cursor.getString(0)));
            hora.setText(cursor.getString(1));
            cliente.setText(cursor.getString(2));
            telefono.setText(cursor.getString(3));
            cobro.setText("$"+cursor.getString(4));
            pago.setText(cursor.getString(5));
            comentarios.setText(cursor.getString(6));
            cursor.close();

        }catch (Exception e){
            Toast.makeText(this, "No se ha encontrado la informacion", Toast.LENGTH_SHORT).show();
            finish();
        }
        db.close();
        regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}