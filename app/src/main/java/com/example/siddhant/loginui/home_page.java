package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home_page extends AppCompatActivity {
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        reff = FirebaseDatabase.getInstance().getReference();
        reff.keepSynced(true);
    }
    public void gotoLogin(View view) {
        startActivity(new Intent(getApplicationContext(),login.class));
    }


    public void gotoRickshaw(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw3.class));
    }

    public void registerRickshaw2(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw2.class));
    }
    public void gotoBus(View view) {
        startActivity(new Intent(getApplicationContext(),basic_bushome.class));
    }

    public void gotoTrail(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw4.class));
    }

    public void gotomap(View view) {
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }
}