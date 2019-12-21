package com.example.siddhant.loginui;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class sign_up extends AppCompatActivity {
    EditText username,password,cpassword,email,verfication,otp;
    Button button;
    member member;
    String codeSent;
    DatabaseReference reff;
    ProgressDialog pro;
    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();


        otp = (EditText) findViewById(R.id.otp);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        email = (EditText) findViewById(R.id.email);
        verfication = (EditText) findViewById(R.id.verfication);
        button = (Button) findViewById(R.id.button);


        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
        pro = new ProgressDialog(this);
        member = new member();
        reff = FirebaseDatabase.getInstance().getReference().child("member");
        final String phonenumber = verfication.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String Cpassword = cpassword.getText().toString();
                String Password = password.getText().toString();


                String user = username.getText().toString();
                if (email.getText().toString().trim().isEmpty()) {
                    email.setError("email field can't be empty!");
                } else if (username.getText().toString().trim().isEmpty()) {
                    username.setError("username field can't be empty!");
                } else if (username.length() > 15)
                    username.setError("username cannot be greater than 15!");
                else if (password.getText().toString().isEmpty()) {
                    password.setError("Password field cannot be empty");
                } else if (cpassword.getText().toString().isEmpty()) {
                    cpassword.setError("Confirm password field cannot be empty!");
                } else if (!Password.equals(Cpassword)) {
                    cpassword.setError("Password do not match!");
                } else {
                    pro.setMessage("Registering...");
                    pro.show();
                    member.setUsername(username.getText().toString().trim());
                    member.setPassword(password.getText().toString().trim());
                    member.setCpassword(cpassword.getText().toString().trim());
                    member.setEmail(email.getText().toString().trim());
                    member.setVerification(verfication.getText().toString().trim());
                    reff.child(username.getText().toString().trim()).setValue(member);
                    Toast.makeText(sign_up.this, "Successfully Signed in", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(), login.class));
                }
            }
        });
    }
        private void verifySignInCode(){
            String code = otp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            signInWithPhoneAuthCredential(credential);
        }

        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //here you can open new activity
                                Toast.makeText(getApplicationContext(),
                                        "Login Successfull", Toast.LENGTH_LONG).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(getApplicationContext(),
                                            "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
        }

        private void sendVerificationCode(){

            String phone = verfication.getText().toString();

            if(phone.isEmpty()){
                verfication.setError("Phone number is required");
                verfication.requestFocus();
                return;
            }

            if(phone.length() < 10 ){
                verfication.setError("Please enter a valid phone");
                verfication.requestFocus();
                return;
            }


            PhoneAuthProvider.getInstance().verifyPhoneNumber(

                    phone,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
            Toast.makeText(getApplicationContext(),
                    "code sending ... ", Toast.LENGTH_LONG).show();
        }



        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(),
                        "code sent", Toast.LENGTH_LONG).show();
                codeSent = s;
            }
        };

}
