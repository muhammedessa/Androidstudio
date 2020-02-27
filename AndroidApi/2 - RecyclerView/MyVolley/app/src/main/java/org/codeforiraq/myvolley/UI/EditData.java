package org.codeforiraq.myvolley.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.codeforiraq.myvolley.Controller.SessionManager;
import org.codeforiraq.myvolley.Controller.VolleySingleton;
import org.codeforiraq.myvolley.MainActivity;
import org.codeforiraq.myvolley.Model.Book;
import org.codeforiraq.myvolley.R;
import org.codeforiraq.myvolley.SERVER.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EditData extends AppCompatActivity {


    private EditText name , author ;
    private Bundle extras;
    private Button   edit;
    private int id ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);



        name = findViewById(R.id.editTextName);
        author = findViewById(R.id.editTextAuthor);
        edit = findViewById(R.id.buttonEditpage);

        extras = getIntent().getExtras();

        if(extras != null){
            name.setText(extras.getString("name"));
            author.setText(extras.getString("author"));
            id = extras.getInt("id");

            String myInfo =  " name: "+ extras.getString("name")
                    + " Author: " + extras.getString("author")
                    + " ID: " + extras.getInt("id");
            Log.d("myInfo", myInfo);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             EditData( id);



                Intent intent = new Intent( EditData.this,MainActivity.class );
                startActivity(intent);
                finish();

            }
        });


    }







    private void EditData(int id) {
       final String  token = SessionManager.getInstance(this)
               .getToken().getToken() ;

        final String myName = name.getText().toString().trim();
        final String myAuthor = author.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(myName)) {
            name.setError("Please enter username");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(myAuthor)) {
            author.setError("Please enter your email");
            author.requestFocus();
            return;
        }


        JSONObject postparams = new JSONObject();
        try {
            postparams.put("name", myName);
            postparams.put("author", myAuthor);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if everything is fine
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                URLs.URL_ALL_DATA +"/"+id,  postparams, new Response.Listener<JSONObject>() {



            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(response.getBoolean("success")){
                        Log.d("message", response.getString("message"));
                    }else {
                        Log.d("message", "sorry");
                    }

                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer   "+token);
                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }





}
