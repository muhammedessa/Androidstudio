package org.codeforiraq.mymap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,
   GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;

    private  LatLng university_of_Suly = new LatLng(35.576881, 45.357077);
    private  LatLng chaviLand = new LatLng(35.580843, 45.467475);
    private  LatLng suly_International_Airport = new LatLng(35.563680, 45.322249);
    private  LatLng aUIS = new LatLng(35.569602, 45.349672);

    private Marker mUniversity_of_Suly;
    private Marker mchaviLand;
    private Marker msuly_International_Airport;
    private Marker maUIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        List<Marker> markerList = new ArrayList<>();

        mUniversity_of_Suly =   mMap.addMarker(new MarkerOptions()
                .position(university_of_Suly).title("University of Sulaimani")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))) ;
        mUniversity_of_Suly.setTag(0);
        markerList.add(mUniversity_of_Suly);

        mchaviLand =   mMap.addMarker(new MarkerOptions()
                .position(chaviLand).title("Chavi Land")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))) ;
        mchaviLand.setTag(0);
        markerList.add(mchaviLand);

        msuly_International_Airport =   mMap.addMarker(new MarkerOptions()
                .position(suly_International_Airport).title("Sulaimani International Airport")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))) ;
        msuly_International_Airport.setTag(0);
        markerList.add(msuly_International_Airport);

        maUIS =   mMap.addMarker(new MarkerOptions()
                .position(aUIS).title("American University of Iraq -  Sulaimani")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))) ;
        maUIS.setTag(0);
        markerList.add(maUIS);


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMarkerClickListener(this);

        for(Marker m : markerList){
            LatLng latLng = new LatLng(m.getPosition().latitude,m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        }


        //35.576881, 45.357077
        // Add a marker in Sydney and move the camera
       // LatLng sulaimani = new LatLng(35.576881, 45.357077);
//        mMap.addMarker(new MarkerOptions().position(sulaimani)
//                .title("University of Sulaimani"))
//                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sulaimani,16));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this,marker.getTitle(),Toast.LENGTH_SHORT).show();

        return false;
    }
}
