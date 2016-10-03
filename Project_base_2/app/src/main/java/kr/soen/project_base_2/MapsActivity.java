package kr.soen.project_base_2;

import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double Lat;
    double Lon;
    private Marker marker;
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        Lat = intent.getDoubleExtra("Lat",0);
        Lon = intent.getDoubleExtra("Lon",0);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        GoogleMapOptions options = new GoogleMapOptions();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);


        LatLng loc = new LatLng(Lat, Lon);
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
     marker =  mMap.addMarker(new MarkerOptions().position(loc).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {


                Point screenPt = mMap.getProjection().toScreenLocation(point);
                LatLng latLng = mMap.getProjection().fromScreenLocation(screenPt);


                geocoder = new Geocoder(MapsActivity.this);
                List<Address> addresses = null;


                try {
                    addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String addr = addresses.get(0).getAddressLine(0);


                //remove previously placed Marker
                if (marker != null) {
                    marker.remove();
                }
                //place marker where user just clicked
                marker = mMap.addMarker(new MarkerOptions().position(point).title("Marker")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

                Intent intent = new Intent(MapsActivity.this, RegisterActivity.class);
                intent.putExtra("Lat", String.valueOf(point.latitude));
                intent.putExtra("Lon", String.valueOf(point.longitude));
                intent.putExtra("address",addr);
                setResult(RESULT_OK,intent);
                finish();

            }
        });


    }
}


