package com.example.siddhant.loginui;

import android.app.Dialog;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class manager extends AppCompatActivity {

    EditText managername, hotelname, hoteladdress,hotelphoneno,  nearby, description, price,numberofrooms, roomscapacity;
    hotel_reg Hotel;
    DatabaseReference reff;
    Button button;
    ProgressDialog pro;
    TextView hotelamenities;


    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        managername=findViewById(R.id.managername);
        hotelname=findViewById(R.id.hotelname);
        hoteladdress=findViewById(R.id.hoteladdress);
        hotelphoneno=findViewById(R.id.hotelphoneno);
        nearby=findViewById(R.id.nearby);
        description=findViewById(R.id.description);
        price=findViewById(R.id.price);
        numberofrooms=findViewById(R.id.numberofrooms);
        roomscapacity=findViewById(R.id.roomscapacity);
        Hotel = new hotel_reg();
        reff = FirebaseDatabase.getInstance().getReference().child("hotel");
        pro = new ProgressDialog(this);
        button=findViewById(R.id.register);


        myDialog = new Dialog(this);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = managername.getText().toString();

                if (hoteladdress.getText().toString().trim().isEmpty()) {
                    hoteladdress.setError("address field can't be empty!");
                } else if (managername.getText().toString().trim().isEmpty()) {
                    managername.setError("name field can't be empty!");
                }
                else if (hotelname.getText().toString().trim().isEmpty()) {
                    hotelname.setError("name field can't be empty!");
                }else if (hotelphoneno.length()!=10){
                    hotelphoneno.setError("Invalid mobile no.");
                }else if (nearby.getText().toString().trim().isEmpty()) {
                    nearby.setError("field can't be empty!");
                }else if (price.length()>=10){
                    price.setError("price can't be empty!");
                }else if (description.getText().toString().trim().isEmpty()) {
                    description.setError("description field can't be empty!");
                }else if (numberofrooms.getText().toString().trim().isEmpty()) {
                    numberofrooms.setError("field can't be empty!");
                }

                else{
                    pro.setMessage("Registering...");
                    pro.show();


                    Hotel.setManagername(managername.getText().toString());
                    Hotel.setHotelname(hotelname.getText().toString().trim());
                    Hotel.setHoteladdress(hoteladdress.getText().toString().trim());
                    Hotel.setHotelphoneno(hotelphoneno.getText().toString().trim());
                    Hotel.setNearby(nearby.getText().toString().trim());
                    Hotel.setPrice(price.getText().toString().trim());
                    Hotel.setRoomscapacity(roomscapacity.getText().toString().trim());
                    Hotel.setDescription(description.getText().toString().trim());
                    Hotel.setNumberofrooms(numberofrooms.getText().toString().trim());



                    reff.child(managername.getText().toString().trim()).setValue(Hotel);
                    Toast.makeText(manager.this, "Resister Successful", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    public void ShowPopup(View v) {
        Button close;
        Button btnFollow;
        myDialog.setContentView(R.layout.custompop);
        close =myDialog.findViewById(R.id.clear_pop);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }



}

