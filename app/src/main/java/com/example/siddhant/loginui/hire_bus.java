package com.example.siddhant.loginui;

import android.content.Intent;
import android.media.MediaCas;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hire_bus extends AppCompatActivity {


    private List<quotation> values;
    private SaveSharedPreference session;


    RecyclerView triplist;


    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_bus);

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

    public void on_book_prvt(View view) {

        startActivity(new Intent(getApplicationContext(),bus_book_dt.class));
    }

}
