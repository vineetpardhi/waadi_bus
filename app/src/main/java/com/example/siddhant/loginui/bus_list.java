package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.android.SqlPersistenceStorageEngine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bus_list extends AppCompatActivity {

    FirebaseDatabase mref;
    DatabaseReference db;

    List<Bus_data> values;

    RecyclerView buslist;
    Bus_data barr[];

    CardView cv;



    private bus_adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bus_list);

        Bundle b = getIntent().getExtras();

        String[] arr = b.getStringArray("myarr");



        final Toolbar mToolbar = findViewById(R.id.toolbar_buss);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        buslist=findViewById(R.id.list);
        buslist.hasFixedSize();
        buslist.setLayoutManager(new LinearLayoutManager(this));



        final String src = arr[0];
        final String des = arr[1];


        values=new ArrayList<Bus_data>();





        db = FirebaseDatabase.getInstance().getReference("Buses");

        db.keepSynced(true);


        db.child(src).child(des).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {


                    Map<String,String> td=(HashMap<String, String>) listsnapshot.getValue();

                    Bus_data lst=new Bus_data(td.get("Name:"),td.get("Time:"),td.get("Number of stops:"),td.get("Bus Number:"));

                    values.add(lst);

                }


                //storing data into bus_data array


                barr=new Bus_data[values.size()];
                barr=values.toArray(barr);





                mAdapter=new bus_adapter(barr,src,des,getApplicationContext());
                buslist.setAdapter(mAdapter);




                SearchView searchView=findViewById(R.id.sbus);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mAdapter.getFilter().filter(newText);
                        return false;
                    }
                });




                mAdapter.setOnItemClickListener(new bus_adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {




                        mAdapter.changetext(barr[position]);
                        mAdapter.notifyDataSetChanged();


                    }
                });
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


                Toast.makeText(getApplicationContext(),"Error Connecting,Try Again with network connected",Toast.LENGTH_SHORT).show();
            }
        });









    }

    public void bus_route(View view) {
        startActivity(new Intent(getApplicationContext(), bus_root.class));

    }







}




