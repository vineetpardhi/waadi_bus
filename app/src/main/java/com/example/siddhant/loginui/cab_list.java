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

public class cab_list extends AppCompatActivity {
    DatabaseReference reff;
    EditText ed;
    List<cabdriver> driver1;
    List<cabdriver> driver2;
    List<cabdriver> driver3;
    static int acount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cab_list);
        acount = 0;
        final Bundle b = getIntent().getExtras();

        final String src = (b.getString("src")).toLowerCase();
        final String dest = (b.getString("dest")).toLowerCase();
        final CabAdapter[] mCabAdapter = new CabAdapter[1];
        final RecyclerView list = findViewById(R.id.lists);
        list.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference().child("member");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    driver1 = new ArrayList<cabdriver>();
                    String cust = listsnapshot.child("role").getValue(String.class);
                    if (!cust.isEmpty() && cust.equals("Driver")) {
                        String a = listsnapshot.child("working").getValue().toString().toLowerCase();
                        if (a.equals(src)) {
                            String b = listsnapshot.child("places").getValue().toString().toLowerCase();
                            if (b.contains(dest)) {
                                Map<String, String> td = (HashMap<String, String>) listsnapshot.getValue();
                                cabdriver cd = new cabdriver();
                                cd.setName(td.get("name"));
                                cd.setMobileno(td.get("mobileno"));
                                cd.setCabno(td.get("Cabno"));
                                driver1.add(cd);
                            }
                        }
                    }
                }
                driver2 = new ArrayList<cabdriver>();
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    String cust = listsnapshot.child("role").getValue(String.class);
                    if (!cust.isEmpty() && cust.equals("Driver")) {
                        String a = listsnapshot.child("places").getValue().toString().toLowerCase();
                        if (a.contains(src)) {
                            String b = listsnapshot.child("places").getValue().toString().toLowerCase();
                            if (b.contains(dest)) {
                                Map<String, String> td = (HashMap<String, String>) listsnapshot.getValue();
                                cabdriver cd = new cabdriver();
                                cd.setName(td.get("name"));
                                cd.setMobileno(td.get("mobileno"));
                                cd.setCabno(td.get("Cabno"));
                                driver1.add(cd);
                            }
                        }
                    }
                }
                driver3 = new ArrayList<cabdriver>();
                for (DataSnapshot listsnapshot : dataSnapshot.getChildren()) {
                    String cust = listsnapshot.child("role").getValue(String.class);
                    if (!cust.isEmpty() && cust.equals("Driver")) {
                        String a = listsnapshot.child("working").getValue().toString().toLowerCase();
                        if (a.equals(dest)) {
                            String b = listsnapshot.child("places").getValue().toString().toLowerCase();
                            if (b.contains(src)) {
                                Map<String, String> td = (HashMap<String, String>) listsnapshot.getValue();
                                cabdriver cd = new cabdriver();
                                Toast.makeText(getApplicationContext(), td.toString(), Toast.LENGTH_SHORT).show();
                                cd.setName(td.get("name"));
                                cd.setMobileno(td.get("mobileno"));
                                cd.setCabno(td.get("cabno"));
                                driver1.add(cd);
                            }
                        }
                    }
                }
                if (driver1.size() + driver2.size() + driver3.size() > 0) {
                    final cabdriver ng[] = new cabdriver[driver1.size() + driver2.size() + driver3.size()];
                    for (cabdriver m : driver1) {
                        ng[acount] = new cabdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }

                    for (cabdriver m : driver2) {
                        ng[acount] = new cabdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }

                    for (cabdriver m : driver3) {
                        ng[acount] = new cabdriver();
                        ng[acount] = m;
                        acount = acount + 1;
                    }
                    mCabAdapter[0] = new CabAdapter(ng);
                    list.setAdapter(mCabAdapter[0]);
                    mCabAdapter[0].setOnClickListnerItem(new Adapter.OnItemClickListner() {
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

    private void callDriver(cabdriver cabdriver) {
        String s = "tel:" + cabdriver.getMobileno();
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
        Toast.makeText(getApplicationContext(), cabdriver.getMobileno(), Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

}