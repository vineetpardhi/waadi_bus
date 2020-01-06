package com.example.siddhant.loginui;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class route_adapter extends RecyclerView.Adapter<route_adapter.RouteViewHolder>{


    private String[] rarr;
    private String src;
    private String des;


    public route_adapter(String[] rarr,String src,String des)
    {

        this.rarr=rarr;
        this.src=src;
        this.des=des;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.route_list,parent,false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {


        if(rarr[position].equals(src))
        {

            holder.station_name.setTextColor(Color.GREEN);
        }
        if(rarr[position].equals((des)))
        {
            holder.station_name.setTextColor(Color.RED);

        }
        holder.station_name.setText(rarr[position]);

    }

    @Override
    public int getItemCount() {
        return rarr.length;
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{
        public TextView station_name;
        public RouteViewHolder(View itemView) {

            super(itemView);

            station_name=itemView.findViewById(R.id.st_name);


        }
    }



}


