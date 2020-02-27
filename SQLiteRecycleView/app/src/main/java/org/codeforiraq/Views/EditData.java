package org.codeforiraq.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.codeforiraq.Controller.DatabaseHelper;
import org.codeforiraq.Model.Data;
import org.codeforiraq.sqliterecycleview.MainActivity;
import org.codeforiraq.sqliterecycleview.R;

import java.util.List;

public class EditData extends AppCompatActivity {

    private EditText nameText , lnameText , ageText , descriptionText;
    private Button editButton;
    private DatabaseHelper databaseHelper;
    Data personInfo;
    int position;
    String str_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);


        nameText = findViewById(R.id.name1);
        lnameText = findViewById(R.id.lname1);
        ageText = findViewById(R.id.age1);
        descriptionText   = findViewById(R.id.description1);
        editButton = findViewById(R.id.buttonedit);


        Bundle bundle = getIntent().getExtras();
        str_position = bundle.getString("position");
        position = Integer.parseInt(str_position);
        databaseHelper = new DatabaseHelper(this);
        personInfo = databaseHelper.getData(position);

        if(personInfo != null){
            nameText.setText(personInfo.getName());
            lnameText.setText(personInfo.getLname());
            ageText.setText(personInfo.getAge());
            descriptionText.setText(personInfo.getDescription());
        }



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personInfo.setName(  nameText.getText().toString() );
                personInfo.setLname(  lnameText.getText().toString() );
                personInfo.setDescription(  descriptionText.getText().toString() );
                personInfo.setAge(  ageText.getText().toString() );

                databaseHelper.updateData(personInfo);
                MainActivity.notifyAdapter();
                Intent intent = new Intent(EditData.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
