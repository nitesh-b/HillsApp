package com.hills.hills11.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.inappmessaging.CommonTypesProto;
import com.hills.hills11.R;
import com.hills.hills11.constants.MapStringConstants;
import com.hills.hills11.data.LocationDetailData;
import com.hills.hills11.dialog.BranchDetailDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class LocationFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    //Constants

    private static final LatLng lidcombe = new LatLng ( -33.8586621 , 151.0563521 );
    private static final LatLng silverwater = new LatLng ( -33.8270365 , 151.0468572 );

    private static final int LOCATION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 16f;
    private GoogleMap mMap;
    private View mapView;
    private Context mContext;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private Location mLastKnownLocation;

    private Spinner stateSpinner, citySpinner;
    private Button getDetailButton;


    //Button
    private Button nearmeBtn;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_location , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        mContext = getContext ( );
        nearmeBtn = view.findViewById ( R.id.mapGetDetailsButton);

        stateSpinner = view.findViewById ( R.id.selectStateSpinner );
        citySpinner = view.findViewById ( R.id.selectBranchSpinner );
        getDetailButton = view.findViewById ( R.id.mapGetDetailsButton );
        getDetailButton.setVisibility ( View.GONE );
        citySpinner.setVisibility ( View.GONE );


        ArrayAdapter<String> stateAdapter = new ArrayAdapter<> ( mContext, android.R.layout.simple_list_item_1, MapStringConstants.mapStateSelectionOptions );
        stateAdapter.setDropDownViewResource ( R.layout.spinner_item );
        stateSpinner.setAdapter ( stateAdapter );

        stateSpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                 stateSpinner.setSelection ( position );
                Toast.makeText ( getContext ( ) , stateSpinner.getSelectedItem ().toString () , Toast.LENGTH_SHORT ).show ( );
                String state = stateSpinner.getSelectedItem ().toString ().toLowerCase ();
                String[] city = MapStringConstants.getCity ( state );
                citySpinner.setVisibility ( View.VISIBLE );
                ArrayAdapter<String > cityAdapter = new ArrayAdapter<> ( getContext (), android.R.layout.simple_list_item_1, city );
                cityAdapter.setDropDownViewResource ( R.layout.spinner_item );
                citySpinner.setAdapter ( cityAdapter );
                citySpinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {
                    @Override
                    public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                        getDetailButton.setVisibility ( View.VISIBLE );

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                } );



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        nearmeBtn.setOnClickListener ( this );
        mLastKnownLocation = null;

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient ( Objects.requireNonNull ( getActivity ( ) ) );

        //these next three lines are MUST for Place API. Remember: we need to implement Place API
        Places.initialize ( mContext , getString ( R.string.APIKey ) );
        placesClient = Places.createClient ( mContext );

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager ( ).findFragmentById ( R.id.mapId );
        assert mapFragment != null;
        mapFragment.getMapAsync ( this );
        mapView = mapFragment.getView ( );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Toast.makeText(getActivity(), "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        getLocationPermission ( );

        //Check if GPS is enabled or not and request user to enable it
        LocationRequest locationRequest = LocationRequest.create ( );
        locationRequest.setInterval ( 10000 );
        locationRequest.setFastestInterval ( 5000 );
        locationRequest.setPriority ( LocationRequest.PRIORITY_HIGH_ACCURACY );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder ( ).addLocationRequest ( locationRequest );

        SettingsClient settingsClient = LocationServices.getSettingsClient ( Objects.requireNonNull ( getActivity ( ) ) );
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings ( builder.build ( ) );

        task.addOnSuccessListener ( getActivity ( ) , new OnSuccessListener<LocationSettingsResponse> ( ) {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        } );

        task.addOnFailureListener ( getActivity ( ) , new OnFailureListener ( ) {
            @Override
            public void onFailure(@NonNull Exception e) {
                if ( e instanceof ResolvableApiException ) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult ( getActivity ( ) , 101 );
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace ( );
                    }
                }
            }
        } );
    }

    //get the Permission to access location
    private void getLocationPermission() {
        if ( ContextCompat.checkSelfPermission ( mContext , Manifest.permission.ACCESS_FINE_LOCATION ) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission ( mContext , Manifest.permission.ACCESS_COARSE_LOCATION ) !=
                        PackageManager.PERMISSION_GRANTED ) {

            Dexter.withActivity ( getActivity ( ) )
                    .withPermission ( Manifest.permission.ACCESS_FINE_LOCATION )
                    .withListener ( new PermissionListener ( ) {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            getDeviceLocation ( );
                            mMap.setMyLocationEnabled ( true );
                            mMap.getUiSettings ( ).setMyLocationButtonEnabled ( true );

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if ( response.isPermanentlyDenied ( ) ) {
                                AlertDialog.Builder builder = new AlertDialog.Builder ( (mContext) );
                                builder.setTitle ( "Permission Denied" ).setMessage ( "Permission has been permanently denied. Please change the location permission setting." )
                                        .setNegativeButton ( "Cancel" , null )
                                        .setPositiveButton ( "OK" , new DialogInterface.OnClickListener ( ) {
                                            @Override
                                            public void onClick(DialogInterface dialog , int which) {
                                                Intent intent = new Intent ( );
                                                intent.setAction ( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
                                                intent.setData ( Uri.fromParts ( "package" , mContext.getPackageName ( ) , null ) );
                                            }
                                        } ).show ( );
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission , PermissionToken token) {
                            token.continuePermissionRequest ( );
                        }
                    } ).check ( );
        } else {
            getDeviceLocation ( );
            mMap.setMyLocationEnabled ( true );
            mMap.getUiSettings ( ).setMyLocationButtonEnabled ( true );

        }

    }


    //Get your Device Location
    private void getDeviceLocation() {

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 180, 180, 0);

        }


        mFusedLocationProviderClient.getLastLocation ( )
                .addOnCompleteListener ( new OnCompleteListener<Location> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if ( task.isSuccessful ( ) ) {
                            Log.d ( TAG , "onComplete: Found Location" );
                            mLastKnownLocation = task.getResult ( );
                            mMap.moveCamera ( CameraUpdateFactory.newLatLngZoom ( new LatLng ( mLastKnownLocation.getLatitude ( ) , mLastKnownLocation.getLongitude ( ) ) , DEFAULT_ZOOM ) );
                        }
                    }
                } );
    }

    @Override
    public void onClick(View v) {

        if ( v.getId ( ) == R.id.mapGetDetailsButton ) {
            if(citySpinner.getSelectedItem ().equals ( "Alexandria Branch" )){
               new MapStringConstants.Alexandria ();
                LocationDetailData data = new LocationDetailData ( MapStringConstants.Alexandria.alexandriaLatLng,
                        MapStringConstants.Alexandria.name,
                        MapStringConstants.Alexandria.address,
                        MapStringConstants.Alexandria.phone,
                        MapStringConstants.Alexandria.openingHours);
                openBranchDetailDialog (data);
            }else if(citySpinner.getSelectedItem ().equals ( "Seven Hills Branch" )){
                new MapStringConstants.Sevenhills ();
                LocationDetailData data = new LocationDetailData ( MapStringConstants.Sevenhills.sevenhills,
                        MapStringConstants.Sevenhills.name,
                        MapStringConstants.Sevenhills.address,
                        MapStringConstants.Sevenhills.phone,
                        MapStringConstants.Sevenhills.openingHours);

                openBranchDetailDialog (data);
            }



        }
    }

    private void openBranchDetailDialog(LocationDetailData data) {
        BranchDetailDialog dialog = new BranchDetailDialog ( data );
        dialog.show ( getChildFragmentManager (), "branch location" );
        mMap.moveCamera ( CameraUpdateFactory.newLatLngZoom ( data.getLatLng () , DEFAULT_ZOOM ) );
        mMap.addMarker ( new MarkerOptions ( ).position ( data.getLatLng () ).title ( data.getName () ) );
    }
}