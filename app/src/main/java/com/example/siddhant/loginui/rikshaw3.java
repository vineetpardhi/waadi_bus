package com.example.siddhant.loginui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class rikshaw3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw3);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
    public void gotoRickshaw1(View view) {
      if(!isNetworkAvailable()) {
          startActivity(new Intent(getApplicationContext(), rikshaw1.class));
      }
      else
      {

          startActivity(new Intent(getApplicationContext(),MapsActivity2.class));
      }
    }

    public void gotoRickshaw2(View view) {
        startActivity(new Intent(getApplicationContext(),rikshaw2.class));

    }

    public void on_rikshaowner(View view) {

        startActivity(new Intent(getApplicationContext(),rikshaowner.class));
    }
}
