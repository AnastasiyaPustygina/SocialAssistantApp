package com.example.mainproject.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.model.Organization;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment  {

    private static final int request_Code = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
               /* googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        googleMap.addMarker(markerOptions);

                    }
                });*/
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission
                        (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_Code );
                }
                googleMap.setMyLocationEnabled(true);
                OpenHelper openHelper = new OpenHelper(getContext(), "OpenHelder", null, OpenHelper.VERSION);
                Toast.makeText(getContext(),
                        openHelper.findPersonByLogin(getArguments().getString("LOG")).getCity(), Toast.LENGTH_LONG).show();
                try {
                    LatLng latLng = getLocationFromAddress("Moscow, Parshina street, 8", googleMap);
                    Marker parshinaMarker = googleMap.addMarker(new MarkerOptions().position(latLng).
                            title("Address3"));
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            Organization organization = openHelper.findOrgByAddress(marker.getTitle());
                            ArrayList<Organization> arrayListOrg = new ArrayList<>();
                            arrayListOrg.add(organization);

                            return false;
                        }
                    });
                    latLng = getLocationFromAddress("Moscow, Perovskaya street, 1", googleMap);
                    Marker perovskayaMarker = googleMap.addMarker(new MarkerOptions().position(latLng).
                            title("SecondAddress"));
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public LatLng getLocationFromAddress(String strAddress, GoogleMap mMap) {
        Geocoder coder = new Geocoder(getContext());
        LatLng latLng = null;
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return latLng;
            }
            Address location = address.get(0);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

}