package com.example.siddhant.loginui;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
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

public class rikshaw4 extends AppCompatActivity{
    Switch aswitch;
    private FusedLocationProviderClient client;
    static double lat1;
    static double long1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw4);
        aswitch=findViewById(R.id.switch2);
        client= LocationServices.getFusedLocationProviderClient(this);

        aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    Toast.makeText(getApplicationContext(),"Location on",Toast.LENGTH_SHORT).show();
                    client.getLastLocation().addOnSuccessListener(rikshaw4.this, new OnSuccessListener<Location>() {

                        @Override
                        public void onSuccess(Location location) {
                            Toast.makeText(rikshaw4.this, location.toString(), Toast.LENGTH_LONG).show();

                            if(location!=null)
                            {
                                TextView textView=findViewById(R.id.location);
                                textView.setText(location.toString());
                                lat1=location.getLatitude();
                                long1=location.getLongitude();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Location off",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
