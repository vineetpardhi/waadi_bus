package com.example.siddhant.loginui;

import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.view.Menu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

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
