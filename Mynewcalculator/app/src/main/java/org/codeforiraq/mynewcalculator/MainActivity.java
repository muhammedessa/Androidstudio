package org.codeforiraq.mynewcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText number1,number2;
    private Button buttonSum,buttonMulti ;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        buttonSum = findViewById(R.id.buttonSum);
        buttonMulti = findViewById(R.id.buttonMulti);
        textViewResult = findViewById(R.id.result);

        buttonSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mynumber1 = number1.getText().toString();
                String mynumber2 = number2.getText().toString();
                if(mynumber1.isEmpty()   ){
                    Toast.makeText(getApplicationContext(),"Please put value",Toast.LENGTH_LONG).show();
                }
                else if(mynumber2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please put value",Toast.LENGTH_LONG).show();
                }
                else{
                    Double input1 = Double.parseDouble(mynumber1);
                    Double input2 = Double.parseDouble(mynumber2);
                    sumValues(input1,input2);
                    String finalResult = String.valueOf( sumValues(input1,input2));
                    textViewResult.setText( "Result: "+ finalResult  );
                }
            }
        });

        buttonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mynumber1 = number1.getText().toString();
                String mynumber2 = number2.getText().toString();
                if(mynumber1.isEmpty()   ){
                    Toast.makeText(getApplicationContext(),"Please put value",Toast.LENGTH_LONG).show();
                }
                else if(mynumber2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please put value",Toast.LENGTH_LONG).show();
                }
                else{
                    Double input1 = Double.parseDouble(mynumber1);
                    Double input2 = Double.parseDouble(mynumber2);
                    multiValues(input1,input2);
                    String finalResult = String.valueOf( multiValues(input1,input2));
                    textViewResult.setText( "Result: "+ finalResult   );
                }
            }
        });
    }




    private Double sumValues(Double number1 , Double number2){

//      Double result =   number1 + number2;
//        Log.d("Sum",""+result);
        return number1 + number2;
    }

    private Double multiValues(Double number1 , Double number2){
//        Double result =   number1 * number2;
//        Log.d("Sum",""+result);
        return  number1 * number2;
    }



}
