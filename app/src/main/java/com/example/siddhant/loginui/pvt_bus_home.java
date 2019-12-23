package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class pvt_bus_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvt_bus_home);
    }

    public void on_agency(View view) {
        startActivity(new Intent(getApplicationContext(),agency_form.class));

    }

    public void on_hire(View view) {
        startActivity(new Intent(getApplicationContext(),hire_bus.class));
    }
}
