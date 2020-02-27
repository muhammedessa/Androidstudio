package org.codeforiraq.myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.codeforiraq.Controller.DatabaseHandler;
import org.codeforiraq.Controller.SessionManager;
import org.codeforiraq.Controller.VolleySingleton;
import org.codeforiraq.Model.Place;
import org.codeforiraq.SERVER.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {

    private TextView latitude , longitude ,title;

    private Button delete , edit, logout;
    private DatabaseHandler db;
    private Bundle extras ;
    private int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        latitude = findViewById(R.id.textViewlatitude);
        longitude = findViewById(R.id.textViewlongitude);
        title = findViewById(R.id.textViewName);
        delete = findViewById(R.id.buttonDelete);
        edit = findViewById(R.id.buttonEdit);
        logout = findViewById(R.id.buttonLogout);

        db = new DatabaseHandler(this);
        extras = getIntent().getExtras();


        if(extras != null){

            latitude.setText(String.valueOf(extras.getDouble("latitude")));
            longitude.setText(String.valueOf(extras.getDouble("longitude")));
            title.setText(extras.getString("title"));

            Float testNum =  extras.getFloat("id") ;
             num = Math.round(testNum);

//
//            Toast.makeText(this, num +""  ,
//                    Toast.LENGTH_SHORT).show();
        }


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(num != 0){

                  deleteData( num );

                  Intent intent = new Intent( ShowActivity.this,MapsActivity.class );
                  startActivity(intent);
                  finish();
              }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, EditActivity.class);
                intent.putExtra("id" , num);
                intent.putExtra("title" , title.getText().toString());
                intent.putExtra("latitude" , latitude.getText().toString());
                intent.putExtra("longitude" , longitude.getText().toString());

                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(ShowActivity.this).userLogout();
                finish();
                startActivity(new Intent(ShowActivity.this,LoginActivity.class));
            }
        });



    }





    public void deleteData(  int id){


        final String token = SessionManager.getInstance(this).getToken().getToken();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET,
                URLs.URL_DELETE_DATA+id, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    if(response.getBoolean("success")){
                        Toast.makeText(ShowActivity.this,response.getString("message")
                                ,Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(ShowActivity.this,"error"
                                ,Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } )
        {
            public Map<String,String> getHeaders(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("Accept","application/json");
                params.put("Authorization","Bearer  "+ token);
                return params;
            }
        }

                ;

        VolleySingleton.getInstance(ShowActivity.this).addToRequestQueue(jsonObjectRequest);

    }








}
