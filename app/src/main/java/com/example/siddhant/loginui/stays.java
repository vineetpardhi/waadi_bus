package com.example.siddhant.loginui;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class stays extends AppCompatActivity {

    private Button lbtn, nav_btn, close_btn;


    TextView txt;
    SaveSharedPreference session;
    private DrawerLayout mdl;
    private NavigationView ng;
    private DatabaseReference db;

    private ImageView prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stays);

        txt = findViewById(R.id.usern);

        mdl = findViewById(R.id.drawerlayout);


        nav_btn = findViewById(R.id.nav_btn);

        close_btn = findViewById(R.id.close_btn);


        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdl.openDrawer(Gravity.LEFT);

            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdl.closeDrawer(Gravity.LEFT);
            }
        });

        prf = findViewById(R.id.stay_usrp);
        lbtn=findViewById(R.id.register_manager);


        session = new SaveSharedPreference(getApplicationContext());




        if(session.isLoggedIn()) { //if user is logged in it will check for manager child



            HashMap<String, String> user = session.getUserDetails();

            final String name = user.get(SaveSharedPreference.KEY_NAME);

            txt.setText(name);
            db = FirebaseDatabase.getInstance().getReference("member").child(name);


            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("manager_det")) {
                        lbtn.setVisibility(View.GONE);



                        prf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext(),manager_dashboard.class));
                            }
                        });


                        lbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(),"you have already registered",Toast.LENGTH_SHORT).show();
                            }
                        });



                    } else {
                        lbtn.setVisibility(View.VISIBLE);
                        lbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext(),manager.class));
                            }
                        });
                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else {
            lbtn.setVisibility(View.VISIBLE);
            txt.setText("");

            lbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"please Login",Toast.LENGTH_SHORT).show();
                }
            });

        }

    }



    public void gotoManager(View view) {
        startActivity(new Intent(getApplicationContext(),manager.class));
    }
}
