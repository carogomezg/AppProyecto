package com.example.icm_proyectofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProyectoFinalBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD="agendarcita.bd";
    private static final int VERSION_BD=1;
    private static final String TABLA_AGENDAR="CREATE TABLE AGENDAR (ID INTEGER PRIMARY KEY NOT NULL, DIA TEXT, HORA TEXT, NOMBRE TEXT, TELEFONO INTEGER, COBRO TEXT, METODO_P TEXT, COMENTARIOS TEXT, STATUS INTEGER)";

    public ProyectoFinalBD(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_AGENDAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TABLA_AGENDAR");
        sqLiteDatabase.execSQL(TABLA_AGENDAR);
    }

    public void agregarCitas(String id, String dia, String hora, String nombre, String telefono, String cobro, String metodo_p, String comentarios){
        SQLiteDatabase bd = getWritableDatabase();
        int status = 0;
        if(bd!=null){
            bd.execSQL("INSERT INTO AGENDAR VALUES ('"+id+"','"+dia+"','"+hora+"','"+nombre+"','"+telefono+"','"+cobro+"','"+metodo_p+"','"+comentarios+"','"+ status+"')");
            bd.close();
        }
    }
    public static String formatoFecha(String fecha){
        String[] aux = fecha.split("-");
        return aux[2] + "-" + aux[1] + "-" + aux[0];
    }

    public static String formatoFechaUI(String fecha){
        String[] aux = fecha.split("-");
        return aux[2] + "-" + aux[1] + "-" + aux[0];
    }

}
