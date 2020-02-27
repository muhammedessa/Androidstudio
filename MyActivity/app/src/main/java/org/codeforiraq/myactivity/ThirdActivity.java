package org.codeforiraq.myactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Bundle extras = getIntent().getExtras();
        textView = findViewById(R.id.textView4);


        String firstname = extras.getString("firstname");
        String lastname = extras.getString("lastname");
        String welcomemessage = extras.getString("welcomemessage");
        int age = extras.getInt("age");
        double salary = extras.getDouble("salary");

        textView.setText(firstname + "\n" +  lastname
                +"\n" + welcomemessage + "\n" +age +"\n"
                +  "\n" +salary);



    }
}
