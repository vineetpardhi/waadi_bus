package com.example.siddhant.loginui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient client;
    DatabaseReference reff;
    static List<newdriver> driversLoc=new ArrayList<newdriver>();
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        final Bundle b = getIntent().getExtras();

        final String src = (b.getString("src")).toLowerCase().trim();
        final String dest = (b.getString("dest")).toLowerCase().trim();
        btn=findViewById(R.id.button17);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            client = LocationServices.getFusedLocationProviderClient(this);
            client.getLastLocation().addOnSuccessListener(MapsActivity2.this, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        getNearby(location.getLatitude(), location.getLongitude());
                    }
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reff=FirebaseDatabase.getInstance().getReference().child("member").child("Hznz");
                    reff.child("source").setValue(src);
                    reff.child("destination").setValue(dest);
                    client.getLastLocation().addOnSuccessListener(MapsActivity2.this, new OnSuccessListener<Location>() {

                        @Override
                        public void onSuccess(Location location) {
                            if(location != null) {
                              reff.child("latitude").setValue(location.getLatitude());
                              reff.child("longitude").setValue(location.getLongitude());
                            }
                        }
                    });

                }
            });
    }

    public void getNearby(final Double lat, final Double longi)
    {
        reff=FirebaseDatabase.getInstance().getReference().child("member");

        reff.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                        String cust = listsnapshot.child("role").getValue(String.class);
                        if(!cust.isEmpty() && cust.equals("Driver")) {
                            if(!listsnapshot.toString().contains("Not available")) {
                                Map<String,Double> loc=(HashMap<String,Double>)listsnapshot.child("location").getValue();
                                Double newlat = loc.get("latitude");
                                if ((lat + 0.01) >= newlat && (lat - 0.01) <= newlat) {
                                    if ((longi + 0.01) >= loc.get("longitude") && (longi - 0.01) <= loc.get("longitude")) {
                                        Map<String, String> driverDetails = (HashMap<String, String>) listsnapshot.getValue();
                                        newdriver newd = new newdriver();
                                        newd.setName(driverDetails.get("name"));
                                        newd.setLatitude(loc.get("latitude"));
                                        newd.setLongitude(loc.get("longitude"));
                                        newd.setRno(driverDetails.get("rickshawno"));
                                        newd.setMobileno(driverDetails.get("mobileno"));
                                        driversLoc.add(newd);

                                    }
                                }
                            }
                        }
                    }
                    onMapReady(mMap);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        for(int i=0;i<driversLoc.size();i++)
        {
            LatLng latLng=new LatLng(driversLoc.get(i).getLatitude(),driversLoc.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).position(latLng)
                    .title(driversLoc.get(i).getName())
                    .snippet(driversLoc.get(i).getRno())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.0f));
            mMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        reff=FirebaseDatabase.getInstance().getReference().child("member");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    Map<String,String>  customer=(HashMap<String,String>)listsnapshot.getValue();

                    if(!customer.isEmpty() && customer.get("role").equals("Driver")&&customer.get("name").equals(marker.getTitle())) {
                        String s = "tel:" + customer.get("mobileno");

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(s));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                       // Toast.makeText(getApplicationContext(), "sds", Toast.LENGTH_SHORT).show();

                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return true;
    }
}
