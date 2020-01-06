package com.example.siddhant.loginui;

import androidx.core.app.ActivityCompat;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class trail extends AppCompatActivity {

    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail);

        client= LocationServices.getFusedLocationProviderClient(this);

        Button button=findViewById(R.id.getLoc);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void requesPermission(){
        ActivityCompat .requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }


}
