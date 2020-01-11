package com.example.siddhant.loginui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;


public class house_stay_adapter extends RecyclerView.Adapter<house_stay_adapter.HouseViewHolder> implements Filterable {


    private List<stays_data> staysfiltered;
    private List<stays_data> stay_val;
    List<String> imgUrls;
    private Context context;
    private HomeStaysListener slistener;

    public house_stay_adapter(List<stays_data> stay_val,Context context,HomeStaysListener listener) {

        this.stay_val=stay_val;
        this.context=context;
        this.staysfiltered=stay_val;
        this.slistener=listener;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.house_stay_list,parent,false);
        return new house_stay_adapter.HouseViewHolder(view);
    }


    public class HouseViewHolder extends RecyclerView.ViewHolder{
        CarouselView carouselView;

        RatingBar rt;
        TextView name,location,price,rating;
        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);

            carouselView=itemView.findViewById(R.id.stay_carousel);
            name=itemView.findViewById(R.id.stay_nm);
            location=itemView.findViewById(R.id.stay_loc);
            price=itemView.findViewById(R.id.stay_price);
            rating=itemView.findViewById(R.id.stay_rating);

            rt=itemView.findViewById(R.id.ratingBar2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slistener.OnStaysSelected(staysfiltered.get(getAdapterPosition()));
                }
            });


        }
    }


    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {

        imgUrls=new ArrayList<>();
        stays_data staysData=staysfiltered.get(position);


        imgUrls.addAll(staysData.getImg_url());


        holder.carouselView.setPageCount(imgUrls.size());
        holder.carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, final ImageView imageView) {
                Glide.with(context.getApplicationContext()).asBitmap().load(imgUrls.get(position)).into(new CustomTarget<Bitmap>() {
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

        holder.name.setText(staysData.getName());
        holder.location.setText(staysData.getLocation());
        holder.price.setText(staysData.getPrice_per_night());
        holder.rating.setText(staysData.getRatings());

        holder.rt.setRating(Float.parseFloat(staysData.getRatings()));


    }

    @Override
    public int getItemCount() {
        return staysfiltered.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private final Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<stays_data> filteredlist=new ArrayList<>();

            if(charSequence.length()==0 || charSequence==null){
                staysfiltered.addAll(stay_val);
            }
            else {

                String filterpattern=charSequence.toString().toLowerCase().trim();

                for(stays_data item:stay_val)
                {
                    if(item.getLocation().contains(filterpattern))
                    {
                        filteredlist.add(item);
                    }
                }

                staysfiltered=filteredlist;

            }


            FilterResults results=new FilterResults();
            results.values=filteredlist;
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            staysfiltered.addAll((List)filterResults.values);
            notifyDataSetChanged();

        }
    };


    public interface HomeStaysListener{
        void OnStaysSelected(stays_data staysData);
    }


}
