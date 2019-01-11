package com.example.fiqhrimuliandaputr.locationmaps;

import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.location.Address;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

public class MapMarkerActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);
        SupportMapFragment mapFrag = (SupportMapFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        if(mMap != null) {

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick (LatLng latLng){
                    Geocoder geocoder = new Geocoder(MapMarkerActivity.this);
                    List<Address> list;
                    try {
                        list = geocoder.getFromLocation(latLng.latitude,
                                latLng.longitude, 1);
                        } catch (IOException e) {
                        return;
                        }
                        Address address = list.get(0);
                    if (marker != null) {
                        marker.remove();
                        }
                        MarkerOptions options = new MarkerOptions()
                                .title(address.getLocality())
                                .position(new LatLng(latLng.latitude,
                                        latLng.longitude));
                    marker = mMap.addMarker(options);
                    }
                    });
        }
    }

    public void onMapReady(final GoogleMap map) {
        this.mMap = map;
    }

    public void findLocation(View v) throws IOException {

        EditText et = (EditText)findViewById(R.id.editText);
        String location = et.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
        mMap.moveCamera(update);
        if(marker != null)
            marker.remove();
        MarkerOptions markerOptions = new MarkerOptions()
                .title(locality)
                .position(new LatLng(add.getLatitude(), add.getLongitude()));
        marker = mMap.addMarker(markerOptions);

    }
}
