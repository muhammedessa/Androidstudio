package org.codeforiraq.imagedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {


    private ImageView imageView;
    private TextView name , info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.textViewName);
        info = findViewById(R.id.textViewInfo);

        Bundle extras = getIntent().getExtras();
        if(extras != null ){
            String name = extras.getString("name");
            String details = extras.getString("details");
            String infoKey = extras.getString("mykey");

            showInfo(name,details, infoKey);
        }

    }


    public void showInfo(String name1 , String details1 , String infoKey){
        if(infoKey.equals("Android")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.androidicon));
            name.setText(name1);
            info.setText(details1);

        }else if(infoKey.equals("Java")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.javaicon));
            name.setText(name1);
            info.setText(details1);
        }
    }

}
