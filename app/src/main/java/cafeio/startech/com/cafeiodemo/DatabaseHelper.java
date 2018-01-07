package cafeio.startech.com.cafeiodemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.HideReturnsTransformationMethod;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mert on 5.01.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "subtegral.db";
    public static final String TABLE_NAME = "Transactions";
    public static final String COL1_NAME = "ID";
    public static final String COL2_NAME = "BILL_PLACE";
    public static final String COL3_NAME = "BILL_PRICE";
    public static final String COL4_NAME = "BILL_DATE";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,BILL_PLACE TEXT,BILL_PRICE REAL,BILL_DATE INTEGER" +
                ",BILL_STATE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
    public boolean insertData(String place,String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_NAME,place);
        contentValues.put(COL3_NAME,price);
        Date date = new Date();
        long time = date.getTime();
        contentValues.put(COL4_NAME,time);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        return true;
    }
    public Cursor returnData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY BILL_DATE DESC",null);
    }
    public Cursor returnFromDate(int year,int month){
        SQLiteDatabase db = this.getWritableDatabase();
        Date date = new Date(year,month,1);
        Date endDate = new Date(year,month+1,1);
        return db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE BILL_DATE>="+date.getTime()+" AND BILL_DATE<"+endDate.getTime() + " ORDER BY BILL_DATE DESC",null);
    }
    public Integer removeData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?",new String[]{id});
    }
}
