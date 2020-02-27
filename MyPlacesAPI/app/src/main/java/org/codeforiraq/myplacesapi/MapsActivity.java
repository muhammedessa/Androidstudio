package org.codeforiraq.myplacesapi;

import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.codeforiraq.myplacesapi.Controller.SessionManager;
import org.codeforiraq.myplacesapi.Controller.VolleySingleton;
import org.codeforiraq.myplacesapi.Model.Place;
import org.codeforiraq.myplacesapi.UI.AddActivity;
import org.codeforiraq.myplacesapi.UI.LoginActivity;
import org.codeforiraq.myplacesapi.UI.ShowActivity;
import org.codeforiraq.myplacesapi.Utils.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback ,GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapLongClickListener,GoogleMap.OnCameraIdleListener{

    private GoogleMap mMap;
    private String token;
    private List<Place> placeList;

    private LatLng sulaymaniyeh = new LatLng(35.561265, 45.348596);
    List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(SessionManager.getInstance(this).isLoggedIn()){
            if(SessionManager.getInstance(this).getToken() != null){
                token = SessionManager.getInstance(this).getToken().getToken();
            }
        }else {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        markerList = new ArrayList<>();
        placeList = new ArrayList<>();
        placeList = getData();

        for(Place p: placeList){
            markerList.add((mMap.addMarker(new MarkerOptions()
           .position(new LatLng(Double.parseDouble(p.getPlatitude()),
                   Double.parseDouble(p.getPlongitude()))
           ).zIndex(p.getId()).title(p.getTitle()).snippet("By Muhammed Essa")) ) );

        }

        markerList.add(mMap.addMarker(new MarkerOptions().position(
                sulaymaniyeh
        ).title("sulaymaniyeh")));

        for(Marker m: markerList){

            LatLng latLng = new LatLng(m.getPosition().latitude,m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng) );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
        }


    }

    @Override
    public void onCameraIdle() {
        placeList = getData();

        for(Place p: placeList){
            markerList.add((mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(p.getPlatitude()),
                            Double.parseDouble(p.getPlongitude()))
                    ).zIndex(p.getId()).title(p.getTitle()).snippet("By Muhammed Essa")) ) );

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent intent = new Intent(MapsActivity.this, AddActivity.class);
        intent.putExtra("latitude",latLng.latitude);
        intent.putExtra("longitude",latLng.longitude);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(MapsActivity.this, ShowActivity.class);
        intent.putExtra("latitude",marker.getPosition().latitude);
        intent.putExtra("longitude",marker.getPosition().longitude);
        intent.putExtra("title",marker.getTitle() );
        intent.putExtra("id",marker.getZIndex());
        startActivity(intent);
        return false;
    }



    public List<Place> getData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET
                , URLs.ALL_DATA_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray placeArray = response.getJSONArray("data");

                    for (int i = 0; i< placeArray.length(); i++) {
                        JSONObject placeObj = placeArray.getJSONObject(i);
                        Place place = new Place();
                        place.setId(placeObj.getInt("id"));
                        place.setTitle(placeObj.getString("name"));
                        place.setPlatitude(placeObj.getString("latitude"));
                        place.setPlongitude(placeObj.getString("logitude"));
                        placeList.add(place);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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
        return placeList;
    }










}
