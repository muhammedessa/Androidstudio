package org.codeforiraq.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Controller.DatabaseHandler;
import Model.Person;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         db = new DatabaseHandler(this);
//            db.addPerson(new Person("Muhammed","Essa","Kirkuk",36));
//            db.addPerson(new Person("Ahmed","Omer","Baghdad",22));
//            db.addPerson(new Person("Ali","khalid","Erbil",44));
//            db.addPerson(new Person("Osama","Saif","Basra",55));

            List<Person> personList = db.getPeople();

            for(Person p : personList){
            String myInfo = "ID: " + p.getId() + " Name: " + p.getName() +
                    " Last name : "+ p.getLname()
                    + " Address: "+p.getAddress()+" Age: "+p.getAge();
            Log.d("People data", myInfo);
        }



        Log.d("all data ", "--------------------------");


        Log.d("person numbers =  ", String.valueOf(db.getNumPerson()) );

      //  Person p3 = db.getPerson(4);
         //   db.deletePerson(p3);



//        Person p = db.getPerson(2);
//        String myInfo = "ID: " + p.getId() + " Name: " + p.getName() +
//                " Last name : "+ p.getLname()
//                + " Address: "+p.getAddress()+" Age: "+p.getAge();
//        Log.d("Person before update", myInfo );


//        Person p2 = db.getPerson(2);
//        p2.setName("Hussam");
//        p2.setAddress("Najaf");
//        p2.setAge(36);
//        p2.setLname("Hamed");
//        int updateInfo = db.updatePerson(p2);
//
//        String myInfo2 = "ID: " + p2.getId() + " Name: " + p2.getName() +
//                " Last name : "+ p2.getLname()
//                + " Address: "+p2.getAddress()+" Age: "+p2.getAge();
//        Log.d("Person after update", myInfo2 );

    }
}
