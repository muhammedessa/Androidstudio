package org.codeforiraq.autheticationcrud.UI;

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

import org.codeforiraq.autheticationcrud.Controller.SessionManager;
import org.codeforiraq.autheticationcrud.Controller.VolleySingleton;
import org.codeforiraq.autheticationcrud.MainActivity;
import org.codeforiraq.autheticationcrud.R;
import org.codeforiraq.autheticationcrud.SERVER.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditData extends AppCompatActivity {




    private EditText nameEdit , authorEdit;
    private Bundle extras;
    private Button editButton;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);


        nameEdit = findViewById(R.id.nameEditpage);
        authorEdit = findViewById(R.id.authorEditpage);
        editButton = findViewById(R.id.buttonEditpage);

        extras = getIntent().getExtras();

        if(extras != null){
            nameEdit.setText(extras.getString("name"));
            authorEdit.setText(extras.getString("author"));
            id = extras.getInt("id");
        }



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData(id);

                finish();
                startActivity(new Intent(EditData.this, MainActivity.class));
            }
        });
    }



    private void editData(int id){
        final String token = SessionManager.getInstance(this).getToken().getToken();

        final String myName = nameEdit.getText().toString().trim();
        final String myAuthor = authorEdit.getText().toString().trim();

        if(TextUtils.isEmpty(myName)){
            nameEdit.setError("Enter you email please");
            nameEdit.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(myAuthor)){
            authorEdit.setError("Enter you password please");
            authorEdit.requestFocus();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JSONObject postparams = new JSONObject();
        try {
            postparams.put("name",myName);
            postparams.put("author",myAuthor);
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

