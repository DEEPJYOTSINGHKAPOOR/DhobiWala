package com.example.dhobiwala.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dhobiwala.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if(mLocationPermissionsGranted){
            getDeviceLocation();

            if(ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                    FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  && ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return ;
            }



            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init() ;

        }
    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static  final float DEFAULT_ZOOM=15f ;

    //widgets

    private EditText mSearchText ;



    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private ImageView mGps ;


    private FusedLocationProviderClient mFusedLocationProviderClient ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSearchText=findViewById(R.id.map_inputTextId);
        mGps=findViewById(R.id.map_mGpsId);

        getLocationPermission();
    }

    private void init()
    {
        Log.d(TAG, "init: initializing.");
//        Toast.makeText(MapActivity.this, "In init method ........", Toast.LENGTH_SHORT).show();

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    || event.getAction()== KeyEvent.KEYCODE_ENTER){

                    Toast.makeText(MapActivity.this, "Geolocating.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onEditorAction: Yo I am in.");
                    geoLocate();

                }else{
                    Toast.makeText(MapActivity.this, "In else//////", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onEditorAction: IME search failed." );
                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: gps icon clicked");
                getDeviceLocation();
            }
        });


    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocaitng");

        String searchString = mSearchText.getText().toString() ;

        Geocoder geocoder =new Geocoder(MapActivity.this);

        List<Address> list =new ArrayList<>();


        try {
            list=geocoder.getFromLocationName(searchString,1);
        }
        catch (IOException e){
            Log.e(TAG, "geoLocate: ", e);
        }
        if(list.size()>0){
            Address address;
            address = list.get(0);

            Toast.makeText(MapActivity.this, "geoLocate: Location is  : "+ address.toString(), Toast.LENGTH_SHORT).show();

            Log.d(TAG, "geoLocate: Location is  : "+ address.toString());

            moveCamera1(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));


        }

    }


    private void getDeviceLocation() throws SecurityException {
        Log.d(TAG, "getDeviceLocation: getting device current location .");

        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


        if(mLocationPermissionsGranted){
            final Task location=mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: Found location.");
                        Location currentLocation= (Location) task.getResult();
                        assert currentLocation != null;
                        try{
                            moveCamera1(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM ,"My Location");
                        }catch(Exception e){
                            Toast.makeText(MapActivity.this, "Please turn on your mobile location or ", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Log.d(TAG, "onComplete: Current location is null");
                        Toast.makeText(MapActivity.this, "Unable to get Current location.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void moveCamera1(LatLng latLng,float zoom,String title){
        Log.d(TAG, "moveCamera: Moving the camera to latitide : "+latLng.latitude+", long: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap(); // this line is for testing ..
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
}
