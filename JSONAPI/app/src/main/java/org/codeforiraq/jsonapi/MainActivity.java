package org.codeforiraq.jsonapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    String url = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                url ,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              //  Log.d("Response: ", response.toString());

                for(int i=0 ; i < response.length() ; i++){
                    try {
                        JSONObject postObject = response.getJSONObject(i);
                      String myInfo  = postObject.getString("id")+" - "+
                              postObject.getString("title") ;

                        Log.d("postObject", myInfo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        } );

        queue.add(arrayRequest);


    }
}
