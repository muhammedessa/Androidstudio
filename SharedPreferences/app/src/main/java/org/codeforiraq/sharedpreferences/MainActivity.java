package org.codeforiraq.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private EditText editText;
    private TextView textView;
    private Button button;
    private SharedPreferences sharedPreferences;
    private  static final String MYKEY = "secret";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textview);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(MYKEY,0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("msg",editText.getText().toString());
                editor.commit();
            }
        });

        SharedPreferences preferences = getSharedPreferences(MYKEY,0);

        if(preferences.contains("msg")){
            String mymsg = preferences.getString("msg" , "not found" );
            textView.setText(mymsg);
        }

    }
}
