package org.codeforiraq.radiobutton;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        textView = findViewById(R.id.textView);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,@IdRes int checkedId) {
                radioButton = findViewById( checkedId);
                switch(radioButton.getId()){
                    case R.id.pizzaId:{
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Pizza");
                     //   Log.d("logid","Pizza");
                    }
                    break;
                    case R.id.hamburgerId:{
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Hamburger");
                    //    Log.d("logid","Hamburger");
                    }
                    break;
                    case R.id.steak:{
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Steak");
                     //   Log.d("logid","Steak");
                    }
                }

            }
        });

    }
}
