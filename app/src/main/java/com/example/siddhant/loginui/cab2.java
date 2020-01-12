package com.example.siddhant.loginui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class cab2 extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView image;
    private Button uploadphoto;
    private Button chooesphoto;
    private Button chooesfile3;
    private Button uploadidproof1;
    private Button chooesfile4;
    private Button rtoverification1;
    private Uri filePath;
    private LinearLayout layout2;
    private CheckBox chkBox3;
    private  LinearLayout layout3;
    private CheckBox chkBox4;
    Uri pdfuri;
    Uri pdf;
    ProgressDialog progressDialog;
    private StorageReference storage;
    private ImageView resultView;
    List<String> strings;
    Calendar c;
    DatePickerDialog dp;

    EditText name1,name, address1, mobileno1, rtobranch1, rtoaddress1, dateofregistration1,working1,username1,password1,cpassword1,email1,agencyname1,cno;
    Button register1,addInput,btn;
    TextView notification4;
    TextView notification5;


    cab Cab;
    DatabaseReference reff;
    FirebaseDatabase database;
    ProgressDialog pro;
    static int count=0;
    static List<EditText> allEds=new ArrayList<EditText>();
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab2);
        storage = FirebaseStorage.getInstance().getReference();
        image = (ImageView) findViewById(R.id.image1);
        uploadphoto = (Button) findViewById(R.id.photo7);
        chooesphoto = (Button) findViewById(R.id.photo6);
        chooesfile3 = (Button) findViewById(R.id.chooesfile3);
        uploadidproof1=(Button)findViewById(R.id.uploadidproof1);
        notification4=findViewById(R.id.notification4);
        chooesfile4 = (Button) findViewById(R.id.chooesfile4);
        cno=findViewById(R.id.cno);
        rtoverification1=(Button)findViewById(R.id.rtoverification1);
        notification5=findViewById(R.id.notification5);
        agencyname1=findViewById(R.id.agencyname1);
        name1= (EditText) findViewById(R.id.name1);
        address1 = (EditText) findViewById(R.id.address1);
        mobileno1 = (EditText) findViewById(R.id.mobileno1);
        rtobranch1 = (EditText) findViewById(R.id.rtobranch1);
        rtoaddress1 = (EditText) findViewById(R.id.rtoaddress1);
        dateofregistration1 = (EditText) findViewById(R.id.dateofregistration1);
        register1 = (Button) findViewById(R.id.register1);
        addInput = (Button) findViewById(R.id.add);
        btn=(Button)findViewById(R.id.dpbtn1);
        username1=(EditText)findViewById(R.id.username1);
        password1=(EditText)findViewById(R.id.password1);
        cpassword1=(EditText)findViewById(R.id.cpassword1);
        email1=(EditText)findViewById(R.id.email1);
        resultView=findViewById(R.id.image);
        name1 = (EditText) findViewById(R.id.name1);

        working1= (EditText) findViewById(R.id.working1);
        chkBox3 = (CheckBox) findViewById(R.id.chkBox3);
        layout2= (LinearLayout) findViewById(R.id.layout2);
        chkBox4 = (CheckBox) findViewById(R.id.chkBox4);
        layout3 = (LinearLayout) findViewById(R.id.layout3);


        pro = new ProgressDialog(this);
        Cab = new cab();
        reff = FirebaseDatabase.getInstance().getReference().child("cab");

        uploadphoto.setOnClickListener(this);
        chooesphoto.setOnClickListener(this);

        chkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                {
                    layout2.setVisibility(View.VISIBLE);
                    chkBox3.setText("Agency");
                }
                else
                {
                    layout2.setVisibility(View.GONE);
                    chkBox3.setText("Agency");
                }
            }


        });
        chkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                {
                    layout3.setVisibility(View.VISIBLE);
                    chkBox4.setText("Owner");
                }
                else
                {
                    layout3.setVisibility(View.GONE);
                    chkBox4.setText("Owner");
                }
            }


        });

        chooesfile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(cab2.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(cab2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        });

        rtoverification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdf!=null){
                    uploadFile(pdf);
                }else {
                    Toast.makeText(cab2.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }
            }
            private void uploadFile(Uri pdf) {
                progressDialog = new ProgressDialog(cab2.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("uploadingFile..");
                progressDialog.setProgress(0);
                progressDialog.show();

                final String fileName = System.currentTimeMillis() + "";
                FirebaseStorage storageReference = storage.getStorage();
                storage.child("Uploads").child(fileName).putFile(pdf)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri downloadUrl = uri;

                                        DatabaseReference reference = database.getInstance().getReference();
                                        reference.child(fileName).setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(cab2.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(cab2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(cab2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int currentprogress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentprogress);
                    }
                });
            }
        });

        chooesfile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(cab2.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf1();
                }
                else
                    ActivityCompat.requestPermissions(cab2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }


        });


        uploadidproof1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfuri!=null){
                    uploadFile(pdfuri);
                }else {
                    Toast.makeText(cab2.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }

            }




            private void uploadFile(Uri pdfuri) {
                progressDialog = new ProgressDialog(cab2.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("uploadingFile..");
                progressDialog.setProgress(0);
                progressDialog.show();

                final String fileName = System.currentTimeMillis() + "";
                FirebaseStorage storageReference = storage.getStorage();
                storage.child("Uploads").child(fileName).putFile(pdfuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri downloadUrl = uri;

                                        DatabaseReference reference = database.getInstance().getReference();
                                        reference.child(fileName).setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(cab2.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(cab2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(cab2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        int currentprogress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentprogress);
                    }
                });
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int day= c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);



                dp=new DatePickerDialog(cab2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker , int myear, int ymonth, int dayOfMonth) {

                        dateofregistration1.setText(dayOfMonth+"/"+ (ymonth+1) + "/"+myear);

                    }
                },day,month,year);
                dp.show();
            }
        } );

        register1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = name1.getText().toString();
                if (address1.getText().toString().trim().isEmpty()) {
                    address1.setError("address field can't be empty!");

                }else if
                (working1.getText().toString().trim().isEmpty()) {
                        working1.setError("Working area field can't be empty!");
                }
                else if (mobileno1.length()!=10){
                    mobileno1.setError("Invalid mobile no.");

                }
                else if(!password1.getText().toString().equals(cpassword1.getText().toString()))
                {
                    cpassword1.setError("Password do not match");
                }
                else if(!isEmailValid(email1.getText().toString().trim()))
                {
                    email1.setError("Not a valid email");
                }
                else if(agencyname1.getText().toString().length()>=15)
                {
                   agencyname1.setError("Should be less than 15");
                }
                else if(username1.getText().toString().length()<=3) {
                    username1.setError("username should be greater than 3");
                }
                else if(password1.getText().toString().length()<6)
                {
                    password1.setError("Password should be greater than 5");
                }else if(chkBox4.isChecked()&&cno.getText().toString().trim().isEmpty())
                {
                    cno.setError("Cannot be empty");
                }else if(chkBox4.isChecked()&& rtobranch1.getText().toString().trim().isEmpty()){
                    rtobranch1.setError("cannot be empty");
                }else if(chkBox4.isChecked()&& address1.getText().toString().trim().isEmpty()){
                    address1.setError("cannot be empty");
                }else if(chkBox4.isChecked()&&dateofregistration1.getText().toString().trim().isEmpty()){
                    dateofregistration1.setError("cannot be empty");
                }else if(chkBox3.isChecked()&&agencyname1.getText().toString().trim().isEmpty()){
                    agencyname1.setError("Cannot be empty");
                }
                else if(!chkBox3.isChecked()&&!chkBox4.isChecked()){
                    Toast.makeText(getApplicationContext(),"please check atleast one ckeckbox",Toast.LENGTH_SHORT).show();
                }
                else{
                    pro.setMessage("Registering...");
                    pro.show();
                    Cab.setUsername(username1.getText().toString().trim());
                    Cab.setName(name1.getText().toString().trim());
                    Cab.setAgencyname(agencyname1.getText().toString().trim());
                    Cab.setPassword(password1.getText().toString());
                    Cab.setEmail(email1.getText().toString().trim());
                    Cab.setWorking(working1.getText().toString());
                    Cab.setName(agencyname1.getText().toString().trim());
                    Cab.setAddress(address1.getText().toString().trim());
                    Cab.setMobileno(mobileno1.getText().toString().trim());
                    Cab.setCabno(cno.getText().toString().trim());
                    Cab.setRtobranch(rtobranch1.getText().toString().trim());
                    Cab.setRtoaddress(rtoaddress1.getText().toString().trim());
                    Cab.setDateofregistration(dateofregistration1.getText().toString().trim());
                    Cab.setplaces(strings);

                    reff.child(name1.getText().toString().trim()).setValue(Cab);
                    Toast.makeText(cab2.this, "Successfully Signed in", Toast.LENGTH_LONG).show();

                }
            }
        });


    }


    public void onCheckboxClicked(View view) {

        switch(view.getId()) {

            case R.id.chkBox3:

                chkBox4.setChecked(false);


                break;

            case R.id.chkBox4:

                chkBox3.setChecked(false);


                break;


        }
    }




    private void selectPdf() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,87);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            selectPdf1();
            selectPdf();

        }else{
            Toast.makeText(cab2.this,"please provide permission..",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf1() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }





    boolean isEmailValid(CharSequence email1) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches();
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
            StorageReference riversRef = storage.child("images/profile/"+username1.getText().toString()+".jpg");

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK  && data.getData() !=null) {
            filePath = data.getData();
        } else{

        }
        if(requestCode==86 && resultCode==RESULT_OK  && data!=null) {
            pdfuri = data.getData();
            notification4.setText("A File is selected:" + data.getData().getLastPathSegment());
        }else{
            Toast.makeText(cab2.this,"please select a file",Toast.LENGTH_SHORT).show();
        }
        if(requestCode==87 && resultCode==RESULT_OK  && data!=null) {
            pdf = data.getData();
            notification5.setText("A File is selected:" + data.getData().getLastPathSegment());
        }else{
            Toast.makeText(cab2.this,"please select a file",Toast.LENGTH_SHORT).show();
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
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

        ed=new EditText(cab2.this);
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