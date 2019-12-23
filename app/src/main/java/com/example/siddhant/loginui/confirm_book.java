package com.example.siddhant.loginui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class confirm_book extends AppCompatActivity {


    CheckBox ac,non_ac;


    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_book);

        final Bundle b=getIntent().getExtras();

        final String dt_arr[]=b.getStringArray("dtarr");


        ac=findViewById(R.id.ac_check);

        final CheckBox non_ac=findViewById(R.id.nonac_check);


        final EditText num_p=findViewById(R.id.num_people);


        Button cbtn=findViewById(R.id.confirm_bk);

        String bustype;
        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ac.isChecked() || !non_ac.isChecked())
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
                            quotation q1=new quotation(dt_arr[0],dt_arr[1],dt_arr[2],dt_arr[3],bustype,Integer.parseInt(num_p.getText().toString()));

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
