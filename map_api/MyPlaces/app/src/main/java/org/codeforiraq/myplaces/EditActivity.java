package org.codeforiraq.myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditActivity extends AppCompatActivity {


    private EditText title;
    private Button editBtn;
    private DatabaseHandler db;
    private Bundle extras ;

    private String latitude ="" ;
    private String longitude ="" ;
    private int testNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.nameEditpage);
        editBtn = findViewById(R.id.buttonEditpage) ;
        db = new DatabaseHandler(this);
        extras = getIntent().getExtras();





        if(extras != null){

            title.setText(extras.getString("title"));
            latitude = extras.getString("latitude");
            longitude = extras.getString("longitude");
         testNum =  extras.getInt("id") ;

        }



        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editData(testNum);

                    Intent intent = new Intent( EditActivity.this,MapsActivity.class );
                    startActivity(intent);
                    finish();
            }
        });

    }






    private void editData(int id){
        final String token = SessionManager.getInstance(this).getToken().getToken();

        final String myName = title.getText().toString().trim();
        final String mylatitude = latitude ;
        final String mylongitude = longitude ;

        if(TextUtils.isEmpty(myName)){
            title.setError("Enter you email please");
            title.requestFocus();
            return;
        }

        if(mylatitude.isEmpty()){
            return;
        }
        if(mylongitude.isEmpty()){
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JSONObject postparams = new JSONObject();
        try {
            postparams.put("name",myName);
            postparams.put("latitude",mylatitude);
            postparams.put("logitude",mylongitude);
        }catch (JSONException e){
            e.getMessage();
        }



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.PUT,
                URLs.URL_ALL_DATA +"/"+id , postparams, new Response.Listener<JSONObject>() {

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
