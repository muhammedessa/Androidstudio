package org.codeforiraq.myactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView textView ;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        textView = findViewById(R.id.textViewfromfirst);
        button = findViewById(R.id.buttonback);

        String firstname = extras.getString("firstname");
        String lastname = extras.getString("lastname");
        String welcomemessage = extras.getString("welcomemessage");
        int age = extras.getInt("age");
        double salary = extras.getDouble("salary");

        textView.setText(firstname + "\n" +  lastname
                +"\n" + welcomemessage + "\n" +age +"\n"
                +  "\n" +salary);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent  = getIntent();
                backIntent.putExtra("welcomemessage","Data back from second activity");
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
