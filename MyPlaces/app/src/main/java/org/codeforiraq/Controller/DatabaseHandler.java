package org.codeforiraq.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.codeforiraq.Model.Place;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(@Nullable Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACES_TABLE = "CREATE TABLE " + Utils.TABLE_NAME + " (" +
                Utils.KEY_ID + " INTEGER PRIMARY KEY," +
                Utils.LATITUDE + " TEXT," +
                Utils.LONGITUDE + " TEXT," +
                Utils.TITLE + " TEXT)" ;

        db.execSQL(CREATE_PLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
        onCreate(db);
    }

    public void addPlace(Place place){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.LATITUDE , place.getPlatitude());
        contentValues.put(Utils.LONGITUDE , place.getPlongitude());
        contentValues.put(Utils.TITLE , place.getTitle());
        database.insert(Utils.TABLE_NAME,null,contentValues);
        database.close();
    }


    public int editPlace(Place place){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.LATITUDE , place.getPlatitude());
        contentValues.put(Utils.LONGITUDE , place.getPlongitude());
        contentValues.put(Utils.TITLE , place.getTitle());

        int result =  database.update(Utils.TABLE_NAME, contentValues,
                Utils.KEY_ID+ "=?",
           new String[]{String.valueOf(place.getId())});
        database.close();
        return result;
    }

    public List<Place> getAllPlaces(){
        SQLiteDatabase database = this.getReadableDatabase();
        List<Place> placeList = new ArrayList<>();
        String getAll = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = database.rawQuery(getAll,null);
        if(cursor.moveToFirst()){
            do{
                Place place = new Place();
                place.setId(cursor.getInt(0));
                place.setPlatitude(cursor.getString(1));
                place.setPlongitude(cursor.getString(2));
                place.setTitle(cursor.getString(3));

                placeList.add(place);
            }while (cursor.moveToNext());
        }
        return  placeList;
    }

    public  int getPlacesNum(){
        SQLiteDatabase database = this.getReadableDatabase();
        String getAll = "SELECT * FROM " + Utils.TABLE_NAME;
        Cursor cursor = database.rawQuery(getAll,null);
        return cursor.getCount();
    }

    public void deletePlace(Place place){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Utils.TABLE_NAME,Utils.KEY_ID+ "=?",
                new String[]{String.valueOf(place.getId())});
        database.close();
    }





//    public void deletePlace(Place place){
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.delete(Utils.TABLE_NAME,Utils.TITLE+ "=?",
//                new String[]{String.valueOf(place.getTitle())});
//        database.close();
//    }
}
