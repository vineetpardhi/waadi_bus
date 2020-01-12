package com.example.siddhant.loginui;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CabAdapter extends RecyclerView.Adapter<CabAdapter.MyViewHolder> {
    private cabdriver[] mDataset;
    private  OnItemClickListner mlistner;

    public void setOnClickListnerItem(Adapter.OnItemClickListner onItemClickListner) {
    }

    public interface OnItemClickListner{
        void onButtonClick(int position);
    }

    public void setOnClickListnerItem(OnItemClickListner listner)
    {
        this.mlistner=listner;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title1;
        public TextView cno1;
        public TextView phone1;
        public Button callbtn1;

        public MyViewHolder(View v, final OnItemClickListner listner) {
            super(v);
            cno1 = (TextView) v.findViewById(R.id.cno1);
            phone1 = (TextView)v.findViewById(R.id.dest1);
            title1=(TextView) v.findViewById(R.id.title1);
            callbtn1=(Button) v.findViewById(R.id.button17);

            callbtn1.setOnClickListener(new View.OnClickListener(){

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

    public CabAdapter(cabdriver[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public CabAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_list_item_layout,parent,false);
        return new MyViewHolder(view,mlistner);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title1.setText(mDataset[position].getName());
        holder.title1.setTextColor(Color.BLACK);

        holder.cno1.setText(mDataset[position].getCabno());
        holder.cno1.setTextColor(Color.BLACK);

        holder.phone1.setText(mDataset[position].getMobileno());
        holder.phone1.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}