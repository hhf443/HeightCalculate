package com.example.heightcalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HcDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TABLE =
            "create table heightinfo("+"id integer primary key autoincrement,"
                    + "name TEXT,"
                    + "ismale INTEGER,"
                    + "fheight TEXT,"
                    + "mheight TEXT,"
                    + "sheight TEXT)";

    private Context mycontext;

    public HcDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mycontext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.v("HcDatabaseHelper","建表成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists heightinfo");
        onCreate(sqLiteDatabase);
    }
}
