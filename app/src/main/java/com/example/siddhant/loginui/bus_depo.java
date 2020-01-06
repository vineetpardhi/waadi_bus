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
import androidx.appcompat.widget.SearchView;

import android.view.inputmethod.EditorInfo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bus_depo extends AppCompatActivity implements Busadapter.DepoAdapterListener{
    DatabaseReference reff;
    List<newdepo> depot;
    private newdepo[] darr;
    private Busadapter madapter;

    private Busadapter.DepoAdapterListener listener;
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






        final Busadapter.DepoAdapterListener ls=this;






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




                madapter=new Busadapter(darr,ls);

                list.setAdapter(madapter);

                madapter.notifyDataSetChanged();







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

    public void callDepo(newdepo depo) {
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
