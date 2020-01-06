package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bus_root extends AppCompatActivity {

    DatabaseReference db;

    String rt_arr[];
    RecyclerView routelist;
    List<routes_list> bnlist;
    private String bn;
    private String src;
    private String des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_root);



        //getting bus number from clicked bus

        Bundle b = getIntent().getExtras();

        bn =b.getString("bus_number");
        src=b.getString("src");
        des=b.getString("des");















        final List<String> bnumber;
        bnumber=new ArrayList<String>();


        bnlist=new ArrayList<>();
        routelist=findViewById(R.id.rt_lists);
        routelist.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseDatabase.getInstance().getReference("Routes");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                for(DataSnapshot list:dataSnapshot.getChildren())
                {

                    routes_list lst=new routes_list(list.getKey(),list.getValue().toString());
                    bnlist.add(lst);
                }



                for(routes_list e:bnlist)
                {

                    if(e.getStation_number().contains(bn) ){
                        bnumber.add(e.getStation_name());

                    }

                }














                rt_arr=new String[bnumber.size()];
                rt_arr=bnumber.toArray(rt_arr);







                routelist.setAdapter(new route_adapter(rt_arr,src,des));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
