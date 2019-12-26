package com.example.siddhant.loginui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {


    private EditText myuser,mypassword;
    private Button loginbtn;

    public TextView showusername;

    FirebaseDatabase database;
    DatabaseReference mref;


    private static String UN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn=findViewById(R.id.button3);
        myuser=findViewById(R.id.email);
        mypassword=findViewById(R.id.Password);

        //Firebase
        database= FirebaseDatabase.getInstance();
        mref=database.getReference("member");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn( myuser.getText().toString(),mypassword.getText().toString());

            }
        });
    }


    private void LogIn(final String Username,final String Password)
    {


        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(Username.isEmpty())
                {
                    myuser.setError("please enter your username");

                }
                else if (Password.isEmpty())
                {
                    Toast.makeText(login.this,"please enter password",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (dataSnapshot.child(Username).exists()) {
                        if (!Username.isEmpty()) {
                            User login = dataSnapshot.child(Username).getValue(User.class);

                            if (login.getPassword().equals(Password)) {
                                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("message",Username);
                                startActivity(i);
                                finish();
                                Toast.makeText(login.this, "Success Login", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(login.this, "Password or Username is Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(login.this, "username not registered", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void gotoHome(View view) {
        startActivity(new Intent(getApplicationContext(),sign_up.class));
    }
}
