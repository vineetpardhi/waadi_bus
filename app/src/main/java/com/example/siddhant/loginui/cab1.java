package com.example.siddhant.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class cab1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab1);
    }

    public void gotoCab2(View view) {
        startActivity(new Intent(getApplicationContext(),cab2.class));
    }

    public void gotoCabowner(View view) {
        startActivity(new Intent(getApplicationContext(),cabowner.class));
    }

    public void gotoCustomer(View view) {
        startActivity(new Intent(getApplicationContext(),cab3.class));
    }
}
