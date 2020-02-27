package org.codeforiraq.myplacesapi.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.codeforiraq.myplacesapi.Controller.SessionManager;
import org.codeforiraq.myplacesapi.Controller.VolleySingleton;
import org.codeforiraq.myplacesapi.MapsActivity;
import org.codeforiraq.myplacesapi.Model.Place;
import org.codeforiraq.myplacesapi.Model.User;
import org.codeforiraq.myplacesapi.R;
import org.codeforiraq.myplacesapi.Utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {

    private TextView latitude, longitude , title;
    private Button delete,edit,logout;
    private Bundle extras;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        latitude = findViewById(R.id.latitudeShow);
        longitude = findViewById(R.id.longitudeShow);
        title = findViewById(R.id.nameShow);
        delete = findViewById(R.id.deleteButtonShow);
        edit = findViewById(R.id.editButtonShow);
        logout = findViewById(R.id.logoutButtonShow);

        extras = getIntent().getExtras();

        if(extras != null){
            latitude.setText(String.valueOf(extras.getDouble("latitude")));
            longitude.setText(String.valueOf(extras.getDouble("longitude")));
            title.setText( extras.getString("title") );

            Float testNum = extras.getFloat("id");
            num = Math.round(testNum);


        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num != 0 ){
                    deleteData(num);

                    finish();
                    startActivity(new Intent(ShowActivity.this, MapsActivity.class));
                }
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num != 0 ){
                    Intent intent = new Intent(ShowActivity.this, EditActivity.class);
                    intent.putExtra("latitude",latitude.getText().toString().trim() );
                    intent.putExtra("longitude",longitude.getText().toString().trim() );
                    intent.putExtra("title",title.getText().toString().trim() );
                    intent.putExtra("id",num);
                    startActivity(intent);
                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(ShowActivity.this).logout();
                finish();
                startActivity(new Intent(ShowActivity.this, MapsActivity.class));
            }
        });


    }








    public void deleteData(int id){

     final  String  token = SessionManager.getInstance(this).getToken().getToken();

    // Toast.makeText(this,URLs.DELETE_URL+id , Toast.LENGTH_SHORT).show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET
                , URLs.DELETE_URL+id, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success") ) {
                        Toast.makeText(ShowActivity.this,
                                response.getString("message"), Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(ShowActivity.this, MapsActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                    } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer  "+token);
                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }











}
