package org.codeforiraq.myplacesapi.UI;

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

import org.codeforiraq.myplacesapi.Controller.SessionManager;
import org.codeforiraq.myplacesapi.Controller.VolleySingleton;
import org.codeforiraq.myplacesapi.MapsActivity;
import org.codeforiraq.myplacesapi.R;
import org.codeforiraq.myplacesapi.Utils.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    private EditText title;
    private Button editBTN;
    private Bundle extras;
    private String latitude;
    private String longitude;
    private int textNum = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        title = findViewById(R.id.nameEditPage);
        editBTN = findViewById(R.id.buttonUpdate);

        extras = getIntent().getExtras();

        if(extras != null){
            title.setText( extras.getString("title") );
            latitude = extras.getString("latitude") ;
            longitude = extras.getString("longitude") ;

            textNum = extras.getInt("id");
        }

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData(textNum);

                finish();
                startActivity(new Intent(EditActivity.this, MapsActivity.class));
            }
        });

    }






    private void   editData(int id) {

        final String token = SessionManager.getInstance(this).getToken().getToken();

        final String myLatitude = latitude ;
        final String mylongitude = longitude ;
        final String myTitle = title.getText().toString().trim();

        //validating inputs
        if (myLatitude.isEmpty()) {

            return;
        }

        if (mylongitude.isEmpty()) {

            return;
        }

        if (TextUtils.isEmpty(myTitle)) {
            title.setError("Please enter your password");
            title.requestFocus();
            return;
        }


        JSONObject postparams = new JSONObject();
        try {

            postparams.put("name", myTitle);
            postparams.put("latitude", myLatitude);
            postparams.put("logitude", mylongitude);
        } catch (JSONException e) {

            e.getMessage();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.PUT
                , URLs.ALL_DATA_URL+"/"+id, postparams, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success") ) {
                        Toast.makeText(EditActivity.this,
                                response.getString("message"), Toast.LENGTH_SHORT).show();


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
