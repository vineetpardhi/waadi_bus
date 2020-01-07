package com.example.siddhant.loginui;

import android.app.Dialog;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class manager extends AppCompatActivity  implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 234;
    EditText managername, hotelname, hoteladdress, hotelphoneno, nearby, description, price, numberofrooms, roomscapacity;
    private StorageReference storage;
    hotel_reg Hotel;
    private ImageView image;
    private Uri filePath;
    DatabaseReference reff;
    Button button;
    ProgressDialog pro;
    TextView hotelamenities;
    Uri pdfuri;
    Uri pdf;
    private DatabaseReference db;

    SaveSharedPreference session;

    private Button close, chfile, upfile;

    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        managername = findViewById(R.id.managername);
        hotelname = findViewById(R.id.hotelname);
        hoteladdress = findViewById(R.id.hoteladdress);
        hotelphoneno = findViewById(R.id.hotelphoneno);
        nearby = findViewById(R.id.nearby);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        numberofrooms = findViewById(R.id.numberofrooms);
        roomscapacity = findViewById(R.id.roomscapacity);
        Hotel = new hotel_reg();

        pro = new ProgressDialog(this);
        button = findViewById(R.id.register);





        //getting session details if logged in

        session = new SaveSharedPreference(getApplicationContext());



        HashMap<String, String> user = session.getUserDetails();

        final String name = user.get(SaveSharedPreference.KEY_NAME);



        //database reference for member
        db=FirebaseDatabase.getInstance().getReference("member").child(name);




        //database reference for hotels
        reff = FirebaseDatabase.getInstance().getReference().child("hotels");



        myDialog = new Dialog(this);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = managername.getText().toString();

                if (hoteladdress.getText().toString().trim().isEmpty()) {
                    hoteladdress.setError("address field can't be empty!");
                } else if (managername.getText().toString().trim().isEmpty()) {
                    managername.setError("name field can't be empty!");
                } else if (hotelname.getText().toString().trim().isEmpty()) {
                    hotelname.setError("name field can't be empty!");
                } else if (hotelphoneno.length() != 10) {
                    hotelphoneno.setError("Invalid mobile no.");
                } else if (nearby.getText().toString().trim().isEmpty()) {
                    nearby.setError("field can't be empty!");
                } else if (price.length() >= 10) {
                    price.setError("price can't be empty!");
                } else if (description.getText().toString().trim().isEmpty()) {
                    description.setError("description field can't be empty!");
                } else if (numberofrooms.getText().toString().trim().isEmpty()) {
                    numberofrooms.setError("field can't be empty!");
                } else {



                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("name").getValue().equals(managername.getText().toString()))
                            {

                                Hotel.setManagername(managername.getText().toString());
                                Hotel.setHotelname(hotelname.getText().toString().trim());
                                Hotel.setHoteladdress(hoteladdress.getText().toString().trim());
                                Hotel.setHotelphoneno(hotelphoneno.getText().toString().trim());
                                Hotel.setNearby(nearby.getText().toString().trim());
                                Hotel.setPrice(price.getText().toString().trim());
                                Hotel.setRoomscapacity(roomscapacity.getText().toString().trim());
                                Hotel.setDescription(description.getText().toString().trim());
                                Hotel.setNumberofrooms(numberofrooms.getText().toString().trim());


                                reff.child(hotelname.getText().toString()).setValue(Hotel);
                                Toast.makeText(manager.this, "Register Successful", Toast.LENGTH_LONG).show();

                                db.child("manager_det").setValue(hotelname.getText().toString());

                                pro.setMessage("Registering...");
                                pro.show();


                            }
                            else {
                                Toast.makeText(getApplicationContext(),"your name is wrong please register again",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            //if user register as manager it will add to his/her member field




        });



    }


    public void ShowPopup(View v) {
        Button btnFollow;

        storage = FirebaseStorage.getInstance().getReference();
        myDialog.setContentView(R.layout.custompop);
        image = myDialog.findViewById(R.id.image_chfile);

        close = myDialog.findViewById(R.id.clear_pop);
        chfile = myDialog.findViewById(R.id.chfile);

        //setting click for choose file



        upfile = myDialog.findViewById(R.id.upfile);
        chfile.setOnClickListener(this);
        upfile.setOnClickListener(this);

        pro = new ProgressDialog(this);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {

        if (filePath != null) {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(("Uploading..."));
            progressDialog.show();
            StorageReference riversRef = storage.child("images/profile/" + hotelname.getText().toString()+ ".jpg");

            riversRef.putFile(filePath)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            // Get a URL to the uploaded content

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Upload");
                        }
                    })
            ;

        } else {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            filePath = data.getData();
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

            image.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        if(view == chfile){
            showFileChooser();

        }else if(view == upfile){
            uploadFile();

        }
    }


}




