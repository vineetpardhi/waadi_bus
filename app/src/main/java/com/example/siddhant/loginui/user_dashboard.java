package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class user_dashboard extends AppCompatActivity {


    private TextView usn,nm,eml,addr,ph;



    EditText ed_email;

    EditText ed_addr;

    Dialog myDialog,mydialog2;



    private SaveSharedPreference session;

    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);




    //getting textview for displaying user details
        usn=findViewById(R.id.usn);
        nm=findViewById(R.id.Nm);
        eml=findViewById(R.id.eml);
        addr=findViewById(R.id.addrs);
        ph=findViewById(R.id.phone_no);










        myDialog = new Dialog(this);        //editable popup for email

        mydialog2=new Dialog(this);         //editable popup for address






        session=new SaveSharedPreference(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);


        db= FirebaseDatabase.getInstance().getReference("member").child(name);


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    for(DataSnapshot list:dataSnapshot.getChildren())
                    {
                        Map<String,String> td=(HashMap<String, String>) dataSnapshot.getValue();

                        usn.setText(td.get("username"));
                        nm.setText(td.get("name"));
                        eml.setText(td.get("email"));
                        addr.setText(td.get("address"));
                        ph.setText(td.get("mobileno"));








                    }









                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });













    }

    //popup functions for email


    public void ShowPopup1(View v) {
        Button close;
        Button btnFollow;
        myDialog.setContentView(R.layout.editpop);
        close =myDialog.findViewById(R.id.clear_pop2);





        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();




        Button submit_em;


        ed_email=myDialog.findViewById(R.id.ed_em);

        submit_em=myDialog.findViewById(R.id.submit_email);


        submit_em.setOnClickListener(new View.OnClickListener() {       //setting listener for submit button
            @Override
            public void onClick(View view) {


                String email = ed_email.getText().toString();
                if(email.isEmpty())     //if email field is empty
                {
                    ed_email.setError("Please enter email");
                }
                else
                {

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                db.child("email").setValue(ed_email.getText().toString());

                                eml.setText(ed_email.getText().toString());

                                Toast.makeText(getApplicationContext(),"email changed to:"+ed_email.getText().toString(),Toast.LENGTH_SHORT).show();

                                myDialog.dismiss();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });







    }


    //popup for address

    public void ShowPopup2(View v) {
        Button close;
        Button btnFollow;


        Button submit_addr;




        mydialog2.setContentView(R.layout.edit_addr_pop);
        close =mydialog2.findViewById(R.id.clear_pop3);



        ed_addr=mydialog2.findViewById(R.id.ed_addr);
        submit_addr=mydialog2.findViewById(R.id.submit_addr);






        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog2.dismiss();
            }
        });
        mydialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog2.show();





        submit_addr.setOnClickListener(new View.OnClickListener() {       //setting listener for submit button
            @Override
            public void onClick(View view) {

            String address=ed_addr.getText().toString();

                if(address.isEmpty())     //if email field is empty
                {
                    ed_addr.setError("please enter address");
                }
                else
                {

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                db.child("address").setValue(ed_addr.getText().toString());

                                addr.setText(ed_addr.getText().toString());

                                Toast.makeText(getApplicationContext(),"Address changed to:"+ed_addr.getText().toString(),Toast.LENGTH_SHORT).show();
                                mydialog2. dismiss();
                            }

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
