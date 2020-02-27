package org.codeforiraq.admobapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

public class MainActivity extends AppCompatActivity {


    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mAdView = findViewById(R.id.adView);
        // AdRequest adRequest = new AdRequest.Builder().build();
        // D2FF52B469012C1C2D5004798FEA1460
        AdRequest adRequest = new AdRequest.Builder()
                                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                                .addTestDevice("D2FF52B469012C1C2D5004798FEA1460")
                                                .build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(MainActivity.this,"onAdLoaded",Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(MainActivity.this,"onAdFailedToLoad"
                        ,Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Toast.makeText(MainActivity.this,"onAdOpened"
                        ,Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(MainActivity.this,"onAdClicked"
                        ,Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Toast.makeText(MainActivity.this,"onAdLeftApplication"
                        ,Toast.LENGTH_SHORT);
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(MainActivity.this,"onAdClosed"
                        ,Toast.LENGTH_SHORT);
            }
        });




    }
}
