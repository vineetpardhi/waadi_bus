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

public class rikshaowner extends AppCompatActivity {
    private LinearLayout ly;
    private Button chooes1;
    private Button upload1;
    private Button chooes;
    private Button upload;
    Uri pdfuri;
    Uri pdf;
    ProgressDialog progressDialog;
    private StorageReference storage;

    Button button, addinput, btn;
    agency Agency;
    DatabaseReference reff;
    TextView notification;
    TextView notification1;


    FirebaseDatabase database;
    ProgressDialog pro;
    List<String> strings;
    static int count=0;
    static List<EditText> allEds=new ArrayList<EditText>();
    EditText ed ,ed1, ed2,ownername, agencyaddress, agencyphone,agencyname,  rtobranch, rtoaddress, dateofregistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaowner);
        storage = FirebaseStorage.getInstance().getReference();
        addinput = (Button) findViewById(R.id.adding);
        ownername = (EditText) findViewById(R.id.ownername);
        agencyname = (EditText) findViewById(R.id.agencyname);
        agencyaddress = (EditText) findViewById(R.id.agencyaddress);
        agencyphone = (EditText) findViewById(R.id.agencyphone);
        chooes1 = findViewById(R.id.chooes1);
        upload1 = findViewById(R.id.upload1);
        chooes = findViewById(R.id.chooes);
        upload = findViewById(R.id.upload);
        button = findViewById(R.id.Register);
        notification=findViewById(R.id.notification);
        notification1=findViewById(R.id.notification1);
        Agency=new agency();
        pro = new ProgressDialog(this);
        reff = FirebaseDatabase.getInstance().getReference().child("agency");

        chooes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(rikshaowner.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf1();
                }
                else
                    ActivityCompat.requestPermissions(rikshaowner.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }


        });


        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfuri!=null){
                    uploadFile(pdfuri);
                }else {
                    Toast.makeText(rikshaowner.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }

            }




            private void uploadFile(Uri pdfuri) {
                progressDialog = new ProgressDialog(rikshaowner.this);
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
                                                    Toast.makeText(rikshaowner.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(rikshaowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(rikshaowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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

        chooes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(rikshaowner.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else
                    ActivityCompat.requestPermissions(rikshaowner.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdf!=null){
                    uploadFile(pdf);
                }else {
                    Toast.makeText(rikshaowner.this, "Select a file" ,Toast.LENGTH_SHORT).show();
                }
            }
            private void uploadFile(Uri pdf) {
                progressDialog = new ProgressDialog(rikshaowner.this);
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
                                                    Toast.makeText(rikshaowner.this, "file successfully Uploaded", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(rikshaowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(rikshaowner.this, "file not successfully Uploaded", Toast.LENGTH_SHORT).show();
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
                String user = ownername.getText().toString();

                if (agencyaddress.getText().toString().trim().isEmpty()) {
                    agencyaddress.setError("address field can't be empty!");
                } else if (ownername.getText().toString().trim().isEmpty()) {
                    ownername.setError("name field can't be empty!");
                }
                else if (agencyname.getText().toString().trim().isEmpty()) {
                    agencyname.setError("name field can't be empty!");
                }else if (agencyphone.length()!=10){
                    agencyphone.setError("Invalid mobile no.");
                }

                else{
                    //pro.setMessage("Registering...");
                    //  pro.show();

                    Agency.setOwnername(agencyname.getText().toString());
                    Agency.setAgencyname(agencyname.getText().toString().trim());
                    Agency.setAgencyaddress(agencyaddress.getText().toString().trim());
                    Agency.setAgencyphone(agencyphone.getText().toString().trim());



                    reff.child(ownername.getText().toString().trim()).setValue(Agency);
                    Toast.makeText(rikshaowner.this, ownername.getText().toString().trim(), Toast.LENGTH_LONG).show();

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
            Toast.makeText(rikshaowner.this,"please provide permission..",Toast.LENGTH_SHORT).show();
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
            notification.setText("A File is selected:" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(rikshaowner.this, "please select a file", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 87 && resultCode == RESULT_OK && data != null) {
            pdf = data.getData();
            notification1.setText("A File is selected:" + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(rikshaowner.this, "please select a file", Toast.LENGTH_SHORT).show();
        }
    }




    public void add (View view){
        LinearLayout ly = findViewById(R.id.LinearLayout);

        ed = new EditText(rikshaowner.this);
        ed1 = new EditText(rikshaowner.this);
        ed2 = new EditText(rikshaowner.this);
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








