package com.cigo7live.biljeske.baza_podataka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cigo7live.biljeske.modeli.podaci;

import java.util.ArrayList;

public class biljeskedb {

    DatabaseHelper DBHelper;

    public biljeskedb(Context context) {
        DBHelper = new DatabaseHelper(context);}

    static final int VERZIJA_BAZE = 10;

    // podaci o bazi
    static final String IME_BAZE = "biljeskedb";

    //Table : "list_root"
    static final String BAZA = "podatak";
    static final String id = "id";
    static final String naslov = "naslov";
    static final String biljeska = "biljeska";
    static final String TABLICA =
            "create table podatak " +
                    "(id integer primary key autoincrement," +
                    "naslov text not null," +
                    "biljeska text);";

    public void dodaj(podaci p) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues initialValues_data = new ContentValues();
        initialValues_data.put(naslov, p.getNaslov());
        initialValues_data.put(biljeska, p.getBiljeska());
        db.insert(BAZA, null, initialValues_data);
        db.close();
    }

    public void obrisi(String ID){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM podatak WHERE podatak.id ="+ID);
        db.close();
    }

    public ArrayList<podaci> getbiljeska(){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String query = "SELECT * FROM podatak";
        ArrayList<podaci> headers =new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                podaci l = new podaci();
                l.setId(cursor.getInt(cursor.getColumnIndex(id)));
                l.setNaslov(cursor.getString(cursor.getColumnIndex(naslov)));
                l.setBiljeska(cursor.getString(cursor.getColumnIndex(biljeska)));
                headers.add(l);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return headers;
    }

    public podaci getJedanPodatak(String ID){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String query = "SELECT podatak.id, podatak.naslov, podatak.biljeska FROM podatak WHERE podatak.id =" + ID ;
        podaci p = new podaci();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                p.setId(cursor.getInt(cursor.getColumnIndex(id)));
                p.setNaslov(cursor.getString(cursor.getColumnIndex(naslov)));
                p.setBiljeska(cursor.getString(cursor.getColumnIndex(biljeska)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return p;
    }

    public void update(podaci p){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        db.execSQL("UPDATE podatak SET naslov='" + p.getNaslov() + "', biljeska='" + p.getBiljeska() + "' WHERE id ="+p.getId());
        db.close();
    }


    //klasa za inicijaliziranje baze
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, IME_BAZE, null, VERZIJA_BAZE);
        }

        @Override
        public void onOpen(SQLiteDatabase db){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLICA);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS podatak");
            onCreate(db);
        }
    }
}
