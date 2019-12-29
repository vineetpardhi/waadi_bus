package com.example.siddhant.loginui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class rikshaw1 extends AppCompatActivity {

    FirebaseDatabase mref;
    DatabaseReference db;
    boolean srcbool=false;
    boolean destbool=false;

    AutoCompleteTextView t1;
    AutoCompleteTextView t2;
    Button sbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw1);
        sbtn=findViewById(R.id.go_bus);
        t1=findViewById(R.id.src);
        t2=findViewById(R.id.des);

        final ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        //Toast.makeText(getApplicationContext(),String.valueOf(isNetworkAvailable()),Toast.LENGTH_SHORT).show();
        db=FirebaseDatabase.getInstance().getReference();
        db.keepSynced(true);
        db.child("Sheet1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //getting each data from database
                for (DataSnapshot suggestionSnapshot:dataSnapshot.getChildren())
                {
                    String suggestion=suggestionSnapshot.child("Locality").getValue(String.class);
                    adapter1.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        t1.setAdapter(adapter1);
        t2.setAdapter(adapter1);

        //go bus button event listener
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String src=t1.getText().toString();
                final String dest=t2.getText().toString();

                if(src.isEmpty()&&dest.isEmpty())
                {
                    t1.setError("Please Enter Source");
                    t2.setError("Please Enter Destination");
                }
                else if(t1.getText().toString().isEmpty())
                {
                    t1.setError("Please Enter Source");
                }
                else if(t2.getText().toString().isEmpty())
                {
                    t2.setError("Please Enter Destination");
                }
                else if (src.equals(dest))
                {
                    t1.setError("Cannot be same as destination");
                    t2.setError("Cannot be same as source");
                    Toast.makeText(rikshaw1.this,"source and Destination cannot be same",Toast.LENGTH_SHORT).show();
                }
                else{

                        Intent i = new Intent(getApplicationContext(), rikshaw_list.class);
                        i.putExtra("src",t1.getText().toString());
                        i.putExtra("dest",t2.getText().toString());
                        startActivity(i);
                }
            }
        });
    }


}
