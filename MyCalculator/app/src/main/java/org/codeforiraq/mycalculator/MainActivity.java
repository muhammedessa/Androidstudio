package org.codeforiraq.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView myresult ;
    EditText number1 , number2 ;
    Button mysum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          myresult = findViewById(R.id.myresult);
          number1  = findViewById(R.id.number1);
          number2     = findViewById(R.id.number2);
          mysum = findViewById(R.id.buttonsum);

    }

    public void clickSum(View view){


    if (  !number1.getText().toString().equals("")  && !number2.getText().toString().equals("")   ){

            double myNumber1 = Double.parseDouble(number1.getText().toString());
            double myNumber2 = Double.parseDouble(number2.getText().toString());
            myresult.setText( "Result : " +  (myNumber1 + myNumber2)   );
        }else{
            myresult.setText( "Please put numbers" );
        }


//        if ( !TextUtils.isEmpty(number1.getText().toString()) &&
//                !TextUtils.isEmpty(number2.getText().toString()) ){
//
//            double myNumber1 = Double.parseDouble(number1.getText().toString());
//            double myNumber2 = Double.parseDouble(number2.getText().toString());
//            myresult.setText( "Result : " +  (myNumber1 + myNumber2)   );
//        }else{
//            myresult.setText( "Please put numbers" );
//        }

//        if ( TextUtils.isDigitsOnly(number1.getText().toString()) &&
//            TextUtils.isDigitsOnly(number2.getText().toString()) ){
//
//            double myNumber1 = Double.parseDouble(number1.getText().toString());
//            double myNumber2 = Double.parseDouble(number2.getText().toString());
//            myresult.setText( "Result : " +  (myNumber1 + myNumber2)   );
//        }else{
//            myresult.setText( "Please put numbers" );
//        }



    }
}
