package org.codeforiraq.myactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button2 , button3 ;
    private final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_SHORT).show();

        button2 = findViewById(R.id.buttonSecond);
        button3 = findViewById(R.id.buttonThird);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , SecondActivity.class);

                intent.putExtra("firstname","Muhammed Essa");
                intent.putExtra("lastname","Hameed");
                intent.putExtra("welcomemessage","Hello from first to second");
                intent.putExtra("age",36);
                intent.putExtra("salary",1000.4);

              //  startActivity(intent);
                startActivityForResult(intent , REQUEST_CODE);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ThirdActivity.class);
                intent.putExtra("firstname","Ahmed Hassan");
                intent.putExtra("lastname","Omer");
                intent.putExtra("welcomemessage","Hello from first to third");
                intent.putExtra("age",26);
                intent.putExtra("salary",3000.4);
                startActivity(intent);

              //  startActivity(new Intent(MainActivity.this , ThirdActivity.class));
            }
        });

    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String returnData = data.getStringExtra("welcomemessage");
                Toast.makeText(getApplicationContext(),returnData , Toast.LENGTH_LONG).show();
            }
        }
    }


















//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(getApplicationContext(),"onStart",Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(getApplicationContext(),"onStop",Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getApplicationContext(),"onDestroy",Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(getApplicationContext(),"onPause",Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(getApplicationContext(),"onResume",Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(getApplicationContext(),"onRestart",Toast.LENGTH_SHORT).show();
//    }
}
