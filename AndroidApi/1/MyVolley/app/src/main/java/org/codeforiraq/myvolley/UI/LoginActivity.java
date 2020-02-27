package org.codeforiraq.myvolley.UI;

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
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.codeforiraq.myvolley.Controller.SessionManager;
import org.codeforiraq.myvolley.Controller.VolleySingleton;
import org.codeforiraq.myvolley.MainActivity;
import org.codeforiraq.myvolley.Model.User;
import org.codeforiraq.myvolley.R;
import org.codeforiraq.myvolley.SERVER.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private   EditText   email , password;
    private  Button buttonGoRegister, buttonLogin;
    private  ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (SessionManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        buttonGoRegister = findViewById(R.id.buttonLogin);
        buttonLogin =    findViewById(R.id.buttonLogin);
        progressBar =    findViewById(R.id.progressBar2);

        buttonGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
               startActivity(new Intent(LoginActivity.this,
                       RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }





    private void userLogin() {
        //first getting the values
        final String myEmail = email.getText().toString();
        final String myPassword = password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(myEmail)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(myPassword)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.VISIBLE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getJSONObject("success") != null) {
                                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("success");

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("token")
                                );

                                //storing the user in shared preferences
                                SessionManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("email", myEmail);
                params.put("password", myPassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
