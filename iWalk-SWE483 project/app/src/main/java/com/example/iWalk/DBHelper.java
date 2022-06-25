package com.example.iWalk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "UserData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table users(name TEXT,password TEXT,email TEXT primary key)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists users");

    }

    public Boolean insertUserData(String name,String password,String email){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("email",email);

        long result=DB.insert("users",null,contentValues);
        if(result==-1){
            return false;

        }else {
            return true;
        }

    }


    public Cursor getPassword(String email) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from users where email=?", new String[]{email});
        return cursor;
    }

    public Cursor getDetails(String name) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from users where name=?", new String[]{name});
        return cursor;
    }

}

