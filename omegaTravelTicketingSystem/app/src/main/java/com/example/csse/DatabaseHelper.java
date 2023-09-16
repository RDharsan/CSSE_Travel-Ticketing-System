package com.example.mad;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Database helper
public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper( Context context) {
        super(context, "Login.db", null, 1);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table user(email text primary key,password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");

        onCreate(sqLiteDatabase);

    }
    //Insert data
    public boolean insert(String email,String password){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long ins=sqLiteDatabase.insert("user",null,contentValues);
        if(ins==-1)
            return false;
        else
            return true;


    }

    //Check mail
    public Boolean chkmail (String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    //Email password
    public boolean emailpassword(String email,String password){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from user where email=? and password=? ", new String[]{email,password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


}

