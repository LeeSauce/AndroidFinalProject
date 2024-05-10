package com.example.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "POKE_DB"; // schema name
    private static final int DB_VERSION = 4;

    private static final String TABLE_NAME = "POKEDEX";

    private static final String ID = "Poke_Id"; //
    private static final String NAME = "Poke_Name";


    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    //Only one column because we only need the names
    @Override
    public void onCreate(SQLiteDatabase db) {
        //JUST FOR DEV, REMOVE BEFORE SUBMISSION
        SQLiteStatement DropT = db.compileStatement("DROP TABLE IF EXISTS " + TABLE_NAME);
        DropT.execute();

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + NAME + " VARCHAR(255)); ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void saveToDB(Pokemon p) {
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO " + TABLE_NAME + "(" + NAME + ") " + "VALUES (?);");
        stmt.bindString(1, p.name);
        stmt.execute();
        db.close();
    }

    //Want to return a List of the names so we can query the API for the pokemon for pokedex
    public List<String> readTable() {
        List<String> names = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            System.out.println("The database is empty");
        } else {
            for (int i = 0; i < cursor.getCount(); i++) {
                String name = cursor.getString(i);
                names.add(name);
            }
        }
        return names;
    }

        public void delete (Pokemon p){
            SQLiteDatabase db = getReadableDatabase();
            SQLiteStatement delete = db.compileStatement("DELETE FROM " + TABLE_NAME + " WHERE " + NAME + " = ?");
            delete.bindString(1, p.name);
            delete.execute();
            db.close();
        }

    }

