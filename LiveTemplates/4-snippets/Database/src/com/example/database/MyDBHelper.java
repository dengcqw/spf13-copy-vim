package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "rinc";

    private static final int MYDATABASE_VERSION = 1;

    private static final String TABLE_NAME = "peoples";

    public static final String COL_NAME = "name";

    public static final String COL_AGE = "age";

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT, " + COL_AGE + " INTEGER);";

    public MyDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, MYDATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_SQL);
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, "Rinc");
        cv.put(COL_AGE, 24);
        db.insert(TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
        onCreate(db);
    }
}
