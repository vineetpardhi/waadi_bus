package com.example.siddhant.loginui;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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



    SaveSharedPreference session;
    private static String UN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //shared preferences for storing data



        //getting the context of the activity
        session= new SaveSharedPreference(getApplicationContext());


        loginbtn=findViewById(R.id.button3);
        myuser=findViewById(R.id.username);
        mypassword=findViewById(R.id.password);

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


    private void LogIn(final String username,final String password)
    {


        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(username.isEmpty())
                {
                    myuser.setError("please enter your username");

                }
                else if (password.isEmpty())
                {
                    Toast.makeText(login.this,"please enter password",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (dataSnapshot.child(username).exists()) {
                        if (!username.isEmpty()) {
                            User login = dataSnapshot.child(username).getValue(User.class);

                            if (login.getPassword().equals(password)) {


                                session.createLoginSession(login.getUsername(),login.getEmail());


                                Intent i=new Intent(getApplicationContext(),home_page.class);
                                i.putExtra("message",username);
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


                Toast.makeText(getApplicationContext(),"Error connecting,please try again",Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void gotoHome(View view) {
        startActivity(new Intent(getApplicationContext(),sign_up.class));
    }
}
