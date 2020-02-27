package org.codeforiraq.myplacesapi.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.codeforiraq.myplacesapi.Controller.SessionManager;
import org.codeforiraq.myplacesapi.Controller.VolleySingleton;
import org.codeforiraq.myplacesapi.MapsActivity;
import org.codeforiraq.myplacesapi.Model.User;
import org.codeforiraq.myplacesapi.R;
import org.codeforiraq.myplacesapi.Utils.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    private EditText fullname , email , password;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fullname = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }





    private void registerUser() {
        final String myName = fullname.getText().toString().trim();
        final String myEmail = email.getText().toString().trim();
        final String myPassword = password.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(myName)) {
            fullname.setError("Please enter username");
            fullname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(myEmail)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(myPassword)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getBoolean("success")) {

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("data");

                                //creating a new user object name, email, token;
                                User user = new User(
                                        userJson.getString("token")
                                );

                                //storing the user in shared preferences
                                SessionManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("name",  myName);
                params.put("email",myEmail );
                params.put("password", myPassword);
                params.put("c_password", myPassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }







}
