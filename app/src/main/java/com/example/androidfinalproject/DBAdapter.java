package com.example.androidfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.PreparedStatement;

public class DBAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "POKE_DB"; // schema name
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "POKEDEX";

    private static final String ID = "Poke_Id"; //
    private static final String NAME = "Poke_Name";
    private static final String MOVES = "Num_Of_Moves";
    private static final String TYPE0 = "Poke_Type0";
    private static final String TYPE1 = "Poke_Type1";



    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    /*
        4 cols, One PK set to auto increment
        Aside from PK only name is set to NOT NULL, in case something messes up
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //JUST FOR DEV, REMOVE BEFORE SUBMISSION
        SQLiteStatement DropT = db.compileStatement("DROP TABLE IF EXISTS " + TABLE_NAME);
        DropT.execute();
//
//        String s  = String.format("CREATE TABLE IF NOT EXISTS %s (%s text, %s integer, %s text, %s text)", TABLE_NAME, NAME, MOVES, TYPE0, TYPE1);
//        db.execSQL(s);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +NAME +" VARCHAR(255) UNIQUE NOT NULL, " //added unique constraint
                +MOVES +" INTEGER, "
                +TYPE0 +" VARCHAR(60), "
                +TYPE1 + " VARCHAR(60));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // used just for inserting values to db
    // Accepts a ContentValues object
    public boolean insertToTable(ContentValues values){
        SQLiteDatabase db = getWritableDatabase();

        long row = db.insert(TABLE_NAME, null, values);
        if(row != -1){
            return true;
        }
        return false;
    }

    public void saveToDB(Pokemon p){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO " + TABLE_NAME + "(" + NAME + ", " + MOVES + ", " + TYPE0 + ", " + TYPE1 + ") " + "VALUES (?,?,?,?);");
        stmt.bindString(1,p.name);
        stmt.bindLong(2,p.moves.size());
        stmt.bindString(3,p.types.get(0));
        if(p.types.size()>1){
        stmt.bindString(4,p.types.get(1));}
        else{
            stmt.bindString(4,"null");
        }
        stmt.execute();
        db.close();
    }

//    public void onPokedex(Pokemon p){
//        SQLiteDatabase db = getWritableDatabase();
//        SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET " + BOOL + " = ? WHERE " + NAME + " = ?");
//        stmt.bindLong(1,p.onPokedex ? 1:0);
//        stmt.bindString(2,p.name);
//        stmt.execute();
//        db.close();
//    }

    // will just return a cursor
    public Cursor readTable(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if(cursor.getCount() > 0){
            return cursor;
        };
        return null;
    }

    // I am not sure if whether we need to update or delete from DB
}
