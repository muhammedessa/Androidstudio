package org.codeforiraq.myvolley.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.codeforiraq.myvolley.R;

public class ShowActivity extends AppCompatActivity {


    private TextView name , author , created_at ;
    private Bundle extras;
    private Button delete , edit;
    private int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        name = findViewById(R.id.nameViewText);
        author = findViewById(R.id.authorViewText);
        created_at = findViewById(R.id.created_atViewText);

        delete = findViewById(R.id.buttonDelete);
        edit = findViewById(R.id.buttonEdit);

        extras = getIntent().getExtras();

        if(extras != null){
            name.setText(extras.getString("name"));
            author.setText(extras.getString("author"));
            created_at.setText(extras.getString("created_at"));

//            Log.d("myInfo", extras.getInt("id")
//                    +" - " + extras.getString("name")+" - " +
//                    extras.getString("author")+" - "
//                    +  extras.getString("created_at"));

        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
