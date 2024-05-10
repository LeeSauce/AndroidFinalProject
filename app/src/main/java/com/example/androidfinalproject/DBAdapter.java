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
    private static final String NAME = "Poke_Name";
    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + NAME + " VARCHAR(255) UNIQUE NOT NULL); ");
        // making the column unique will prevent multiple values to be added
        // it's important to make NAME unique since it's a surrogate key
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean saveToDB (Pokemon p){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(NAME, p.name);

        long row = db.insert(TABLE_NAME, null, value);
        if(row != -1){
            return true;
        }
        return false;
    }
    //Want to return a List of the names so we can query the API for the pokemon for pokedex
    public List<String> readTable() {
        List<String> names = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        int nameIndex = cursor.getColumnIndex(NAME);// <- needed an index

        if (cursor.getCount() == 0) {
            System.out.println("The database is empty");
        } else {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                String name = cursor.getString(nameIndex);
                names.add(name);
                cursor.moveToNext(); // this needs to be added or we will run into a never ending loop
            }
        }
        if(names.isEmpty()){//for debugging
            return null;
        }
        return names;
    }
        public void delete (Pokemon p){
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_NAME, NAME+"= ?", new String[]{p.name});
            db.close();
        }

    }

