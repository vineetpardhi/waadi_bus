package com.example.siddhant.loginui;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private newdriver[] mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView rno;
        public TextView phone;

        public MyViewHolder(View v) {
            super(v);
            rno = (TextView) v.findViewById(R.id.rno);
            phone = (TextView)v.findViewById(R.id.phone);
            title=(TextView) v.findViewById(R.id.title);
        }
    }

    public Adapter(newdriver[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
    LayoutInflater inflater=LayoutInflater.from(parent.getContext());
    View view=inflater.inflate(R.layout.activity_list_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(mDataset[position].getName());
        holder.title.setTextColor(Color.BLACK);

        holder.rno.setText(mDataset[position].getRno());
        holder.rno.setTextColor(Color.BLACK);

        holder.phone.setText(mDataset[position].getMobileno());
        holder.phone.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}