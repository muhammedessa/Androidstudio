package org.codeforiraq.ratingbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonShow;
    TextView textViewResult;
    RatingBar ratingBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.mytextview);
        ratingBar = findViewById(R.id.ratingBar);
        buttonShow = findViewById(R.id.buttonshow);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalValue = ratingBar.getNumStars() + "";
                String rating = ratingBar.getRating() +"";
                textViewResult.setText("Total value is " + totalValue + "\n" +"Your result: "+rating);
                Toast.makeText(getApplicationContext(), "Your result: "+rating ,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
