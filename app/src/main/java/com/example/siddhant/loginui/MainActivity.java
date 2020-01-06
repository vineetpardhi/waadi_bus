package com.example.siddhant.loginui;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;


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


    }


}