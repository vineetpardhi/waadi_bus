package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bus_list extends AppCompatActivity {

    FirebaseDatabase mref;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        Bundle b=getIntent().getExtras();

        String[] arr=b.getStringArray("myarr");



        String src=arr[0];
        String des=arr[1];

        check_buses(src,des);
    }

    public void bus_route(View view) {
        startActivity(new Intent(getApplicationContext(),bus_root.class));

    }

    private  void  check_buses(String source,String destination)
    {

        db=FirebaseDatabase.getInstance().getReference();
        db.child("routes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot listsnapshot:dataSnapshot.getChildren())
                {
                    String busname=listsnapshot.child("bus_name").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
