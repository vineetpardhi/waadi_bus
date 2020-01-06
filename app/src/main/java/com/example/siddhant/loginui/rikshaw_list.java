package com.example.siddhant.loginui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rikshaw_list extends AppCompatActivity {
    DatabaseReference reff;
    EditText ed;
    List<newdriver> driver1;
    List<newdriver> driver2;
    List<newdriver> driver3;
    static int acount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rikshaw_list);
        acount = 0;
        final Bundle b = getIntent().getExtras();

        final String src = (b.getString("src")).toLowerCase();
        final String dest = (b.getString("dest")).toLowerCase();
        final Adapter[] mAdapter = new Adapter[1];
        final RecyclerView list = findViewById(R.id.lists);
        list.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference().child("member");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    driver1 = new ArrayList<newdriver>();
                    String cust = listsnapshot.child("role").getValue(String.class);
                    if (!cust.isEmpty() && cust.equals("Driver")) {
                        String a = listsnapshot.child("working").getValue().toString().toLowerCase();
                        if (a.equals(src)) {
                            String b = listsnapshot.child("places").getValue().toString().toLowerCase();
                            if (b.contains(dest)) {
                                Map<String, String> td = (HashMap<String, String>) listsnapshot.getValue();
                                newdriver nd = new newdriver();
                                nd.setName(td.get("name"));
                                nd.setMobileno(td.get("mobileno"));
                                nd.setRno(td.get("rickshawno"));
                                driver1.add(nd);
                            }
                        }
                    }
                }
                driver2 = new ArrayList<newdriver>();
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    String cust = listsnapshot.child("role").getValue(String.class);
                    if (!cust.isEmpty() && cust.equals("Driver")) {
                        String a = listsnapshot.child("places").getValue().toString().toLowerCase();
                        if (a.contains(src)) {
                            String b = listsnapshot.child("places").getValue().toString().toLowerCase();
                            if (b.contains(dest)) {
                                Map<String, String> td = (HashMap<String, String>) listsnapshot.getValue();
                                newdriver nd = new newdriver();
                                nd.setName(td.get("name"));
                                nd.setMobileno(td.get("mobileno"));
                                nd.setRno(td.get("rickshawno"));
                                driver1.add(nd);
                            }
                        }
                    }
                }
                driver3 = new ArrayList<newdriver>();
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    String cust = listsnapshot.child("role").getValue(String.class);
                    if (!cust.isEmpty() && cust.equals("Driver")) {
                        String a = listsnapshot.child("working").getValue().toString().toLowerCase();
                        if (a.equals(dest)) {
                            String b = listsnapshot.child("places").getValue().toString().toLowerCase();
                            if (b.contains(src)) {
                                Map<String, String> td = (HashMap<String, String>) listsnapshot.getValue();
                                newdriver nd = new newdriver();
                                Toast.makeText(getApplicationContext(), td.toString(), Toast.LENGTH_SHORT).show();
                                nd.setName(td.get("name"));
                                nd.setMobileno(td.get("mobileno"));
                                nd.setRno(td.get("rickshawno"));
                                driver1.add(nd);
                            }
                        }
                    }
                }
                if (driver1.size() + driver2.size() + driver3.size() > 0) {
                    final newdriver ng[] = new newdriver[driver1.size() + driver2.size() + driver3.size()];
                    for (newdriver m : driver1) {
                        ng[acount] = new newdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }

                    for (newdriver m : driver2) {
                        ng[acount] = new newdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }

                    for (newdriver m : driver3) {
                        ng[acount] = new newdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }
                    mAdapter[0] = new Adapter(ng);
                    list.setAdapter(mAdapter[0]);
                    mAdapter[0].setOnClickListnerItem(new Adapter.OnItemClickListner() {
                        @Override
                        public void onButtonClick(int position) {
                            callDriver(ng[position]);
                        }
                    });
                } else {


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void callDriver(newdriver newdriver) {
        String s = "tel:" + newdriver.getMobileno();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(s));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Toast.makeText(getApplicationContext(), newdriver.getMobileno(), Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

}