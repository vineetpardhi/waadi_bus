package com.example.siddhant.loginui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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

public class rikshaw4 extends AppCompatActivity{
    Switch aswitch;
    private FusedLocationProviderClient client;
    static double lat1;
    static double long1;
    DatabaseReference reff;
    DatabaseReference reff2;
    static List<member> customers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw4);
        final usersAdapter[] uAdapter = new usersAdapter[1];
        final RecyclerView list = findViewById(R.id.lists);
        list.setLayoutManager(new LinearLayoutManager(this));
        aswitch=findViewById(R.id.switch2);
        client= LocationServices.getFusedLocationProviderClient(this);
        //add here
        Toast.makeText(getApplicationContext(),"username need to be add",Toast.LENGTH_SHORT).show();
        reff=FirebaseDatabase.getInstance().getReference().child("member").child("Vidhesh").child("location");


//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                Map<String, String> td = (HashMap<String, String>)snapshot.getValue();
//                //driversLoc=td.get("Location");
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                }
            }
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.INTERNET)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.INTERNET}, 101);
                }
            }

            aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b == true) {
                        Toast.makeText(getApplicationContext(), "Location on", Toast.LENGTH_SHORT).show();
                        client.getLastLocation().addOnSuccessListener(rikshaw4.this, new OnSuccessListener<Location>() {

                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {
                                    reff.setValue(location);
                                    lat1 = location.getLatitude();
                                    long1 = location.getLongitude();
                                    reff2=FirebaseDatabase.getInstance().getReference().child("member");
                                    reff2.addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                                               // Toast.makeText(getApplicationContext(),String.valueOf("AS"+customers.size()),Toast.LENGTH_LONG).show();
                                                Map<String, Double> td = (HashMap<String, Double>) listsnapshot.getValue();

                                                if(td.get("latitude")!=null&&lat1+0.01>=td.get("latitude")&&lat1-0.01<=td.get("latitude")) {
                                                    if(long1+0.01>=td.get("longitude")&&long1-0.01<=td.get("longitude")) {

                                                        member nd = new member();
                                                        Map<String, String> td1 = (HashMap<String, String>) listsnapshot.getValue();
                                                        nd.setSource(td1.get("source"));
                                                        nd.setDestination(td1.get("destination"));
                                                        customers.add(nd);

                                                    }
                                                }
                                            }
                                            Toast.makeText(getApplicationContext(),String.valueOf(customers.size()),Toast.LENGTH_LONG).show();
                                            if(customers.size()!=0) {
                                                member[] mem = new member[customers.size()];
                                                int count = 0;
                                                for (member c : customers) {
                                                    mem[count++] = c;
                                                }

                                                uAdapter[0] = new usersAdapter(mem);
                                                list.setAdapter(uAdapter[0]);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        });
                    } else {
                        reff.setValue("Not available");
                        Toast.makeText(getApplicationContext(), "Location off", Toast.LENGTH_SHORT).show();

                    }
                }
            });
    }


}
