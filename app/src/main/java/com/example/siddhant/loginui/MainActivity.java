package com.example.siddhant.loginui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText username,password,cpassword,email,verification;
    Button button;
    member member;
    TextView text;
    DatabaseReference reff;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        name = i.getStringExtra("message");

        text = findViewById(R.id.usr);

    }



}