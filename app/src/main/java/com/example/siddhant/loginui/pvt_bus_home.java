package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class pvt_bus_home extends AppCompatActivity {

    SaveSharedPreference session;
    private Button b1,b2;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvt_bus_home);

        session=new SaveSharedPreference(getApplicationContext());



        b1=findViewById(R.id.rgagency);
        b2=findViewById(R.id.hireb);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.isLoggedIn()) {





                    HashMap<String,String> user=session.getUserDetails();

                    final String usrn=user.get(SaveSharedPreference.KEY_NAME);

                    db= FirebaseDatabase.getInstance().getReference("member").child(usrn);


                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child("agency").exists())
                            {
                                Toast.makeText(getApplicationContext(),"You have already registered",Toast.LENGTH_LONG).show();


                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),agency_form.class));
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                } else {

                    Toast.makeText(pvt_bus_home.this,"please Login",Toast.LENGTH_SHORT).show();
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (session.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), hire_bus.class));
                } else {

                    Toast.makeText(pvt_bus_home.this,"please Login",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
