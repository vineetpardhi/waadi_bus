package com.example.siddhant.loginui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.inputmethod.EditorInfo;
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
import java.util.concurrent.ThreadPoolExecutor;

public class bus_depo extends AppCompatActivity {
    DatabaseReference reff;
    List<newdepo> depot;
    private newdepo[] darr;
    private Busadapter madapter;

    private SearchView searchView;

    static int acount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_depo);
        acount=0;
        final Bundle d = getIntent().getExtras();
        final Adapter[] bAdapter = new Adapter[1];
        final RecyclerView list = findViewById(R.id.busdepolist);
        list.setLayoutManager(new LinearLayoutManager(this));
        reff = FirebaseDatabase.getInstance().getReference().child("Depos");
        depot =new ArrayList<newdepo>();

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot listsnapshot: dataSnapshot.getChildren()){



                    newdepo lst=new newdepo(listsnapshot.getKey(),listsnapshot.getValue().toString());

                    depot.add(lst);




                }
                darr=new newdepo[depot.size()];
                darr=depot.toArray(darr);

                madapter=new Busadapter(darr);
                list.setAdapter(madapter);
                madapter.setOnClickListnerItem(new Busadapter.OnItemClickListner() {
                    @Override
                    public void onButtonClick(int position) {
                        callDepo(darr[position]);
                    }
                });




                searchView=findViewById(R.id.deposearch);

                searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        madapter.getFilter().filter(newText);
                        return true;
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void callDepo(newdepo depo) {
        String s = "tel:" + depo.getDepoNumber();


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

        startActivity(intent);
    }


}
