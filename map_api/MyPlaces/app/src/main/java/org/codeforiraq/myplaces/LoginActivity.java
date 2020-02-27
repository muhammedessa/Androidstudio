package org.codeforiraq.myplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.codeforiraq.Controller.SessionManager;
import org.codeforiraq.Controller.VolleySingleton;
import org.codeforiraq.Model.User;
import org.codeforiraq.SERVER.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {



    private EditText email, password;
    private Button buttonRegister, buttonLogin;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmailLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        buttonRegister = findViewById(R.id.button2RegisterPage);
        buttonLogin = findViewById(R.id.button2LoginNow);
        progressBar = findViewById(R.id.progressBarLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this
                        ,RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }



    private void userLogin(){
        final String myEmail = email.getText().toString().trim();
        final String myPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(myEmail)){
            email.setError("Enter you email please");
            email.requestFocus();
            return;
        }


        if(TextUtils.isEmpty(myPassword)){
            password.setError("Enter you password please");
            password.requestFocus();
            return;
        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLs.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.VISIBLE);


                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getJSONObject("success") != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),
                                "Welcome", Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("success");

                        User user = new User(userJson.getString("token"));

                        SessionManager.getInstance(getApplicationContext()).userLogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(),
                                MapsActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        )

        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("email", myEmail);
                params.put("password", myPassword);
                return  params;

            }
        }   ;


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }

}
