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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class manager extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    EditText managername, hotelname, hoteladdress, hotelphoneno,add_amen, nearby,ratings, description, price, numberofrooms, roomscapacity;
    private StorageReference storage;
    hotel_reg Hotel;
    private ImageView image;
    private Uri filePath;
    DatabaseReference reff;
    Button button;
    ProgressDialog pro;
    TextView hotelamenities;
    private Uri img_uri;


    List<String> ammenties;


    AdapterView.OnItemSelectedListener listener;
    private DatabaseReference db;

    private Spinner spinner;

    SaveSharedPreference session;

    private Button close, chfile, upfile;

    Dialog myDialog;

    private Boolean isupload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        isupload=false;

        spinner=findViewById(R.id.stay_spinner);

        spinner.setOnItemSelectedListener(this);


        List<String> categories = new ArrayList<String>();
        categories.add("Hotels");
        categories.add("Villas");
        categories.add("House Stays");
        categories.add("Resorts");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        final CheckBox ac,wifi,pool,tv,park;

        ac=findViewById(R.id.chkBox);
        wifi=findViewById(R.id.chkBox2);
        pool=findViewById(R.id.chkBox3);
        tv=findViewById(R.id.chkBox4);
        park=findViewById(R.id.chkBox5);










        managername = findViewById(R.id.managername);
        hotelname = findViewById(R.id.hotelname);
        hoteladdress = findViewById(R.id.hoteladdress);
        hotelphoneno = findViewById(R.id.hotelphoneno);
        nearby = findViewById(R.id.nearby);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        numberofrooms = findViewById(R.id.numberofrooms);
        roomscapacity = findViewById(R.id.roomscapacity);
        ratings=findViewById(R.id.ratings);
        Hotel = new hotel_reg();

        add_amen=findViewById(R.id.addition_amenitites);


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
                }
                else if(!(ac.isChecked() || tv.isChecked() || pool.isChecked() || wifi.isChecked() || park.isChecked())) {
                    Toast.makeText(getApplicationContext(),"please select at least one tickbox",Toast.LENGTH_SHORT).show();
                }
                else if (nearby.getText().toString().trim().isEmpty()) {
                    nearby.setError("field can't be empty!");
                } else if (price.length() >= 10) {
                    price.setError("price can't be empty!");
                } else if (description.getText().toString().trim().isEmpty()) {
                    description.setError("description field can't be empty!");
                } else if (numberofrooms.getText().toString().trim().isEmpty()) {
                    numberofrooms.setError("field can't be empty!");
                }
                else if(ratings.getText().toString().isEmpty())
                {
                    ratings.setError("field can't be empty");
                }
                else if(!isupload)
                {
                    Toast.makeText(getApplicationContext(),"please upload photos",Toast.LENGTH_SHORT).show();
                }
                else{


                    ammenties=new ArrayList<>();


                    if(ac.isChecked())
                    {
                        ammenties.add("AC");

                    }
                    if(wifi.isChecked())
                    {
                        ammenties.add("wifi");


                    }
                    if(park.isChecked())
                    {
                        ammenties.add("parking");


                    }
                    if(pool.isChecked())
                    {
                        ammenties.add("Swimming pool");


                    }
                    if(tv.isChecked())
                    {
                        ammenties.add("TV");

                    }






                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                Map<String,String> sdet=new HashMap<>(); //creating a hashmap for stays


                                sdet.put("Amenities",ammenties.toString());
                                sdet.put("Description",description.getText().toString().trim());
                                sdet.put("Location",hoteladdress.getText().toString().trim());
                                sdet.put("Name",hotelname.getText().toString().trim());
                                sdet.put("Near",nearby.getText().toString().trim());
                                sdet.put("Phone No",hotelphoneno.getText().toString());
                                sdet.put("Price_per_night",price.getText().toString().trim());
                                sdet.put("Rooms Capacity",roomscapacity.getText().toString().trim());
                                sdet.put("Number of rooms",numberofrooms.getText().toString().trim());
                                sdet.put("Ratings",ratings.getText().toString());
                                sdet.put("img_url",img_uri.toString());
                                if(!add_amen.getText().toString().isEmpty())
                                {

                                    sdet.put("additional_ammenities",add_amen.getText().toString());

                                }






                                reff.child("Sheet1").child(hotelname.getText().toString()).setValue(sdet);
                                Toast.makeText(manager.this, "Register Successful", Toast.LENGTH_LONG).show();

                                db.child("manager_det").setValue(hotelname.getText().toString());

                                pro.setMessage("Registering...");
                                pro.show();


                                startActivity(new Intent(getApplicationContext(),home_page.class));



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
            final StorageReference riversRef = storage.child("images/profile/" + hotelname.getText().toString()+ ".jpg");


            riversRef.putFile(filePath)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            isupload=true;


                            // Get a URL to the uploaded content

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                img_uri=uri;
                                }
                            });
                            progressDialog.dismiss();   
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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        if(item.equals("Hotels"))
        {
            reff = FirebaseDatabase.getInstance().getReference().child("hotels");        }
        else if(item.equals("Villas"))
        {

            reff = FirebaseDatabase.getInstance().getReference().child("villas");

        }
        else if(item.equals("House Stays"))
        {
            reff = FirebaseDatabase.getInstance().getReference().child("house stays");

        }
        else {
            reff = FirebaseDatabase.getInstance().getReference().child("resorts");


        }


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}




