package org.codeforiraq.myplaces;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.codeforiraq.Controller.DatabaseHandler;
import org.codeforiraq.Controller.SessionManager;
import org.codeforiraq.Controller.VolleySingleton;
import org.codeforiraq.Model.Place;
import org.codeforiraq.SERVER.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,
GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener
,GoogleMap.OnCameraIdleListener{

    private GoogleMap mMap;
    private List<Place> placeList;
    String token;

    private LatLng sulaymaniyah = new LatLng(35.556830, 45.434665);
    List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(SessionManager.getInstance(this).isLoggedIn()){
            if(SessionManager.getInstance(this).getToken() != null){
                token = SessionManager.getInstance(this).getToken().getToken();
            }
        }else {
            finish();
            startActivity(new Intent(this , LoginActivity.class));
            return;
        }



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);

        markerList = new ArrayList<>();
        placeList = new ArrayList<>();

        placeList = getData();

        for(Place p: placeList){
            markerList.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(p.getPlatitude())
                            ,Double.parseDouble(p.getPlongitude()))).title(p.getTitle())
                .zIndex( p.getId() ).snippet("By Muhammed Essa")     ));
        }

        markerList.add(mMap.addMarker(new MarkerOptions()
                .position(sulaymaniyah).title("Sulaymaniyah")));

        for(Marker m : markerList){
            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng) );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
        }





    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(MapsActivity.this, ShowActivity.class);
        intent.putExtra("latitude" , marker.getPosition().latitude);
        intent.putExtra("longitude" , marker.getPosition().longitude);
        intent.putExtra("title" , marker.getTitle());
        intent.putExtra("id" ,  marker.getZIndex()) ;
        startActivity(intent);
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent intent = new Intent(MapsActivity.this, AddActivity.class);
        intent.putExtra("latitude" , latLng.latitude);
        intent.putExtra("longitude" , latLng.longitude);

        startActivity(intent);
    }




    public List<Place> getData(){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET,
                URLs.URL_ALL_DATA, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray bookArray = response.getJSONArray("data");


                    for (int i = 0; i < bookArray.length(); i++) {
                        JSONObject bookObj = bookArray.getJSONObject(i);
                        Place book = new Place();
                        book.setId(bookObj.getInt("id"));
                        book.setTitle(bookObj.getString("name"));
                        book.setPlatitude(bookObj.getString("latitude"));
                        book.setPlongitude(bookObj.getString("logitude"));

                        placeList.add(book);

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
        return placeList;
    }


    @Override
    public void onCameraIdle() {

        placeList = getData();
        for(Place p: placeList){
            markerList.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(p.getPlatitude())
                            ,Double.parseDouble(p.getPlongitude()))).title(p.getTitle())
                    .zIndex( p.getId() ).snippet("By Muhammed Essa")     ));
        }
    }
}
