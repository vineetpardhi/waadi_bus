package com.example.siddhant.loginui;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Busadapter extends RecyclerView.Adapter<Busadapter.ViewHolder> implements Filterable {
    private newdepo[]dDataset;
    private OnItemClickListner mylistner;
    private List<newdepo> examplelistfull;
    public interface OnItemClickListner{
        void onButtonClick(int position);
    }
    public void setOnClickListnerItem(OnItemClickListner listner){
        this.mylistner=listner;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deponame;
        public TextView deponumber ;
        public Button callphone;

        public ViewHolder(View  itemView , final OnItemClickListner listner) {

            super(itemView);
            deponame=(TextView)itemView.findViewById(R.id.deponame);
            deponumber=(TextView)itemView.findViewById(R.id.deponumber);
            callphone=(Button)itemView.findViewById(R.id.button17);



            callphone.setOnClickListener(new View.OnClickListener(){

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
    public Busadapter(newdepo[] mydepoDataset){
        this.dDataset=mydepoDataset;
        examplelistfull= Arrays.asList(mydepoDataset);
    }

    @Override
    public Busadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.bus_list_depo_item,parent,false);
        return new ViewHolder(view , mylistner);
    }

    @Override
    public void onBindViewHolder(Busadapter.ViewHolder holder, int position) {
        holder.deponame.setText(dDataset[position].getDepoName());
        holder.deponame.setTextColor(Color.BLACK);

        holder.deponumber.setText(dDataset[position].getDepoNumber());
        holder.deponumber.setTextColor(Color.BLACK);



    }
    @Override
    public int getItemCount() {
        return dDataset.length;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<newdepo> filteredList = new ArrayList<>();
            if(charSequence ==null|| charSequence.length()==0){
                filteredList.addAll(examplelistfull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (newdepo item:examplelistfull){
                    if(item.getDepoName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);

                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            dDataset=null;
            List<newdepo> lst=new ArrayList<newdepo>();
            lst.addAll((List)filterResults.values);
            dDataset=new newdepo[lst.size()];
            dDataset=lst.toArray(dDataset);
            notifyDataSetChanged();
        }
    };
}


