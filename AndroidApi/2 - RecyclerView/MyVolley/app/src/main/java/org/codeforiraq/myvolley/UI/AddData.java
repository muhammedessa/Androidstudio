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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.codeforiraq.myvolley.Controller.SessionManager;
import org.codeforiraq.myvolley.Controller.VolleySingleton;
import org.codeforiraq.myvolley.MainActivity;
import org.codeforiraq.myvolley.Model.Book;
import org.codeforiraq.myvolley.R;
import org.codeforiraq.myvolley.SERVER.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {

    private EditText nameAdd , authorAdd ;
    private Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);


        nameAdd = findViewById(R.id.addNameText);
        authorAdd = findViewById(R.id.addAuthorText);
        saveBtn = findViewById(R.id.savebutton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }






    private void saveData()   {
        final String  token = SessionManager.getInstance(this)
                .getToken().getToken() ;

        final String myName = nameAdd.getText().toString().trim();
        final String myAuthor = authorAdd.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(myName)) {
            nameAdd.setError("Please enter username");
            nameAdd.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(myAuthor)) {
            authorAdd.setError("Please enter Author");
            authorAdd.requestFocus();
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URLs.URL_ALL_DATA,  postparams, new Response.Listener<JSONObject>() {



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
