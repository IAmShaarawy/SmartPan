package net.elshaarawy.smartpan.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import net.elshaarawy.smartpan.Adapters.CustomInfoWindow;
import net.elshaarawy.smartpan.GooglePlayServicesManager;
import net.elshaarawy.smartpan.R;

import java.util.Locale;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener
        , GoogleMap.OnInfoWindowClickListener {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG_SMART_PAN = "tag_smart_pan";
    private static final String TAG_CITY_STARS = "tag_city_stars";
    private static final String TAG_MY_LOCATION = "tag_my_location";
    private View v;
    private GooglePlayServicesManager playServicesManager;
    private MapView mMapView;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Marker smartPan,cityStars;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        playServicesManager = new GooglePlayServicesManager(getActivity());
        if (!playServicesManager.isPlayServicesAvailable()) {

            v = inflater.inflate(R.layout.empty_view, container, false);

        } else {

            v = inflater.inflate(R.layout.fragment_map, container, false);

            mMapView = (MapView) v.findViewById(R.id.map_fragment);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume();

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(this);

            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();

        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!googleApiClient.isConnected())
            googleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        String[] permissionArray = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        int permissionCode = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCode != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissionArray, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                loadMyLocation(getLastLocation());
            }else{
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                getActivity().finish();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Snackbar.make(v, "Suspended", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(v, "Failed", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowClickListener(this);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setInfoWindowAdapter(new CustomInfoWindow(getContext()));
        moveToEgypt(map);

        smartPan = map.addMarker(new MarkerOptions().position(new LatLng(30.0573278, 31.3324917)));
        smartPan.setTag(TAG_SMART_PAN);

        cityStars = map.addMarker(new MarkerOptions().position(new LatLng(30.0653009, 31.3416121)));
        cityStars.setTag(TAG_CITY_STARS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadMyLocation(getLastLocation());
        }
    }

    public void moveToEgypt(GoogleMap map) {
        LatLng latLng = new LatLng(30.424201, 30.6117923);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 8);
        map.animateCamera(update);
    }

    private Location getLastLocation() {
        try {
            map.setMyLocationEnabled(true);
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            return location;
        } catch (SecurityException e) {
            return null;
        }
    }

    synchronized public void loadMyLocation(Location location) {
        if (location != null) {
            Marker myLocation = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
            myLocation.setTitle("My Location");
            myLocation.setTag(TAG_MY_LOCATION);

            LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
            LatLng smart = smartPan.getPosition();
            LatLng city = cityStars.getPosition();
            double x =  SphericalUtil.computeDistanceBetween(position,city);
            double y =  SphericalUtil.computeDistanceBetween(position,smart);
            cityStars.setTitle("City Stars\n"+formatDistance(x));
            smartPan.setTitle("SmartPan\n"+formatDistance(y));
        }
    }

    private String formatDistance(double d){
        int distance  = (int) d;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(distance/1000).append(" Km\n").append(distance%1000).append(" m");
        return stringBuilder.toString();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LatLng latLng= marker.getPosition();
        String uri = String.format(Locale.ENGLISH, "geo:%s,%s?q=%s,%s(%s)",latLng.latitude, latLng.longitude
                , latLng.latitude, latLng.longitude,marker.getTitle());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
