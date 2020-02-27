package org.codeforiraq.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);

        myButton = findViewById(R.id.mybotton);
        myTextView = findViewById(R.id.myTextView);

        myButton.setTextColor(Color.YELLOW);
        myButton.setBackgroundColor(Color.BLUE);
        myButton.setText(R.string.my_name);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  myTextView.setTextColor(Color.YELLOW);
                  myTextView.setBackgroundColor(Color.BLUE);
                   myTextView.setText(R.string.my_text);
            }
        });

    }


//    public void clickMyButton(View view ){
//        myTextView.setTextColor(Color.YELLOW);
//        myTextView.setBackgroundColor(Color.BLUE);
//        myTextView.setText(R.string.my_text);
//    }



}
