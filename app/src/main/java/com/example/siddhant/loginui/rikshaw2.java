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


public class rikshaw2 extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView image;
    private Button uploadphoto;
    private Button chooesphoto;
    private Button chooesfile;
    private Button uploadidproof;
    private Button chooesfile1;
    private Button rtoverification;
    private Uri filePath;
    private LinearLayout layout;
    private CheckBox chkBox;
    private  LinearLayout layout1;
    private CheckBox chkBox1;
    Uri pdfuri;
    Uri pdf;
    ProgressDialog progressDialog;
    private StorageReference storage;
    private ImageView resultView;
    List<String> strings;
    Calendar c;
    DatePickerDialog dp;

    EditText name, address, mobileno, rtobranch, rtoaddress, dateofregistration,working,username,password,cpassword,email;
    Button button,addInput,btn;
    TextView notification;
    TextView notification1;


    Driver drive;
    DatabaseReference reff;
    FirebaseDatabase database;
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
        chooesfile = (Button) findViewById(R.id.chooesfile);
        uploadidproof=(Button)findViewById(R.id.uploadidproof);
        notification=findViewById(R.id.notification);
        chooesfile1 = (Button) findViewById(R.id.chooesfile1);
        rtoverification=(Button)findViewById(R.id.rtoverification);
        notification1=findViewById(R.id.notification1);

        name = (EditText) findViewById(R.id.username);
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
        resultView=findViewById(R.id.image);
        name = (EditText) findViewById(R.id.agencyname);

        working= (EditText) findViewById(R.id.working);
        chkBox = (CheckBox) findViewById(R.id.chkBox);
        layout = (LinearLayout) findViewById(R.id.layout);
        chkBox1 = (CheckBox) findViewById(R.id.chkBox1);
        layout1 = (LinearLayout) findViewById(R.id.layout1);


        pro = new ProgressDialog(this);
        drive = new Driver();
        reff = FirebaseDatabase.getInstance().getReference().child("member");

        uploadphoto.setOnClickListener(this);
        chooesphoto.setOnClickListener(this);

        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                {
                    layout.setVisibility(View.VISIBLE);
                    chkBox.setText("Owner");
                }
                else
                {
                    layout.setVisibility(View.GONE);
                    chkBox.setText("Owner");
                }
            }


        });
        chkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                {
                    layout1.setVisibility(View.VISIBLE);
                    chkBox1.setText("Agency");
                }
                else
                {
                    layout1.setVisibility(View.GONE);
                    chkBox1.setText("Agency");
                }
            }


        });

        chooesfile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(rikshaw2.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(rikshaw2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        });

        rtoverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdf!=null){
                    uploadFile(pdf);
                }else {
                    Toast.makeText(rikshaw2.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }
            }
            private void uploadFile(Uri pdf) {
                progressDialog = new ProgressDialog(rikshaw2.this);
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
                                                    Toast.makeText(rikshaw2.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(rikshaw2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(rikshaw2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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

        chooesfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(rikshaw2.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf1();
                }
                else
                    ActivityCompat.requestPermissions(rikshaw2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }


        });


        uploadidproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfuri!=null){
                    uploadFile(pdfuri);
                }else {
                    Toast.makeText(rikshaw2.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }

            }




            private void uploadFile(Uri pdfuri) {
                progressDialog = new ProgressDialog(rikshaw2.this);
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
                                                    Toast.makeText(rikshaw2.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(rikshaw2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(rikshaw2.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                    drive.setUsername(username.getText().toString().trim());
                    drive.setPassword(password.getText().toString());
                    drive.setEmail(email.getText().toString().trim());
                    drive.setWorking(working.getText().toString());
                    drive.setName(name.getText().toString().trim());
                    drive.setAddress(address.getText().toString().trim());
                    drive.setMobileno(mobileno.getText().toString().trim());
                    drive.setRtobranch(rtobranch.getText().toString().trim());
                    drive.setRtoaddress(rtoaddress.getText().toString().trim());
                    drive.setDateofregistration(dateofregistration.getText().toString().trim());
                    drive.setplaces(strings);

                    reff.child(name.getText().toString().trim()).setValue(drive);
                    Toast.makeText(rikshaw2.this, "Successfully Signed in", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void onCheckboxClicked(View view) {

        switch(view.getId()) {

            case R.id.chkBox:

                chkBox1.setChecked(false);


                break;

            case R.id.chkBox1:

                chkBox.setChecked(false);


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
            Toast.makeText(rikshaw2.this,"please provide permission..",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf1() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
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
            StorageReference riversRef = storage.child("images/profile/"+username.getText().toString()+".jpg");

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
            notification.setText("A File is selected:" + data.getData().getLastPathSegment());
        }else{
            Toast.makeText(rikshaw2.this,"please select a file",Toast.LENGTH_SHORT).show();
        }
        if(requestCode==87 && resultCode==RESULT_OK  && data!=null) {
            pdf = data.getData();
            notification1.setText("A File is selected:" + data.getData().getLastPathSegment());
        }else{
            Toast.makeText(rikshaw2.this,"please select a file",Toast.LENGTH_SHORT).show();
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