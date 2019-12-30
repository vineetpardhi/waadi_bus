package com.example.siddhant.loginui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.MyViewHolder>  {
    private static member[] mDataset;
    private usersAdapter.OnItemClickListner mlistner;

    public interface OnItemClickListner{
        void onButtonClick(int position);
    }

    public void setOnClickListnerItem(usersAdapter.OnItemClickListner listner)
    {
        this.mlistner=listner;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView src;
        public TextView dest;


        public MyViewHolder(View v) {
            super(v);
            src = (TextView) v.findViewById(R.id.source);
            dest = (TextView)v.findViewById(R.id.dest);


//            callbtn.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View view) {
//                    if(listner!=null)
//                    {
//                        int position=getAdapterPosition();
//                        if(position!=RecyclerView.NO_POSITION)
//                        {
//                            listner.onButtonClick(position);
//                        }
//                    }
//                }
//            });

        }
    }

    public usersAdapter(member[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public usersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.customers,parent,false);
        return new usersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(usersAdapter.MyViewHolder holder, int position) {
        holder.src.setText(mDataset[position].getSource());
        holder.src.setTextColor(Color.BLACK);

        holder.dest.setText(mDataset[position].getDestination());
        holder.dest.setTextColor(Color.BLACK);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
