package org.codeforiraq.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.codeforiraq.Model.Data;
import org.codeforiraq.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context  ) {
        super(context, Utils.DATABASE_NAME,null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Utils.TABLE_NAME  +" ("
                    + Utils.COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Utils.COLOUMN_NAME + " TEXT,"
                    + Utils.COLOUMN_LNAME + " TEXT,"
                    + Utils.COLOUMN_DESCRIPTION + " TEXT,"
                    + Utils.COLOUMN_AGE + " TEXT,"
                    + Utils.COLOUMN_TIME_STAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    +  " )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ Utils.TABLE_NAME);
        onCreate(db);
    }

    public  long insertData(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Utils.COLOUMN_NAME , data.getName());
        cv.put(Utils.COLOUMN_LNAME , data.getLname());
        cv.put(Utils.COLOUMN_DESCRIPTION , data.getDescription());
        cv.put(Utils.COLOUMN_AGE , data.getAge());
        long id = db.insert(Utils.TABLE_NAME , null , cv);
        db.close();
        return  id;
    }

    public  int updateData(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Utils.COLOUMN_NAME , data.getName());
        cv.put(Utils.COLOUMN_LNAME , data.getLname());
        cv.put(Utils.COLOUMN_DESCRIPTION , data.getDescription());
        cv.put(Utils.COLOUMN_AGE , data.getAge());
        return db.update(Utils.TABLE_NAME , cv , "id" + " =?",
                new String[]{String.valueOf(data.getId())});
    }


    public  void deleteData(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
            db.delete(Utils.TABLE_NAME ,  Utils.COLOUMN_ID + " =?",
                new String[]{String.valueOf(data.getId())});
            db.close();
    }


    public  Data getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Utils.TABLE_NAME,
                new String[]{Utils.COLOUMN_ID,
                        Utils.COLOUMN_NAME,Utils.COLOUMN_LNAME
                        ,Utils.COLOUMN_DESCRIPTION,
                        Utils.COLOUMN_AGE,Utils.COLOUMN_TIME_STAMP},
                Utils.COLOUMN_ID + "=?", new String[]{String.valueOf(id)},
                null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();
   //     Data data = new Data(
//                cursor.getInt(cursor.getColumnIndex(Utils.COLOUMN_ID)),
//                cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_NAME)),
//                cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_LNAME)),
//                cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_DESCRIPTION)),
//                cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_AGE)),
//                cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_TIME_STAMP))  );


         Data data = new Data(
        Integer.parseInt(cursor.getString(0)),
        cursor.getString(1) ,
                cursor.getString(2) ,
                cursor.getString(3) ,
                cursor.getString(4)
        );

        return data;
    }




    public List<Data> getAllData() {
        List<Data> allData = new ArrayList<>();
        String query = "SELECT * FROM "+ Utils.TABLE_NAME +
                " ORDER BY " + Utils.COLOUMN_TIME_STAMP + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query ,null);
        if(cursor.moveToFirst())
          do{
              Data data = new Data();
              data.setId(cursor.getInt(cursor.getColumnIndex(Utils.COLOUMN_ID)));
              data.setName( cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_NAME)));
              data.setLname( cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_LNAME)));
              data.setAge(cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_AGE)));
              data.setDescription( cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_DESCRIPTION)));
              data.setTimeStamp(cursor.getString(cursor.getColumnIndex(Utils.COLOUMN_TIME_STAMP)));

              allData.add(data);

          }while (cursor.moveToNext());
        db.close();
        return allData;
    }


    public  int getDataCount(){
        String query = "SELECT * FROM "+ Utils.TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( query ,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


}
