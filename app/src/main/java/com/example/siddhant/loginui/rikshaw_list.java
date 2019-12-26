package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rikshaw_list extends AppCompatActivity {
    DatabaseReference reff;
    EditText ed;
    List<newdriver> driver1;
    List<newdriver> driver2;
    List<newdriver> driver3;
    static int acount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw_list);
        acount=0;
        final Bundle b = getIntent().getExtras();

        final String src = (b.getString("src")).toLowerCase();
        final String dest = (b.getString("dest")).toLowerCase();
        final RecyclerView list=findViewById(R.id.lists);
        list.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference().child("member");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren())
                {
                    driver1=new ArrayList<newdriver>();
                    String cust=listsnapshot.child("role").getValue(String.class);
                    if(!cust.isEmpty()&&cust.equals("Driver"))
                    {
                        String a=listsnapshot.child("working").getValue().toString().toLowerCase();
                        if(a.equals(src))
                        {
                            String b=listsnapshot.child("places").getValue().toString().toLowerCase();
                            if(b.contains(dest))
                            {
                                Map<String,String> td=(HashMap<String, String>)listsnapshot.getValue();
                                newdriver nd=new newdriver();
                                nd.setName(td.get("name"));
                                nd.setMobileno(td.get("mobileno"));
                                nd.setRno(td.get("rickshawno"));
                                driver1.add(nd);
                            }
                        }
                    }
                }
                driver2=new ArrayList<newdriver>();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren())
                {
                    String cust=listsnapshot.child("role").getValue(String.class);
                    if(!cust.isEmpty()&&cust.equals("Driver"))
                    {
                        String a=listsnapshot.child("places").getValue().toString().toLowerCase();
                        if(a.contains(src))
                        {
                            String b=listsnapshot.child("places").getValue().toString().toLowerCase();
                            if(b.contains(dest))
                            {
                                Map<String,String> td=(HashMap<String, String>)listsnapshot.getValue();
                                newdriver nd=new newdriver();
                                nd.setName(td.get("name"));
                                nd.setMobileno(td.get("mobileno"));
                                nd.setRno(td.get("rickshawno"));
                                driver1.add(nd);
                            }
                        }
                    }
                }
                driver3=new ArrayList<newdriver>();
                for(DataSnapshot listsnapshot:dataSnapshot.getChildren())
                {
                    String cust=listsnapshot.child("role").getValue(String.class);
                    if(!cust.isEmpty()&&cust.equals("Driver"))
                    {
                        String a=listsnapshot.child("working").getValue().toString().toLowerCase();
                        if(a.equals(dest))
                        {
                            String b=listsnapshot.child("places").getValue().toString().toLowerCase();
                            if(b.contains(src))
                            {
                                Map<String,String> td=(HashMap<String, String>)listsnapshot.getValue();
                                newdriver nd=new newdriver();
                                nd.setName(td.get("name"));
                                nd.setMobileno(td.get("mobileno"));
                                nd.setRno(td.get("rickshawno"));
                                driver1.add(nd);
                            }
                        }
                    }
                }
                if(driver1.size()+driver2.size()+driver3.size()>0) {
                    newdriver ng[] = new newdriver[driver1.size() + driver2.size() + driver3.size()];
                    for (newdriver m : driver1) {
                        ng[acount] = new newdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }

                    for (newdriver m : driver2) {
                        ng[acount] = new newdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }

                    for (newdriver m : driver3) {
                        ng[acount] = new newdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }
                    list.setAdapter(new Adapter(ng));
                }
                else
                {


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}