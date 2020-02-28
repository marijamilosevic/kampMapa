package com.mapakampovasrbije.kampmapa.ui;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapakampovasrbije.kampmapa.CampApp;
import com.mapakampovasrbije.kampmapa.R;
import com.mapakampovasrbije.kampmapa.model.CampsiteModel;

import java.util.ArrayList;

public class CampMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final int REQUEST_CODE = 100;

    private static final float DEFAULT_ZOOM = 8;

    private GoogleMap mMap;

    private View rootView;

    private FusedLocationProviderClient fusedLocationClient;

    private ArrayList<CampsiteModel> campList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_maps, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        checkPermissionAndGetLocation();
        campList = (ArrayList<CampsiteModel>) getArguments().getSerializable("list");
        return rootView;
    }

    private void checkPermissionAndGetLocation() {
        int myVersion = Build.VERSION.SDK_INT;
        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyHavePermission()) {
                requestForSpecificPermission();
            } else {
                getLastLocation();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        CampApp.getInstance().setCurrentLocation(location);
                        if (mMap != null) {
                            mMap.setMyLocationEnabled(true);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(location.getLatitude(), location.getLongitude()), DEFAULT_ZOOM));
                        }
                    }
                });
    }

    private void addLocations() {
        for (CampsiteModel campsiteModel : campList) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(campsiteModel.getName());
            markerOptions.icon(vectorToBitmap(R.drawable.ic_location_on_black_24dp, getResources().getColor(R.color.colorPrimary)));
            markerOptions.position(new LatLng(campsiteModel.getLatitude(), campsiteModel.getLongitude()));
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addLocations();
        mMap.setOnInfoWindowClickListener(this);
        Location currentLocation = CampApp.getInstance().getCurrentLocation();
        if (currentLocation != null) {
            mMap.setMyLocationEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM));

        }
    }

    private boolean checkIfAlreadyHavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestForSpecificPermission() {
        requestPermissions( new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(getActivity(), "Da bismo vam prikazali lokacije u vašoj neposrednoj blizini, molimo vas da nam dozvolite pristup vašoj lokaciji.", Toast.LENGTH_LONG).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestForSpecificPermission();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent campIntent = CampsiteActivity.createIntent(getActivity(), findLocationByMarker(marker));
        startActivity(campIntent);
    }

    private CampsiteModel findLocationByMarker(Marker marker) {
        for (CampsiteModel location : campList) {
            if (TextUtils.equals(marker.getTitle(), location.getName())) {
                return location;
            }
        }
        return null;
    }

    /**
     * Demonstrates converting a {@link Drawable} to a {@link BitmapDescriptor},
     * for use as a marker icon.
     */
    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
