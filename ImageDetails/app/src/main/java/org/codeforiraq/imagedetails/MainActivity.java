package org.codeforiraq.imagedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView androidImage , javaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidImage = findViewById(R.id.androidImage);
        javaImage = findViewById(R.id.javaImage);

        androidImage.setOnClickListener(this);
        javaImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.androidImage:
             //   Toast.makeText(MainActivity.this,"Android",Toast.LENGTH_SHORT).show();
                Intent intentAndroid = new Intent(MainActivity.this, InfoActivity.class);
                intentAndroid.putExtra("name","Android OS");
                intentAndroid.putExtra("mykey","Android");
                intentAndroid.putExtra("details", "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets.");
                startActivity(intentAndroid);
                break;
            case R.id.javaImage:
            //    Toast.makeText(MainActivity.this,"Java",Toast.LENGTH_SHORT).show();
                Intent intentJava = new Intent(MainActivity.this, InfoActivity.class);
                intentJava.putExtra("name","Java programming");
                intentJava.putExtra("mykey","Java");
                intentJava.putExtra("details", "Java is one of the most popular and widely used programming language and platform. A platform is an environment that helps to develop and run programs written in any programming language. Java is fast, reliable and secure.");
                startActivity(intentJava);
                break;
        }
    }
}
