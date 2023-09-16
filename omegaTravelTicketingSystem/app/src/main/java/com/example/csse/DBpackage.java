package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



//Package database
public class DBpackage extends SQLiteOpenHelper {

    //properties of package
    public static final String DATABASE_NAME = "pak.db";
    public static final String TABLE_NAME = "package";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PackageType";
    public static final String COL_3 = "PakageCost";
    public static final String COL_4 = "PackageFeatures";


    public DBpackage(Context context) {

        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    }

    //create table for package

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER primary key ,PackageType text,PakageCost interger,PackageFeatures text)");


    }

    //table drop
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //insert package data
    public boolean insertData(String ID, String PackageType, String PakageCost, String PackageFeatures) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, PackageType);
        contentValues.put(COL_3,PakageCost );
        contentValues.put(COL_4, PackageFeatures);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //get package data
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    //update package data
    public  boolean updateData(String ID, String PackageType, String PakageCost, String PackageFeatures){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2, PackageType);
        contentValues.put(COL_3, PakageCost);
        contentValues.put(COL_4, PackageFeatures);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"ID = ?",new String[] {ID});
        return true;
    }

    //package data delete
    public Integer deleteData (String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"ID = ?",new String[]{ID});
    }

    //search package data
    public Cursor searchData(String ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "= '" + ID + "'",null);
        return data;
    };
}
