package Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Person;
import Utils.Utils;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(  Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PEOPLE_TABLE ="CREATE TABLE " + Utils.TABLE_NAME + " (" +
                Utils.KEY_ID + " INTEGER PRIMARY KEY,"
                + Utils.KEY_NAME + " TEXT,"
                + Utils.KEY_LNAME + " TEXT,"
                + Utils.KEY_ADDRESS + " TEXT," +
                Utils.KEY_AGE + " TEXT)";

        db.execSQL(CREATE_PEOPLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_NAME);
        onCreate(db);
    }



    public void addPerson(Person person){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME , person.getName());
        contentValues.put(Utils.KEY_LNAME , person.getLname());
        contentValues.put(Utils.KEY_ADDRESS , person.getAddress());
        contentValues.put(Utils.KEY_AGE , String.valueOf(person.getAge()) );

        database.insert(Utils.TABLE_NAME , null,contentValues);
        database.close();
    }


public  Person getPerson(int id){
    SQLiteDatabase database = this.getReadableDatabase() ;
    Cursor cursor = database.query(Utils.TABLE_NAME ,
            new String[]{Utils.KEY_ID,Utils.KEY_NAME,
                    Utils.KEY_LNAME,
                    Utils.KEY_ADDRESS,Utils.KEY_AGE},
            Utils.KEY_ID + "=?",new String[]{String.valueOf(id)},
            null,null,null,null);

    if (cursor != null)
        cursor.moveToFirst();
        Person person = new Person(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2)
                ,cursor.getString(3)
                ,Integer.parseInt(cursor.getString(4)) );
        return person;

}


    public List<Person> getPeople(){
        SQLiteDatabase database = this.getReadableDatabase() ;
        List<Person> personList = new ArrayList<>();

        String getAll = "SELECT * FROM "+ Utils.TABLE_NAME;

        Cursor cursor = database.rawQuery(getAll ,null );

        if (cursor.moveToFirst())

            do{
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setName(cursor.getString(1));
                person.setLname(cursor.getString(2));
                person.setAddress(cursor.getString(3));
                person.setAge(Integer.parseInt(cursor.getString(4)));

                personList.add(person);

            }while (cursor.moveToNext());

       return  personList;

    }





    public  int updatePerson(Person person){
        SQLiteDatabase database = this.getWritableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.KEY_NAME , person.getName());
        contentValues.put(Utils.KEY_LNAME , person.getLname());
        contentValues.put(Utils.KEY_ADDRESS , person.getAddress());
        contentValues.put(Utils.KEY_AGE , String.valueOf(person.getAge()) );

        int result = database.update(Utils.TABLE_NAME , contentValues,Utils.KEY_ID + "=?",
                new String[]{ String.valueOf(person.getId())});
        database.close();
        return result;

    }



    public  void deletePerson(Person person){
        SQLiteDatabase database = this.getWritableDatabase() ;
        database.delete(Utils.TABLE_NAME , Utils.KEY_ID + "=?",
                new String[]{ String.valueOf(person.getId())});
        database.close();

    }



    public  int getNumPerson(){
        String getAll = "SELECT * FROM "+ Utils.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase() ;
        Cursor cursor = database.rawQuery(getAll ,null );

       // cursor.close();
        return cursor.getCount();

    }



}
