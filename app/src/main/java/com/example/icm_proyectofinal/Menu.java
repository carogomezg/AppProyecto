package com.example.icm_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button agendar;
    Button citas_p;
    Button citas_a;

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        agendar = (Button) findViewById(R.id.agendar);
        citas_p = (Button) findViewById(R.id.citas_pendi);
        citas_a = (Button) findViewById(R.id.citas_ant);

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Agendar.class);
                startActivityForResult(intent,2);
            }
        });

        citas_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, CitasPendi.class);
                startActivityForResult(intent,3);
            }
        });

        citas_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, CitasAnteriores.class);
                startActivityForResult(intent,4);
            }
        });

    }
}