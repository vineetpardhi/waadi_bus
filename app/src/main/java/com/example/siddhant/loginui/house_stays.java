package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class house_stays extends AppCompatActivity implements house_stay_adapter.HomeStaysListener,AdapterView.OnItemSelectedListener  {

    RecyclerView houselist;
    private List<stays_data> stay_val;


    AdapterView.OnItemSelectedListener itemlistener;
    private house_stay_adapter hadapter;

    private Dialog myDialog;

    private house_stay_adapter.HomeStaysListener listener;

    private DatabaseReference stref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_stays);


        stay_val=new ArrayList<stays_data>();


        myDialog=new Dialog(this);
        itemlistener=this;


        listener=this;

        houselist=findViewById(R.id.houselist);
        houselist.hasFixedSize();
        houselist.setLayoutManager(new LinearLayoutManager(this));

        stref= FirebaseDatabase.getInstance().getReference("stays").child("Sheet1");


        stref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot list:dataSnapshot.getChildren())
                {
                    Map<String,String> sd=(HashMap<String, String>) list.getValue();



                    List<String> img_url=new ArrayList<>();
                            img_url=(List)list.child("img_url").getValue();
                    stays_data sob=new stays_data(sd.get("Amenities"),sd.get("Comments"),sd.get("Coordinates"),sd.get("Description"),sd.get("Location"),sd.get("Name"),sd.get("Near"),sd.get("Price_per_night"),sd.get("Ratings"),img_url,sd.get("number_of_rooms"));



                    stay_val.add(sob);
                }




                hadapter=new house_stay_adapter(stay_val,getApplicationContext(),listener);

                houselist.setAdapter(hadapter);




                SearchView searchView=findViewById(R.id.stay_search);
                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        hadapter.getFilter().filter(s);

                        return true;
                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    public void ShowPopFilter(View v) {
        Button btnFollow,close;

        myDialog.setContentView(R.layout.filter_layout_dialog);


        close = myDialog.findViewById(R.id.clear_pop_sy_filter);


        //setting click for choose file


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();





        Spinner spinner=myDialog.findViewById(R.id.filter_spinner);

        spinner.setOnItemSelectedListener(itemlistener);


        List<String> categories = new ArrayList<String>();
        categories.add("City");
        categories.add("Beaches");
        categories.add("Forests");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    @Override
    public void OnStaysSelected(stays_data staysData)
    {
        String s=staysData.getName();
        Intent i=new Intent(getApplicationContext(),home_stays_details.class);
        i.putExtra("stay_name",s);
        startActivity(i);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
