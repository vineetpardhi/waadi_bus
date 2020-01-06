package com.example.siddhant.loginui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Random;

public class confirm_book extends AppCompatActivity {


    CheckBox ac,non_ac;
    private Boolean flag;


    SaveSharedPreference session;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_book);

        final Bundle b=getIntent().getExtras();

        final String dt_arr[]=b.getStringArray("dtarr");


        session= new SaveSharedPreference(getApplicationContext());




        ac=findViewById(R.id.ac_check);

        final CheckBox non_ac=findViewById(R.id.nonac_check);


        final EditText num_p=findViewById(R.id.num_people);


        Button cbtn=findViewById(R.id.confirm_bk);

        final Random rnd=new Random();

        String bustype;
        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(ac.isChecked() || non_ac.isChecked()))
                {
                    Toast.makeText(confirm_book.this,"Pleae enter select one",Toast.LENGTH_SHORT).show();
                }
                else if(num_p.getText().toString().isEmpty())
                {
                    num_p.setError("Please enter no. of people");
                }
                else
                {
                    //storing the quotation
                    db=FirebaseDatabase.getInstance().getReference("member");


                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String bustype;

                            //option for selecting ac/nonac
                            if(ac.isChecked())
                            {
                                bustype="AC";
                            }
                            else {
                                bustype="NON AC";

                            }



                            HashMap<String,String> user=session.getUserDetails();

                            String usrn=user.get(SaveSharedPreference.KEY_NAME);
                            quotation qobj=new quotation();

                            qobj.setSource(dt_arr[0]);
                            qobj.setDestination(dt_arr[1]);
                            qobj.setDt1(dt_arr[2]);
                            qobj.setDt2(dt_arr[3]);
                            qobj.setBustype(bustype);
                            qobj.setNo_of_seates(Integer.parseInt(num_p.getText().toString()));
                            qobj.setTrip(dt_arr[4]);







                            db.child(usrn).child("trips").child(dt_arr[0].concat(dt_arr[1]).concat(String.valueOf(rnd.nextInt(1000)))).setValue(qobj);


                            flag=true;

                            Toast.makeText(confirm_book.this,"Booking Confirmed",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),home_page.class));



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });








    }


    @Override
    public void onBackPressed() {

        if(flag) {
            Intent i = new Intent(getApplicationContext(), home_page.class);
            startActivity(i);
        }
    }


}
