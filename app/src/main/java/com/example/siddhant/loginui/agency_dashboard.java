package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class agency_dashboard extends AppCompatActivity {


    private List<agency_details> values;
    private TextView agnname,agnaddr,agnmob,sv1,sv2,sv3;
    private DatabaseReference db;
    private SaveSharedPreference session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_dashboard);

        agnname=findViewById(R.id.agn_name);
        agnaddr=findViewById(R.id.agn_addr);
        agnmob=findViewById(R.id.agn_mob);

        session=new SaveSharedPreference(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);


        //getting services for agency

        sv1=findViewById(R.id.sv1);
        sv2=findViewById(R.id.sv2);
        sv3=findViewById(R.id.sv3);






        values=new ArrayList<>();

        db= FirebaseDatabase.getInstance().getReference("member");
        db.child(name).child("agency").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    for (DataSnapshot list : dataSnapshot.getChildren()) {
                        Map<String, String> td = (HashMap<String, String>) list.getValue();

                        agnname.setText(td.get("agency_name"));
                        agnaddr.setText(td.get("agency_address"));
                        agnmob.setText(td.get("agency_number"));


                        agency_details nob = new agency_details();


                        nob.setVh((List) list.child("vh").getValue());


                        values.add(nob);

                    }

                    List<String> v = new ArrayList<String>(values.size());

                    for (agency_details e : values) {

                        if (e.getVh().size() == 1) {
                            v.addAll(e.getVh());
                            sv1.setVisibility(View.GONE);
                            sv2.setText(v.get(0));
                            sv3.setVisibility(View.GONE);
                        } else if (e.getVh().size() == 2) {
                            v.addAll(e.getVh());
                            sv1.setText(v.get(0));
                            sv2.setText(v.get(1));
                            sv3.setVisibility(View.GONE);
                        } else if (e.getVh().size() == 3) {
                            v.addAll(e.getVh());
                            sv1.setText(v.get(0));
                            sv2.setText(v.get(1));
                            sv3.setText(v.get(2));
                        }
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Please register as agency",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }
}
