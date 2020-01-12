package com.example.siddhant.loginui;



import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.List;

public class cabowner extends AppCompatActivity {
    private LinearLayout ly;
    private Button chooes2;
    private Button upload2;
    private Button chooes3;
    private Button upload3;
    Uri pdfuri;
    Uri pdf;
    ProgressDialog progressDialog;
    private StorageReference storage;

    Button button, addinput, btn;
    cab_agency Agency;
    DatabaseReference reff;
    TextView notification7;
    TextView notification8;


    FirebaseDatabase database;
    ProgressDialog pro;
    List<String> strings;
    static int count=0;
    static List<EditText> allEds=new ArrayList<EditText>();
    EditText ed ,ed1, ed2,ownername2, agencyaddress2, agencyphone1,agencyname2,  rtobranch, rtoaddress, dateofregistration,password2,cno2,cpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabowner);
        storage = FirebaseStorage.getInstance().getReference();
        addinput = (Button) findViewById(R.id.adding);
        ownername2 = (EditText) findViewById(R.id.ownername2);
        agencyname2 = (EditText) findViewById(R.id.agencyname2);
        agencyaddress2 = (EditText) findViewById(R.id.agencyaddress2);
        agencyphone1 = (EditText) findViewById(R.id.agencyphone1);
        password2=findViewById(R.id.password2);
        cno2=findViewById(R.id.cno2);
        cpassword2 = findViewById(R.id.cpassword2);
        chooes2 = findViewById(R.id.chooes2);
        upload2 = findViewById(R.id.upload2);
        chooes3 = findViewById(R.id.chooes3);
        upload3 = findViewById(R.id.upload3);
        button = findViewById(R.id.Register1);
        notification7=findViewById(R.id.notification7);
        notification8=findViewById(R.id.notification8);
        Agency=new cab_agency();
        pro = new ProgressDialog(this);
        reff = FirebaseDatabase.getInstance().getReference().child("cab_agency");

        chooes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(cabowner.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf1();
                }
                else
                    ActivityCompat.requestPermissions(cabowner.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }


        });


        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfuri!=null){
                    uploadFile(pdfuri);
                }else {
                    Toast.makeText(cabowner.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }

            }




            private void uploadFile(Uri pdfuri) {
                progressDialog = new ProgressDialog(cabowner.this);
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
                                                    Toast.makeText(cabowner.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(cabowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(cabowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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

        chooes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(cabowner.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(cabowner.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        });

        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdf!=null){
                    uploadFile(pdf);
                }else {
                    Toast.makeText(cabowner.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }
            }
            private void uploadFile(Uri pdf) {
                progressDialog = new ProgressDialog(cabowner.this);
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
                                                    Toast.makeText(cabowner.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(cabowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(cabowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = ownername2.getText().toString();

                if (agencyaddress2.getText().toString().trim().isEmpty()) {
                    agencyaddress2.setError("address field can't be empty!");
                } else if (ownername2.getText().toString().trim().isEmpty()) {
                    ownername2.setError("name field can't be empty!");
                }
                else if (agencyname2.getText().toString().trim().isEmpty()) {
                    agencyname2.setError("name field can't be empty!");
                }else if (agencyphone1.length()!=10){
                    agencyphone1.setError("Invalid mobile no.");
                }else if(password2.getText().toString().length()<6)
                {
                    password2.setError("Password should be greater than 5");

                }

                else{
                    //pro.setMessage("Registering...");
                    //  pro.show();

                    Agency.setOwnername(agencyname2.getText().toString());
                    Agency.setAgencyname(agencyname2.getText().toString().trim());
                    Agency.setAgencyaddress(agencyaddress2.getText().toString().trim());
                    Agency.setAgencyphone(agencyphone1.getText().toString().trim());
                    Agency.setPassword(password2.getText().toString().trim());
                    Agency.setCabno(cno2.getText().toString().trim());


                    reff.child(ownername2.getText().toString().trim()).setValue(Agency);
                    Toast.makeText(cabowner.this,"Successfully Register", Toast.LENGTH_LONG).show();

                }
            }
        });

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
            Toast.makeText(cabowner.this,"please provide permission..",Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf1() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfuri = data.getData();
            notification7.setText("A File is selected:" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(cabowner.this, "please select a file", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 87 && resultCode == RESULT_OK && data != null) {
            pdf = data.getData();
            notification8.setText("A File is selected:" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(cabowner.this, "please select a file", Toast.LENGTH_SHORT).show();
        }
    }




    public void add (View view){
        LinearLayout ly = findViewById(R.id.LinearLayout);

        ed = new EditText(cabowner.this);
        ed1 = new EditText(cabowner.this);
        ed2 = new EditText(cabowner.this);
        allEds.add(ed);
        allEds.add(ed1);
        allEds.add(ed2);
        ly.addView(ed);
        ly.addView(ed1);
        ly.addView(ed2);
        this.count = this.count + 1;

        ed.setId(this.count);
        ed.setHint("RTO Certificate " + this.count);
        this.count = this.count + 1;

        ed1.setId(this.count);
        ed1.setHint("RTO Branch " + this.count);
        this.count = this.count + 1;


        ed2.setId(this.count);
        ed2.setHint("RTO Address " + this.count);


    }
}








