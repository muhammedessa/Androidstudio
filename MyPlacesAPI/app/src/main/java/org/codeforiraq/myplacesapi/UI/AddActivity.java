package org.codeforiraq.myplacesapi.UI;

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

import org.codeforiraq.myplacesapi.Controller.SessionManager;
import org.codeforiraq.myplacesapi.Controller.VolleySingleton;
import org.codeforiraq.myplacesapi.MapsActivity;
import org.codeforiraq.myplacesapi.R;
import org.codeforiraq.myplacesapi.Utils.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private TextView latitude , longitude;
    private EditText title;
    private Button save;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        latitude = findViewById(R.id.textViewlat);
        longitude = findViewById(R.id.textViewlong);
        title = findViewById(R.id.editTextNameAdd);
        save = findViewById(R.id.buttonAdd);

        extras = getIntent().getExtras();

        if(extras != null){
            latitude.setText(String.valueOf(extras.getDouble("latitude")));
            longitude.setText(String.valueOf(extras.getDouble("longitude")));

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                finish();
                startActivity(new Intent(AddActivity.this, MapsActivity.class));
            }
        });



    }




    private void   saveData() {

        final String token = SessionManager.getInstance(this).getToken().getToken();

        final String myLatitude = latitude.getText().toString().trim();
        final String mylongitude = longitude.getText().toString().trim();
        final String myTitle = title.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(myLatitude)) {
            latitude.setError("Please enter your email");
            latitude.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mylongitude)) {
            longitude.setError("Please enter your password");
            longitude.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(myTitle)) {
            title.setError("Please enter your password");
            title.requestFocus();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JSONObject postparams = new JSONObject();
        try {

            postparams.put("name", myTitle);
            postparams.put("latitude", myLatitude);
            postparams.put("logitude", mylongitude);
        } catch (JSONException e) {

            e.getMessage();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.POST
                , URLs.ALL_DATA_URL, postparams, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success") ) {
                        Toast.makeText(AddActivity.this,
                                response.getString("message"), Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(AddActivity.this, MapsActivity.class));
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
