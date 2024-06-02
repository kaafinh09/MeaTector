package com.example.finalProject_meatector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "db_history";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tbl_history";

    private static final String FIELD_ID = "id" ;
    private static final String FIELD_IMAGE = "image" ;
    private static final String FIELD_PREDICT = "predict";
    private static final String FIELD_DATE = "date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                        FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FIELD_IMAGE +
                        " TEXT, " + FIELD_PREDICT + " TEXT, " + FIELD_DATE + " TEXT ); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData (String image, String predict, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_IMAGE, image);
        cv.put(FIELD_PREDICT, predict );
        cv.put(FIELD_DATE, date);

        long execution = db.insert(TABLE_NAME, null, cv);
        return execution;

    }

    public Cursor readData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db!=null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;


    }

}
