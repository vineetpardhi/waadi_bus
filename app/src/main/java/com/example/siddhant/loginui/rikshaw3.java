package com.example.siddhant.loginui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class rikshaw3 extends AppCompatActivity {

    private DrawerLayout mdl;
    private Button nav_btn,close_btn;
    private TextView usn;


    private SaveSharedPreference session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw3);


        mdl=findViewById(R.id.drawerlayout3);


        nav_btn=findViewById(R.id.nav_btn3);

        close_btn=findViewById(R.id.close_btn3);








        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdl.openDrawer(Gravity.LEFT);

            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdl.closeDrawer(Gravity.LEFT);
            }
        });



        usn=findViewById(R.id.usern3);


        session=new SaveSharedPreference(getApplicationContext());




        HashMap<String, String> user = session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);


        if(session.isLoggedIn())
        {


            usn.setText(name);
        }
        else {
            usn.setText("");
        }




    }

    public void gotoRickshaw1(View view) {
          startActivity(new Intent(getApplicationContext(), rikshaw1.class));
    }

    public void gotoRickshaw2(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw2.class));

    }

    public void on_rikshaowner(View view) {

        startActivity(new Intent(getApplicationContext(),rikshaowner.class));
    }
}
