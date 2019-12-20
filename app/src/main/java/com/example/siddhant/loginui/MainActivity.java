package com.example.siddhant.loginui;

import android.content.Intent;
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
    EditText username,password,cpassword,email,verification;
    Button button;
    member member;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i= getIntent();
        String name=i.getStringExtra("message");
        TextView text;
        text=findViewById(R.id.usr);

        text.setText(name);


    }

}