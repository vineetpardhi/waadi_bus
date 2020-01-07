package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class manager_dashboard extends AppCompatActivity {

    private TextView usn,hn,phoneno,address,nearby;

    private Button agnbtn;
    public String hname;
    private SaveSharedPreference session;
    private String name;
    private DatabaseReference db;
    DatabaseReference href;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        usn=findViewById(R.id.usmn);
        hn=findViewById(R.id.hn);
        phoneno=findViewById(R.id.phoneno);
        address=findViewById(R.id.hotel_addr);
        nearby=findViewById(R.id.nearby);


        session=new SaveSharedPreference(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        name=user.get(SaveSharedPreference.KEY_NAME);


        db= FirebaseDatabase.getInstance().getReference("member").child(name);


        db.addValueEventListener(new ValueEventListener() {     //getting hotel name from member field
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                       Map<String,String> ud=(HashMap<String,String>) dataSnapshot.getValue();

                       hname=ud.get("manager_det");



                //hotel reference
                href=FirebaseDatabase.getInstance().getReference("hotels");


                href.child(hname).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        Map<String,String> td=(HashMap<String, String>) dataSnapshot.getValue();



                    usn.setText(td.get("managername"));
                    hn.setText(td.get("hotelname"));
                    address.setText(td.get("hoteladdress"));
                    phoneno.setText(td.get("hotelphoneno"));
                    nearby.setText(td.get("nearby"));





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    }

