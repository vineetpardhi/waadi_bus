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
import java.util.HashMap;
import java.util.Map;

public class agency_form extends AppCompatActivity {


    EditText aname,aaddr,amob;

    Button abtn;


    SaveSharedPreference session;

    FirebaseDatabase mref;
    DatabaseReference db;


    String[] vh= new String[9];
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


        session=new SaveSharedPreference(getApplicationContext());



        //register as an agency


        abtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //validation for the form
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

                    //checking the value for vehicles
                    if(f1.isChecked())
                    {
                        vh[0]="Tempo";
                    }
                    else if(f3.isChecked())
                    {
                        vh[0]="Bus";
                    }
                    else if(f3.isChecked())
                    {
                        vh[0]="Car";
                    }
                    else if(f1.isChecked() && f2.isChecked())
                    {
                        vh[0]="Tempo";
                        vh[1]="Bus";
                    }
                    else if(f1.isChecked() && f3.isChecked())
                    {
                        vh[0]="Tempo";
                        vh[1]="Car";
                    }
                    else if(f2.isChecked() && f3.isChecked())
                    {
                        vh[0]="Bus";
                        vh[1]="Car";
                    }
                    else if(f1.isChecked() && f3.isChecked() && f2.isChecked())
                    {
                        vh[0]="Tempo";
                        vh[1]="Bus";
                        vh[2]="Car";
                    }






                    mref=FirebaseDatabase.getInstance();
                    db=mref.getReference("member");

                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            HashMap<String,String> user=session.getUserDetails();

                            String usrn=user.get(SaveSharedPreference.KEY_NAME);


                            if(dataSnapshot.child(usrn).exists())
                            {



                                agency_details obj=new agency_details();
                                obj.setAgency_name(aname.getText().toString());
                                obj.setAgency_address(aaddr.getText().toString());
                                obj.setAgency_number(amob.getText().toString());
                                obj.setVh(vh);

                                db.child(usrn).child("agency").child(aname.getText().toString()).setValue(obj);

                                startActivity(new Intent(getApplicationContext(),home_page.class));
                                Toast.makeText(agency_form.this,"registered successfully",Toast.LENGTH_SHORT).show();



                            }
                            else {
                                Toast.makeText(agency_form.this,"Sorry You cannot Register",Toast.LENGTH_SHORT).show();
                            }


                            // verify that the user is logged in

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
