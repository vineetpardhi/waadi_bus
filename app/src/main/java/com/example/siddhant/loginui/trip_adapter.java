package com.example.siddhant.loginui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class trip_adapter extends RecyclerView.Adapter<trip_adapter.TripViewHolder>{

    private quotation qarr[];
    public trip_adapter(quotation qarr[]) {
        this.qarr=qarr;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.trip_list,parent,false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {

        holder.src.setText(qarr[position].getSource());
        holder.des.setText(qarr[position].getDestination());
        holder.bus_type.setText(qarr[position].getBustype());
        holder.nos.setText(String.valueOf(qarr[position].getNo_of_seates()));
        holder.trip.setText(qarr[position].getTrip());
        holder.dt1.setText(qarr[position].getDt1());
        holder.dt2.setText(qarr[position].getDt2());

    }

    @Override
    public int getItemCount() {
        return qarr.length;
    }

    public class TripViewHolder extends RecyclerView.ViewHolder{
        TextView src,des,bus_type,dt1,dt2,nos,trip;
        public TripViewHolder(View itemView) {
            super(itemView);

            src=itemView.findViewById(R.id.hsource);
            des=itemView.findViewById(R.id.hdestination);
            bus_type=itemView.findViewById(R.id.bus_type);
            nos=itemView.findViewById(R.id.num_of_seats);
            trip=itemView.findViewById(R.id.trip_type);
            dt1=itemView.findViewById(R.id.frmdt);
            dt2=itemView.findViewById(R.id.frmwhen);


        }
    }
}
