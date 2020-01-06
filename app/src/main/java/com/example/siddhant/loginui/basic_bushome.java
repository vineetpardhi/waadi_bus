/*
*
basic bushome consists of options for private bus and st bus
*
*************author:vineet paradhi****************
*
*
*
*
*/

package com.example.siddhant.loginui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class basic_bushome extends AppCompatActivity {

    private DrawerLayout mdl;
    private Button nav_btn,close_btn;

    private Boolean flag;



    private TextView agnbtn,usrn,bk_op;
    private SaveSharedPreference session;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_bushome);

        mdl=findViewById(R.id.drawerlayout2);




        session=new SaveSharedPreference(getApplicationContext());




        HashMap<String, String> user = session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);

        usrn=findViewById(R.id.usern);


        agnbtn=findViewById(R.id.agn_op);
        bk_op=findViewById(R.id.bk_op);


        if(session.isLoggedIn())        //if user is logged in
        {

            usrn.setText(name);


            flag=true;
            db= FirebaseDatabase.getInstance().getReference("member").child(name);



            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {



                        agnbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(dataSnapshot.child("agency").exists())
                                {

                                    startActivity(new Intent(getApplicationContext(),agency_dashboard.class));
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Please register as agency first",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                        bk_op.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(dataSnapshot.child("trips").exists())
                                {
                                    startActivity(new Intent(getApplicationContext(),bus_trips.class));
                                }
                                else {

                                    Toast.makeText(getApplicationContext(),"Please Book a bus first",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });






                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });








        }
        else
        {

            flag=false;

            usrn.setText("");






        }



        nav_btn=findViewById(R.id.nav_btn2);

        close_btn=findViewById(R.id.close_btn2);




        if(!flag){

            agnbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();
                }
            });

            bk_op.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();
                }
            });
        }







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


























    }

    public void on_bushome(View view) {
        startActivity(new Intent(getApplicationContext(),bus_home.class));
    }

    public void on_prvt(View view) {
        startActivity(new Intent(getApplicationContext(),pvt_bus_home.class));
    }
}
