package com.example.siddhant.loginui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class agency_form extends AppCompatActivity {


    EditText aname,aaddr,amob;

    Button abtn;

    String s;
    FirebaseDatabase mref;
    DatabaseReference db;

    CheckBox f1,f2,f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_form);




        aname=findViewById(R.id.agency_name);
        aaddr=findViewById(R.id.agency_address);
        amob=findViewById(R.id.agency_mob);

        f1=findViewById(R.id.checkBox);

        f2=findViewById(R.id.checkBox2);
        f3=findViewById(R.id.checkBox3);

        abtn=findViewById(R.id.rbtn);



        //register as an agency


        abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(aname.getText().toString().isEmpty())
                {
                    aname.setError("Please enter your agency name");
                }
                else if(aaddr.getText().toString().isEmpty())
                {
                    aaddr.setError("Please enter your agency address");
                }
                else if(amob.getText().toString().isEmpty())
                {
                    amob.setError("Please enter you agency phone");
                }
                else if( !(f1.isChecked() || f2.isChecked() || f3.isChecked()))
                {
                    Toast.makeText(agency_form.this,"please tick at least one",Toast.LENGTH_SHORT).show();
                }
                else {


                    mref=FirebaseDatabase.getInstance();
                    db=mref.getReference("member");

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            // verify that the user is logged in
                            Toast.makeText(agency_form.this,"registered successfully",Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(agency_form.this,MainActivity.class));

                            //add this data to users database


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
















    }
}
