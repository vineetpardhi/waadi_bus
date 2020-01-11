package com.example.siddhant.loginui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class home_stays_details extends AppCompatActivity {



    private Map<String,String> sd;
    private String syname,sydet;

    private List<String> imgurl;

    private ListView ls;

    private DatabaseReference sref;

    private TextView home_st_name,home_st_loc,home_st_des,home_st_price,home_stay_rating,home_st_comments,home_st_cordinate,home_St_numofrm;


    private  String[] coordinates;
    private RatingBar ratingBar;
    private CarouselView carouselView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stays_details);



        ratingBar=findViewById(R.id.ratingBar3);

        home_st_name=findViewById(R.id.home_st_name);
        home_st_loc=findViewById(R.id.home_st_loc);
        home_st_des=findViewById(R.id.home_st_des);
        home_st_price=findViewById(R.id.home_st_price);
        home_stay_rating=findViewById(R.id.home_stay_rating);
        home_st_comments=findViewById(R.id.home_st_comments);
        //home_st_cordinate=findViewById(R.id.home_stay_cordinate);
        home_St_numofrm=findViewById(R.id.home_st_numofr);


        ls=findViewById(R.id.amm_list);







        carouselView2=findViewById(R.id.stay_carousel2);


        Bundle b=getIntent().getExtras();
        syname=b.getString("stay_name");

        imgurl=new ArrayList<>();
        sref= FirebaseDatabase.getInstance().getReference("stays").child("Sheet1");

        sref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                for(DataSnapshot list:dataSnapshot.getChildren()) {
                    if (list.child("Name").getValue().toString().equals(syname)) {

                        home_st_name.setText(list.child("Name").getValue().toString());
                        home_st_loc.setText(list.child("Location").getValue().toString());
                        home_st_des.setText(list.child("Description").getValue().toString());
                        home_st_price.setText(list.child("Price_per_night").getValue().toString()+"/Night");
                        home_stay_rating.setText(list.child("Ratings").getValue().toString());
                        ratingBar.setRating(Float.parseFloat(home_stay_rating.getText().toString()));



                        home_st_comments.setText(list.child("Comments").getValue().toString());
                        home_St_numofrm.setText("No. of rooms: "+list.child("number of rooms").getValue().toString());




                        imgurl.addAll((List)list.child("img_url").getValue());


                        String[] amlist=list.child("Amenities").getValue().toString().split(",");
                        coordinates=list.child("Coordinates").getValue().toString().split(",");






                        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.ammenities_list,R.id.label,amlist);
                        ls.setAdapter(adapter);
                    }


                }








            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        home_st_cordinate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Float.parseFloat(coordinates[0]),Float.parseFloat(coordinates[1]));
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
//            }
//        });





        loadcarousel();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadcarousel();
            }
        },5000);
    }

    public void loadcarousel()
    {


        carouselView2.setPageCount(imgurl.size());

        /*setting image carousel*/
        carouselView2.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, final ImageView imageView) {
                Glide.with(getApplicationContext()).asBitmap().load(imgurl.get(position)).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
            }
        });
        /*end of image carousel*/

    }


}
