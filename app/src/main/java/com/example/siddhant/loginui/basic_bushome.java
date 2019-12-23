package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class basic_bushome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_bushome);
    }

    public void on_bushome(View view) {
        startActivity(new Intent(getApplicationContext(),bus_home.class));
    }

    public void on_prvt(View view) {
        startActivity(new Intent(getApplicationContext(),pvt_bus_home.class));
    }
}
