package com.example.siddhant.loginui;

import android.graphics.Color;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private newdriver[] mDataset;
    private  OnItemClickListner mlistner;

    public interface OnItemClickListner{
        void onButtonClick(int position);
    }

    public void setOnClickListnerItem(OnItemClickListner listner)
    {
        this.mlistner=listner;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView rno;
        public TextView phone;
        public Button callbtn;

        public MyViewHolder(View v, final OnItemClickListner listner) {
            super(v);
            rno = (TextView) v.findViewById(R.id.rno);
            phone = (TextView)v.findViewById(R.id.dest);
            title=(TextView) v.findViewById(R.id.title);
            callbtn=(Button) v.findViewById(R.id.button16);

            callbtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                        if(listner!=null)
                        {
                            int position=getAdapterPosition();
                            if(position!=RecyclerView.NO_POSITION)
                            {
                                listner.onButtonClick(position);
                            }
                        }
                }
            });

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
        return new MyViewHolder(view,mlistner);
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