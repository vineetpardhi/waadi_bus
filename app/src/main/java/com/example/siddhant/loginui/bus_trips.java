package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bus_trips extends AppCompatActivity {



    private List<quotation> values;
    private SaveSharedPreference session;


    RecyclerView triplist;


    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_trips);


        session= new SaveSharedPreference(getApplicationContext());
        HashMap<String,String> user= session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);









        triplist=findViewById(R.id.tlist);
        triplist.setLayoutManager(new LinearLayoutManager(this));


        db= FirebaseDatabase.getInstance().getReference("member").child(name);




        values=new ArrayList<quotation>();

        db=FirebaseDatabase.getInstance().getReference("member");
        db.child(name).child("trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot list:dataSnapshot.getChildren())
                {

                    values.add(list.getValue(quotation.class));

                }


                quotation qarr[];

                qarr= new quotation[values.size()];

                qarr=values.toArray(qarr);



                triplist.setAdapter(new trip_adapter(qarr));





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
