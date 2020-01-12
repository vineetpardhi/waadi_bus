package com.example.siddhant.loginui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class hire_bus extends AppCompatActivity {


    private List<quotation> values;
    private SaveSharedPreference session;





    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_bus);

        session= new SaveSharedPreference(getApplicationContext());
        HashMap<String,String> user= session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);




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
