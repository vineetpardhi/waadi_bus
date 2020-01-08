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

public class manager_dashboard extends AppCompatActivity {

    private TextView usn, hn, phoneno, address, nearby;

    private Button agnbtn;
    Dialog myDialog3, mydialog4,mydialog5;
    public String hname;
    private SaveSharedPreference session;
    private String name;
    private DatabaseReference db;
    DatabaseReference href;
    EditText ed_hname;

    EditText ed_haddress;
    EditText ed_hphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        usn = findViewById(R.id.usmn);
        hn = findViewById(R.id.hn);
        phoneno = findViewById(R.id.phoneno);
        address = findViewById(R.id.hotel_addr);
        nearby = findViewById(R.id.nearby);


        mydialog4 = new Dialog(this);


        myDialog3 = new Dialog(this);
        mydialog5 = new Dialog(this);


        session = new SaveSharedPreference(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        name = user.get(SaveSharedPreference.KEY_NAME);


        db = FirebaseDatabase.getInstance().getReference("member").child(name);


        db.addValueEventListener(new ValueEventListener() {     //getting hotel name from member field
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                Map<String, String> ud = (HashMap<String, String>) dataSnapshot.getValue();

                hname = ud.get("manager_det");


                //hotel reference
                href = FirebaseDatabase.getInstance().getReference("hotels");


                href.child(hname).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        Map<String, String> td = (HashMap<String, String>) dataSnapshot.getValue();


                        usn.setText(td.get("managername"));
                        hn.setText(td.get("hotelname"));
                        address.setText(td.get("hoteladdress"));
                        phoneno.setText(td.get("hotelphoneno"));
                        nearby.setText(td.get("nearby"));
                        //editable popup for email


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void ShowPopup3(View v) {
        Button close;
        Button btnFollow;
        myDialog3.setContentView(R.layout.hotelnamepopup);
        close = myDialog3.findViewById(R.id.clear_pop6);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog3.dismiss();
            }
        });
        myDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog3.show();


        Button submit_hn;


        ed_hname = myDialog3.findViewById(R.id.ed_hname);

        submit_hn = myDialog3.findViewById(R.id.submit_hname);


        submit_hn.setOnClickListener(new View.OnClickListener() {       //setting listener for submit button
            @Override
            public void onClick(View view) {


                String name = ed_hname.getText().toString();
                if (name.isEmpty())     //if email field is empty
                {
                    ed_hname.setError("Please enter hotel  name");
                } else {

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                db.child("hotel name").setValue(ed_hname.getText().toString());

                                ed_hname.setText(ed_hname.getText().toString());

                                Toast.makeText(getApplicationContext(), "hotel name changed to:" + ed_hname.getText().toString(), Toast.LENGTH_SHORT).show();

                                myDialog3.dismiss();

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

    public void ShowPopup4(View v) {
        Button close;
        Button btnFollow;


        Button submit_addr;


        mydialog4.setContentView(R.layout.hoteladdresspopup);
        close = mydialog4.findViewById(R.id.clear_pop7);


        ed_haddress = mydialog4.findViewById(R.id.ed_haddress);
        submit_addr = mydialog4.findViewById(R.id.submit_haddress);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog4.dismiss();
            }
        });
        mydialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog4.show();


        submit_addr.setOnClickListener(new View.OnClickListener() {       //setting listener for submit button
            @Override
            public void onClick(View view) {

                String address = ed_haddress.getText().toString();

                if (address.isEmpty())     //if email field is empty
                {
                    ed_haddress.setError("please enter address");
                } else {

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                db.child("address").setValue(ed_haddress.getText().toString());

                                ed_haddress.setText(ed_haddress.getText().toString());

                                Toast.makeText(getApplicationContext(), "Address changed to:" + ed_haddress.getText().toString(), Toast.LENGTH_SHORT).show();
                                mydialog4.dismiss();
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
    public void ShowPopup5(View v) {
        Button close;
        Button btnFollow;


        Button submit_hphoneno;


        mydialog5.setContentView(R.layout.phonenopopup);
        close = mydialog5.findViewById(R.id.clear_pop8);


        ed_hphone = mydialog5.findViewById(R.id.ed_hphone);
        submit_hphoneno = mydialog5.findViewById(R.id.submit_hphoneno);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog5.dismiss();
            }
        });
        mydialog5.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog5.show();


        submit_hphoneno.setOnClickListener(new View.OnClickListener() {       //setting listener for submit button
            @Override
            public void onClick(View view) {

                String phone = ed_hphone.getText().toString();

                if (phone.isEmpty())     //if email field is empty
                {
                    ed_hphone.setError("please enter phone no");
                } else {

                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                db.child("phone").setValue(ed_hphone.getText().toString());

                                ed_hphone.setText(ed_hphone.getText().toString());

                                Toast.makeText(getApplicationContext(), "Phone changed to:" + ed_hphone.getText().toString(), Toast.LENGTH_SHORT).show();
                                mydialog5.dismiss();
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
