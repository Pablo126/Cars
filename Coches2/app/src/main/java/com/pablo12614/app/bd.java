package com.pablo12614.app;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.Context;
import android.database.Cursor;
import android.text.method.DateTimeKeyListener;

import java.util.Date;

/**
 * Created by Pablo on 7/03/14.

 */
public class bd extends SQLiteOpenHelper {
    private static String name = "bd";
    private static int version = 1;
    protected static String TableListas = "listas";
    //protected static String TableLibros = "libros";

    private String SQLCreateListas = "CREATE TABLE " + TableListas +  " (id_usuario INT, nombre VARCHAR(15), fechahora DATETIME) ";
    //private String SQLCreateLibros = "CREATE TABLE " + TableLibros +  " (id_libro INT, nombre VARCHAR(1000), editorial VARCHAR(1000) )";


    private static CursorFactory cursorFactory = null;

    public bd(Context context){
        super(context,name,cursorFactory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLCreateListas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void insertarUsuario(int idUsuario, String nombre){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO " + TableListas +
                    " (id_usuario, nombre, fechahora) " +
                    " VALUES(" + 1 + ", '" + nombre + "','datetime()') ");
            db.close();
        }
    }

    public Cursor leerLista(){
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery("SELECT id_usuario AS _id, nombre,fechahora "+
                " FROM "+TableListas +
                " WHERE id_usuario = 1 order by fechahora asc", null);
    }

    public void borrarNombre(String nombre){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("DELETE FROM "+ TableListas +" WHERE nombre = '"+nombre+"' ");
            db.close();

        }
    }

    public Cursor updateFechaActual(String nombre){
        SQLiteDatabase db = getReadableDatabase();
        //Date fechahora = new Date();
        return db.rawQuery("Update "+TableListas + " SET fechahora='datetime()' WHERE nombre = '"+nombre+"'", null);
    }
}
