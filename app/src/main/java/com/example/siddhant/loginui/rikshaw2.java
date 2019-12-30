package com.example.siddhant.loginui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class rikshaw2 extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView image;
    private Button uploadphoto;
    private Button chooesphoto;
    private Uri filePath;
    private StorageReference storage;
    List<String> strings;
    Calendar c;
    DatePickerDialog dp;

    EditText name, address, mobileno, rtobranch, rno,rtoaddress, dateofregistration,working,username,password,cpassword,email;
    Button button,addInput,btn;
    Driver drive;
    DatabaseReference reff;
    ProgressDialog pro;
    static int count=0;
    static List<EditText> allEds=new ArrayList<EditText>();
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw2);
        storage = FirebaseStorage.getInstance().getReference();
        image = (ImageView) findViewById(R.id.image);
        uploadphoto = (Button) findViewById(R.id.photo);
        chooesphoto = (Button) findViewById(R.id.photo1);
        name = (EditText) findViewById(R.id.source);
        address = (EditText) findViewById(R.id.address);
        mobileno = (EditText) findViewById(R.id.mobileno);
        rtobranch = (EditText) findViewById(R.id.rtobranch);
        rtoaddress = (EditText) findViewById(R.id.rtoaddress);
        dateofregistration = (EditText) findViewById(R.id.dateofregistration);
        button = (Button) findViewById(R.id.Register);
        addInput = (Button) findViewById(R.id.add);
        btn=(Button)findViewById(R.id.dpbtn);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        cpassword=(EditText)findViewById(R.id.cpassword);
        email=(EditText)findViewById(R.id.email);
        rno=(EditText)findViewById(R.id.rno);

        working= (EditText) findViewById(R.id.working);

        pro = new ProgressDialog(this);
        drive = new Driver();
        reff = FirebaseDatabase.getInstance().getReference().child("member");

        uploadphoto.setOnClickListener(this);
        chooesphoto.setOnClickListener(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day= c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);



                dp=new DatePickerDialog(rikshaw2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker , int myear, int ymonth, int dayOfMonth) {

                        dateofregistration.setText(dayOfMonth+"/"+ (ymonth+1) + "/"+myear);

                    }
                },day,month,year);
                dp.show();
            }
        } );

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = name.getText().toString();
                if (address.getText().toString().trim().isEmpty()) {
                    address.setError("email field can't be empty!");
                } else if (name.getText().toString().trim().isEmpty()) {
                    name.setError("name field can't be empty!");
                } else if (mobileno.length()!=10){
                    mobileno.setError("Invalid mobile no.");
                }
                else if (rtobranch.getText().toString().trim().isEmpty()) {
                    rtobranch.setError("rtobranch field can't be empty");
                } else if (rtoaddress.getText().toString().trim().isEmpty()) {
                    rtoaddress.setError("rtoaddress field can't be empty");
                } else if (dateofregistration.getText().toString().trim().isEmpty()) {
                    dateofregistration.setError("Date of registration field can't be empty");
                }
                else if(!password.getText().toString().equals(cpassword.getText().toString()))
                {
                    cpassword.setError("Password do not match");
                }
                else if(!isEmailValid(email.getText().toString().trim()))
                {
                    email.setError("Not a valid email");
                }
                else if(username.getText().toString().length()>=15)
                {
                    username.setError("Should be less than 15");
                }
                else if(username.getText().toString().length()<3) {
                    username.setError("Username should be greater than 3");
                }
                else if(password.getText().toString().length()<6)
                {
                    password.setError("Password should be greater than 5");
                }
                else{
                    pro.setMessage("Registering...");
                    pro.show();
                    drive.setRickshawno(rno.getText().toString().trim());
                    drive.setUsername(username.getText().toString().trim());
                    drive.setRole("Driver");
                    drive.setPassword(password.getText().toString());
                    drive.setEmail(email.getText().toString().trim());
                    drive.setWorking(working.getText().toString());
                    drive.setName(name.getText().toString().trim());
                    drive.setAddress(address.getText().toString().trim());
                    drive.setMobileno(mobileno.getText().toString().trim());
                    drive.setRtobranch(rtobranch.getText().toString().trim());
                    drive.setRtoaddress(rtoaddress.getText().toString().trim());
                    drive.setDateofregistration(dateofregistration.getText().toString().trim());
                    String listString = "";
                    if(strings.size()!=0){
                        for (String s : strings)
                        {
                            listString += s + ", ";
                        }
                    }
                    //String str = String.join(",", strings);
                    drive.setplaces(listString);

                    reff.child(username.getText().toString().trim()).setValue(drive);
                    Toast.makeText(rikshaw2.this, "Successfully Signed in", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
            StorageReference riversRef = storage.child("images/profile/"+name.getText().toString()+".jpg");

            riversRef.putFile(filePath)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"File Uploaded" , Toast.LENGTH_LONG).show();
                            // Get a URL to the uploaded content

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),exception.getMessage() , Toast.LENGTH_LONG).show();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int)progress)+"% Upload");
                        }
                    })
            ;

        }else
        {

        }
    }


    @Override
    public void onClick(View view) {
        if(view == chooesphoto){
            showFileChooser();

        }else if(view == uploadphoto){
            uploadFile();

        }
    }

    public void addone(View view) {
            LinearLayout ly=findViewById(R.id.LinearLayout);

            ed=new EditText(rikshaw2.this);
            allEds.add(ed);
            ly.addView(ed);
            this.count=this.count+1;
            ed.setId(this.count);

            ed.setHint("Place "+this.count);
    }

    public void onSave(View view) {
          //Toast.makeText(getApplicationContext(),String.valueOf(this.count),Toast.LENGTH_SHORT).show();

        strings=new ArrayList<>();
        for(int i=0;i<allEds.size();i++)
          {
              strings.add(allEds.get(i).getText().toString());
             // Toast.makeText(getApplicationContext(),strings[i] , Toast.LENGTH_LONG).show();
          }
    }
}