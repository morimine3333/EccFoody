/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.a2170188.navigationdrawractivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class MyLocationDemoActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private UiSettings mUiSettings;

    HashMap<String, String> markerMap = new HashMap<String, String>();

//    public MyLocationDemoActivity(View mWindow) {
//        this.mWindow = mWindow;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location_demo);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        if (mMap != null) {
//            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                @Override
//                public View getInfoContents(Marker marker) {
//                    View view = getLayoutInflater().inflate(R.layout.info_window, null);
//                    TextView title = (TextView) view.findViewById(R.id.info_title);
//                    title.setText(marker.getTitle());
//                    return view;
//                }
//
//                @Override
//                public View getInfoWindow(Marker marker) {
//                    return null;
//                }
//        startActivity(new Intent(this, InfoWindow.class));
//            });
//        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mUiSettings = mMap.getUiSettings();
//        mUiSettings.isZoomGesturesEnabled();
//        mUiSettings.isZoomControlsEnabled();

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        LatLng osaka = new LatLng(34.706430, 135.503279);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(osaka,18));

        LatLng ecc = new LatLng(34.706363, 135.503596);
        Marker markerOne = mMap.addMarker(new MarkerOptions().position(ecc).title("ECCコンピュータ専門学校"));
        String idone = markerOne.getId();
        markerMap.put(idone, "action_one");

        LatLng eccart = new LatLng(34.707271, 135.503438);
        Marker markerTwo =mMap.addMarker(new MarkerOptions().position(eccart).title("ECCアーティスト美容専門学校"));
        String idtwo = markerTwo.getId();
        markerMap.put(idtwo, "action_two");

        LatLng ecckoku = new LatLng(34.705436, 135.503095);
        Marker markerThree =mMap.addMarker(new MarkerOptions().position(ecckoku).title("ECC国際外語専門学校"));
        String idthree = markerThree.getId();
        markerMap.put(idthree, "action_three");


        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);



    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
            mUiSettings.setZoomGesturesEnabled(true);
            mUiSettings.setZoomControlsEnabled(true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private Marker mSelectedMarker;
    Context context = this;


    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(mSelectedMarker)) {
            mSelectedMarker = null;
            return true;

        }
        mSelectedMarker = marker;

        return false;
    }

    public void onInfoWindowClick(Marker marker) {
        String actionId = markerMap.get(marker.getId());

        if (actionId.equals("action_one")) {
            Intent i = new Intent(context, test1.class);
            startActivity(i);
        } else if (actionId.equals("action_two")) {
            Intent i = new Intent(context, test2.class);
            startActivity(i);
        } else if (actionId.equals("action_three")) {
            Intent i = new Intent(context, test3.class);
            startActivity(i);
        }
    }

//    private final View mWindow;
//
//    MyLocationDemoActivity() {
//        mWindow = getLayoutInflater().inflate(R.layout.info_window, null);
//    }
//
//    @Override
//    public View getInfoWindow(Marker marker) {
//
//            // This means that getInfoContents will be called.
//            return null;
//        render(marker, mWindow);
//        return mWindow;
//    }

}
