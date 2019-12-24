package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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
}