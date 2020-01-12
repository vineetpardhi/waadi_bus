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
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class house_stays extends AppCompatActivity implements house_stay_adapter.HomeStaysListener,AdapterView.OnItemSelectedListener  {

    RecyclerView houselist;
    private List<stays_data> stay_val;

    private String near,ppn;
    private Integer filter_rating;
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

        near=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void ShowPopFilter(View v) {
        Button apply_filter,close;

        myDialog.setContentView(R.layout.filter_layout_dialog);



        close = myDialog.findViewById(R.id.clear_pop_sy_filter);


        RatingBar rb=myDialog.findViewById(R.id.get_filter_rate);

        filter_rating=rb.getNumStars();

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




        SeekBar sb=myDialog.findViewById(R.id.seekBar);
        sb.setMax(5000);
        sb.setProgress(100);




        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                TextView tv=myDialog.findViewById(R.id.tooltext);
                tv.setText(String.valueOf(progress));
                ppn=tv.getText().toString();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        apply_filter=myDialog.findViewById(R.id.sb_filter);

        apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Apply_filter();
            }
        });


    }

    public void Apply_filter()
    {


        if(ppn=="0" || Integer.parseInt(ppn)<2000)
        {

            Toast.makeText(getApplicationContext(),"please enter price greater than 2000",Toast.LENGTH_SHORT).show();
        }
        else if(filter_rating<3)
        {
            Toast.makeText(getApplicationContext(),"hotel available only of 3 stars",Toast.LENGTH_SHORT).show();
        }
        else{





            final DatabaseReference ftref;
            ftref=FirebaseDatabase.getInstance().getReference("stays").child("Sheet1");


            ftref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<stays_data> filter_list=null;
                    Map sd=null;
                    for(DataSnapshot list:dataSnapshot.getChildren())
                    {

                        if(list.child("Near").getValue().toString().toLowerCase().equals(near.toLowerCase()) )
                        {
                            sd=(HashMap) list.getValue();





                        }
                        if(list.child("Ratings").getValue().toString().equals(String.valueOf(filter_rating)))
                        {
                            sd=(HashMap) list.getValue();


                        }
                        if(Integer.parseInt(list.child("Price_per_night").getValue().toString()) <=Integer.parseInt(ppn))
                        {
                            sd=(HashMap) list.getValue();


                        }

                    }




                    if(sd!=null)
                    {

                        Toast.makeText(getApplicationContext(),sd.toString(),Toast.LENGTH_SHORT).show();
//
//                        stay_val.addAll(filter_list);
//                        houselist.setAdapter(hadapter);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"sorry no results",Toast.LENGTH_SHORT).show();
                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





        }





    }



}
