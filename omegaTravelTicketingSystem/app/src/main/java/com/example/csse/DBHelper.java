package com.example.mad;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Booking database
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "bus_booking.db";
    public static final String TABLE_NAME = "bookingBus";
    public static final String COL_1 = "busType";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Phone_no";
    public static final String COL_4 = "Email";
    public static final String COL_5 = "Check_in";
    public static final String COL_6 = "CHeck_out";
    public static final String COL_7 = "NoOfSeats";
    public static final String COL_8 = "Total_cost";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(busType text,Name text, Phone_no text primary key, Email text,Check_in text, Check_out text, NoOfSeats integer, Total_cost integer)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String car_type, String Name, String Phone_no, String Email, String Check_in, String Check_out, int No_of_cars, int Total_cost) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, car_type);
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Phone_no);
        contentValues.put(COL_4, Email);
        contentValues.put(COL_5, Check_in);
        contentValues.put(COL_6, Check_out);
        contentValues.put(COL_7, No_of_cars);
        contentValues.put(COL_8, Total_cost);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }
    //Get booking details
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    //Update booking details
    public  boolean updateDetail(String busType, String Name, String Phone_no, String Email, String Check_in, String Check_out, String NoOfSeats, String Total_cost){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1, busType);
        contentValues.put(COL_2, Name);
        contentValues.put(COL_3, Phone_no);
        contentValues.put(COL_4, Email);
        contentValues.put(COL_5, Check_in);
        contentValues.put(COL_6, Check_out);
        contentValues.put(COL_7, NoOfSeats);
        contentValues.put(COL_8, Total_cost);

        sqLiteDatabase.update(TABLE_NAME,contentValues,"Phone_no = ?",new String[] {Phone_no});
        return true;
    }

    //delete booking details
    public Integer deleteDetail (String Phone_no){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"Phone_no = ?",new String[] {Phone_no});
    }


}

