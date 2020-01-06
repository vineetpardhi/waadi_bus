package com.example.siddhant.loginui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class stays extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stays);
    }




    public void gotoManager(View view) {
        startActivity(new Intent(getApplicationContext(),manager.class));
    }
}
