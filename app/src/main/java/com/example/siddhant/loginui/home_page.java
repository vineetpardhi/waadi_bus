package com.example.siddhant.loginui;

import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class home_page extends AppCompatActivity {

    Button lbtn,nav_btn,close_btn;



    TextView txt;
    SaveSharedPreference session;


    private DrawerLayout mdl;
    private NavigationView ng;

    private ImageView prf;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        lbtn=findViewById(R.id.login_btn);
        txt=findViewById(R.id.usern);

        mdl=findViewById(R.id.drawerlayout);






        nav_btn=findViewById(R.id.nav_btn);

        close_btn=findViewById(R.id.close_btn);



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


















        prf=findViewById(R.id.usrp);


        session=new SaveSharedPreference(getApplicationContext());


        HashMap<String, String> user = session.getUserDetails();

        String name=user.get(SaveSharedPreference.KEY_NAME);

        if(!session.isLoggedIn()) {

            lbtn.setText("Login");
            txt.setVisibility(TextView.GONE);
            lbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    session.checkLogin();
                }
            });


            prf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {


            lbtn.setText("Logout");

            txt.setText(name);

            lbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    // Clear the session data
                    // This will clear all session data and
                    // redirect user to LoginActivity
                    session.logoutUser();
                    finish();

                }
            });



            prf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),user_dashboard.class));

                }
            });
        }





    }


    @Override
    public void onBackPressed() {
        if(!session.isLoggedIn())
        {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            finish();



                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            finish();



                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }




    public void gotoRickshaw(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw3.class));
    }

    public void registerRickshaw2(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw2.class));
    }
    public void gotoBus(View view) {
        startActivity(new Intent(getApplicationContext(),basic_bushome.class));
    }

    public void gotoTrail(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw4.class));
    }

    public void gotomap(View view) {
        startActivity(new Intent(getApplicationContext(),MapsActivity2.class));
    }



    public void gotoStays(View view) {
        startActivity(new Intent(getApplicationContext(),stays.class));
    }
}