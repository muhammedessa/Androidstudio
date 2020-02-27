package org.codeforiraq.smsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    private Button sendSMS;
    private EditText message , phoneNumber;
    private static final int PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         message = findViewById(R.id.message);
        phoneNumber = findViewById(R.id.phoneNumber);
         sendSMS = findViewById(R.id.sendSMS);

         if(Build.VERSION.SDK_INT >= 23){
             if(checkPermission()){
                 Log.e("permission","Permission granted !");
             }else{
                 requestPermission();
             }
         }

         sendSMS.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String sms = message.getText().toString();
                 String phoneNum = phoneNumber.getText().toString();
                 if(!TextUtils.isEmpty(sms) && !TextUtils.isEmpty(phoneNum)){
                     if(checkPermission()){

          SmsManager smsManager = SmsManager.getDefault();
          smsManager.sendTextMessage(phoneNum,null,sms,null,null);
                     }else{
                         Toast.makeText(MainActivity.this,"Permission denied"
                                 ,Toast.LENGTH_SHORT).show();
                     }
                 }
             }
         });


    }

   public boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
   }




   public void requestPermission(){
       ActivityCompat.requestPermissions(this,new String[]{
               Manifest.permission.SEND_SMS
       },PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"Permission accepted"
                    ,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Permission denied"
                            ,Toast.LENGTH_SHORT).show();
                    sendSMS.setEnabled(false);
                }
                break;
        }
    }
}
