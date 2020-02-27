package org.codeforiraq.myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddActivity extends AppCompatActivity {


    private TextView latitude , longitude ;
    private EditText title;
    private Button save;
    private Bundle extras ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        latitude = findViewById(R.id.latitudetext);
        longitude = findViewById(R.id.longitudetext);
        title = findViewById(R.id.titleText);
        save = findViewById(R.id.buttonSave);

        extras = getIntent().getExtras();

        if(extras != null){

            latitude.setText(String.valueOf(extras.getDouble("latitude")));
            longitude.setText(String.valueOf(extras.getDouble("longitude")));
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveData();

                Intent intent = new Intent( AddActivity.this,MapsActivity.class );
                startActivity(intent);
                finish();
            }
        });
    }






    private void saveData(){
        final String token = SessionManager.getInstance(this).getToken().getToken();

        final String myLatitude = latitude.getText().toString().trim();
        final String myTitle = title.getText().toString().trim();
        final String myLongitude = longitude.getText().toString().trim();

        if(TextUtils.isEmpty(myTitle)){
            title.setError("Enter you title please");
            title.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(myLatitude)){
            latitude.setError("Enter you email please");
            latitude.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(myLongitude)){
            longitude.setError("Enter you password please");
            longitude.requestFocus();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JSONObject postparams = new JSONObject();
        try {
            postparams.put("name",myTitle);
            postparams.put("latitude",myLatitude);
            postparams.put("logitude",myLongitude);
        }catch (JSONException e){
            e.getMessage();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST,
                URLs.URL_ALL_DATA, postparams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    if(response.getBoolean("success")){
                        Toast.makeText(getApplicationContext(),response.getString("message")
                                ,Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                    }else{
                        Toast.makeText(getApplicationContext(),"error"
                                ,Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

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

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }














}
