package org.codeforiraq.locationsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView showGPS , mysubAdminArea , myAddressLine;
    private EditText phoneNumber;
    private Button buttonSend;

    private LocationManager locationManager;
    private LocationListener locationListener;

    String[] appPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS
    };
    private static final int PERMISSIONS_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGPS = findViewById(R.id.showGPSTEXT);
        mysubAdminArea = findViewById(R.id.mysubAdminArea);
        myAddressLine = findViewById(R.id.myAddressLine);
        phoneNumber = findViewById(R.id.editTextNumber);
        buttonSend = findViewById(R.id.sendButton);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sms = showGPS.getText().toString() + " --- "
                        + myAddressLine.getText().toString();
                String phoneNum = phoneNumber.getText().toString();

                if(!TextUtils.isEmpty(sms) && !TextUtils.isEmpty(phoneNum)  ){
                    if(checkAndRequestPermissions()){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNum ,
                                null , sms ,null,null);
                    }
                }
            }
        });

        if (checkAndRequestPermissions()) {

        }


        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                String fullAddress = "";

                showGPS.setText(location.getLatitude() + " - " + location.getLongitude() );
                Log.d("location", location.toString());

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                            location.getLongitude(),1  );
                    if(addressList != null && addressList.size() > 0){
                        if(addressList.get(0).getSubAdminArea() != null){
                            mysubAdminArea.setText(addressList.get(0).getSubAdminArea() +"");
                            fullAddress += addressList.get(0).getSubAdminArea() +"";
                        }
                        if(addressList.get(0).getAddressLine(0) != null){
                            myAddressLine.setText(addressList.get(0).getAddressLine(0)  +"");
                            fullAddress += addressList.get(0).getAddressLine(0)  +"";
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0,
                0, locationListener);
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for(String perm : appPermissions){
            if(ContextCompat.checkSelfPermission(this,perm)
                    != PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(perm);
            }
        }

        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(
                    new String[listPermissionsNeeded.size()]) ,PERMISSIONS_REQUEST_CODE
            ) ;
            return false;
        }

       return  true;
    }
}
