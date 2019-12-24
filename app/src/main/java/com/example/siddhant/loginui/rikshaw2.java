package com.example.siddhant.loginui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class rikshaw2 extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 234;
    private ImageView image;
    private Button uploadphoto;
    private Button chooesphoto;
    private Uri filePath;
    private StorageReference storage;
    List<String> strings;

    EditText name, address, mobileno, rtobranch, rtoaddress, dateofregistration,working;
    Button button,addInput;
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
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        mobileno = (EditText) findViewById(R.id.mobileno);
        rtobranch = (EditText) findViewById(R.id.rtobranch);
        rtoaddress = (EditText) findViewById(R.id.rtoaddress);
        dateofregistration = (EditText) findViewById(R.id.rtoaddress);
        button = (Button) findViewById(R.id.Register);
        addInput = (Button) findViewById(R.id.add);


        working= (EditText) findViewById(R.id.working);

        pro = new ProgressDialog(this);
        drive = new Driver();
        reff = FirebaseDatabase.getInstance().getReference().child("member");

        uploadphoto.setOnClickListener(this);
        chooesphoto.setOnClickListener(this);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = name.getText().toString();
                if (address.getText().toString().trim().isEmpty()) {
                    address.setError("email field can't be empty!");
                } else if (name.getText().toString().trim().isEmpty()) {
                    name.setError("name field can't be empty!");
                } else if (mobileno.length() == 10)
                    mobileno.setError("Invalid mobile no.");
                else if (rtobranch.getText().toString().trim().isEmpty()) {
                    rtobranch.setError("rtobranch field can't be empty");
                } else if (rtoaddress.getText().toString().trim().isEmpty()) {
                    rtoaddress.setError("rtoaddress field can't be empty");
                } else if (dateofregistration.getText().toString().trim().isEmpty()) {
                    dateofregistration.setError("Date of registration field can't be empty");
                } else {
                    pro.setMessage("Registering...");
                    pro.show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData() !=null){
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
            ed.setLayoutParams();
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