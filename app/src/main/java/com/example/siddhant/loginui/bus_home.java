package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bus_home extends AppCompatActivity {
    FirebaseDatabase mref;
    DatabaseReference db;

    AutoCompleteTextView t1;
    AutoCompleteTextView t2;
    Button sbtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_home);



        sbtn=findViewById(R.id.go_bus);
        t1=findViewById(R.id.src);
        t2=findViewById(R.id.des);










        final ArrayAdapter<String> adapter1= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);





        //Auto complete code for bus depot.



        db=FirebaseDatabase.getInstance().getReference();

        db.child("depot").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //getting each data from database
                for (DataSnapshot suggestionSnapshot:dataSnapshot.getChildren())
                {
                    String suggestion=suggestionSnapshot.child("depo_name").getValue(String.class);
                    adapter1.add(suggestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        t1.setAdapter(adapter1);
        t2.setAdapter(adapter1);









        //go bus button event listener
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.getText().toString().isEmpty())
                {
                    t1.setError("Please Enter Source");

                }
                else if(t2.getText().toString().isEmpty())
                {
                    t2.setError("Please Enter Source");
                }
                else if (t1.getText().toString().equals(t2.getText().toString()))
                {
                    Toast.makeText(bus_home.this,"source and Destination Should not be same",Toast.LENGTH_SHORT).show();

                }
                else {
                    String[] name={t1.getText().toString(),t2.getText().toString()};
                    Intent i = new Intent(getApplicationContext(), bus_list.class);
                    i.putExtra("myarr",name);
                    startActivity(i);

                }



            }
        });


    }






    public void on_depo(View view) {
        startActivity(new Intent(getApplicationContext(),bus_depo.class));
    }
}
