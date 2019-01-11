package com.example.fiqhrimuliandaputr.locationmaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{

    private GoogleMap mMap;
            private GoogleApiClient googleApiClient;
            private LocationRequest locationRequest;
            private Location lastlocation;
            private Marker currentUserLocationMarker;
            private static final int Request_User_Location_code = 99;


            @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    CheckUserLocationPermission();
                }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btnmosque = (Button) findViewById(R.id.btn_mosque);
                btnmosque.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        Intent intent = new Intent(MapsActivity.this, MapsActivity2.class);
                        startActivity(intent);
                    }
                });
                Button btntoilet = (Button) findViewById(R.id.btn_toilet);
                btntoilet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        Intent intent = new Intent(MapsActivity.this, MapsActivity4.class);
                        startActivity(intent);
                    }
                });
                Button btncanteen = (Button) findViewById(R.id.btn_restaurant);
                btncanteen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        Intent intent = new Intent(MapsActivity.this, MapsActivity3.class);
                        startActivity(intent);
                    }
                });


            }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
        LatLng fmipa = new LatLng(-6.557657, 106.731301);
        mMap.addMarker(new MarkerOptions().position(fmipa).title("Lokasi FMIPA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fmipa));

        LatLng fapet = new LatLng(-6.560530, 106.730406);
        mMap.addMarker(new MarkerOptions().position(fapet).title("Lokasi GWW"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fapet));

        LatLng CCR = new LatLng( -6.556069, 106.731038);
        mMap.addMarker(new MarkerOptions().position(CCR).title("Lokasi CCR"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CCR));

        LatLng gimipb = new LatLng(-6.557220, 106.732530);
        mMap.addMarker(new MarkerOptions().position(gimipb).title("Lokasi Gymnasium IPB"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gimipb));

        LatLng rektorat = new LatLng(-6.560375, 106.725824);
        mMap.addMarker(new MarkerOptions().position(rektorat).title("Lokasi Rektorat"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rektorat));


        //mMap.animateCamera(CameraUpdateFactory.zoomBy(11));


    }
            public boolean CheckUserLocationPermission()
            {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                    {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code );
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_code );
                    }
                    return false;
                }
                else
                {
                    return true;
                }
            }


            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

                switch (requestCode)
                {
                    case Request_User_Location_code:
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        {
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                            {
                                if (googleApiClient == null)
                                {
                                    buildGoogleApiClient();
                                }
                                mMap.setMyLocationEnabled(true);
                            }
                        }
                        else
                        {
                            Toast.makeText(this,"Permission Dennied...", Toast.LENGTH_SHORT).show();
                        }
                        return;
                }
            }

            protected synchronized void buildGoogleApiClient()
            {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                googleApiClient.connect();
            }

            @Override
            public void onLocationChanged(Location location) {

                lastlocation = location;

                if (currentUserLocationMarker != null)
                {
                    currentUserLocationMarker.remove();
                }
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("User Location");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                currentUserLocationMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomBy(11));

                if (googleApiClient != null)
                {
                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                }
            }

            //@TargetApi(Build.VERSION_CODES.M)
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                locationRequest = new LocationRequest();
                locationRequest.setInterval(1100);
                locationRequest.setFastestInterval(1100);
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            }
        }

