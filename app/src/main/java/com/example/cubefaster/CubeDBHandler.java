package com.example.cubefaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class CubeDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CubeFasterStats.db";
    private static final String TABLE_CUBE_STATS = "cube_faster_stats";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "GAME_TYPE";
    private static final String COL_3 = "DATE";
    private static final String COL_4 = "TIME1";
    private static final String COL_5 = "TIME2";
    private static final String COL_6 = "TIME3";
    private static final String COL_7 = "TIME4";
    private static final String COL_8 = "TIME5";
    private static final String COL_9 = "TIME_AVG";

    public CubeDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_CUBE_STATS +
                "(ID INTEGER PRIMARY KEY, " +
                "GAME_TYPE TEXT, DATE TEXT, TIME1 TEXT, TIME2 TEXT, TIME3 TEXT, TIME4 TEXT, TIME5 TEXT, TIME_AVG TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUBE_STATS);
        onCreate(db);
    }

    public boolean addT3x3data(String gameType, Date date, String time1, String time2, String time3, String time4, String time5, String avgTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, gameType);
        values.put(COL_3, String.valueOf(date));
        values.put(COL_4, time1);
        values.put(COL_5, time2);
        values.put(COL_6, time3);
        values.put(COL_7, time4);
        values.put(COL_8, time5);
        values.put(COL_9, avgTime);

        long result = db.insert(TABLE_CUBE_STATS,null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean addS3x3data(String gameType, Date date, String time1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, gameType);
        values.put(COL_3, String.valueOf(date));
        values.put(COL_4, time1);

        long result = db.insert(TABLE_CUBE_STATS,null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getT3x3Data(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_CUBE_STATS + " WHERE GAME_TYPE = 'T3x3' ORDER BY TIME_AVG ASC",null);
        return data;
    }

    public Cursor getT3x3DataDel(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT ID, TIME_AVG FROM " + TABLE_CUBE_STATS + " WHERE GAME_TYPE = 'T3x3' ORDER BY TIME_AVG ASC",null);
        return data;
    }

    public Cursor getS3x3Data(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_CUBE_STATS + " WHERE GAME_TYPE = 'S3x3' ORDER BY TIME1 ASC",null);
        return data;
    }

    public Cursor getS3x3DataDel(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT ID, TIME1 FROM " + TABLE_CUBE_STATS + " WHERE GAME_TYPE = 'S3x3' ORDER BY TIME1 ASC",null);
        return data;
    }

    public boolean delT3x3Data(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CUBE_STATS,"ID = " + id, null);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean delS3x3Data(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_CUBE_STATS,"ID = " + id, null);

        if (result == -1)
            return false;
        else
            return true;
    }
}
