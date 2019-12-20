package com.example.siddhant.loginui;

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
    DatabaseReference reff;
    ProgressDialog pro;
    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        otp = (EditText) findViewById(R.id.otp);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        email = (EditText) findViewById(R.id.email);
        verfication = (EditText) findViewById(R.id.verfication);
        button = (Button) findViewById(R.id.button);
        pro = new ProgressDialog(this);
        member = new member();
        reff = FirebaseDatabase.getInstance().getReference().child("member");
        final String phonenumber = verfication.getText().toString();
        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String number = verfication.getText().toString().trim();

                    if(number.isEmpty() || number.length() < 10) {
                        verfication.setError("Valid number is required");
                        verfication.requestFocus();
                        return;
                    }
                    sendVerificationCode("+91"+number);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
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
                } else if (cpassword.getText().toString() != password.getText().toString()) {
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
                    startActivity(new Intent(getApplicationContext(), login.class));
                }
            }
        });


        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = otp.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)) {

                    otp.setError("Enter code...");
                    otp.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });
    }
        private void verifyCode(String code){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithCredential(credential);
        }

        private void signInWithCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(sign_up.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);

                            } else {
                                Toast.makeText(sign_up.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    });
        }

        private void sendVerificationCode(String number){
            Toast.makeText(sign_up.this,number, Toast.LENGTH_LONG).show();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    mCallBack
            );
        }

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks
                mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(sign_up.this, "code sent", Toast.LENGTH_LONG).show();
                verificationid = s;
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null){
                    progressBar.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(sign_up.this, e.getMessage(),Toast.LENGTH_LONG).show();

            }
        };

    public void gotologin(View view) {
        startActivity(new Intent(getApplicationContext(),login.class));
    }

}
