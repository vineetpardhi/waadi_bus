package com.example.siddhant.loginui;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Busadapter extends RecyclerView.Adapter<Busadapter.DepoViewHolder> implements Filterable {
    private newdepo[]dDataset;
    private List<newdepo> examplelistfull;

    private List<newdepo> oglist;
    private  DepoAdapterListener listener;






    public Busadapter(newdepo[] mydepoDataset,DepoAdapterListener listener){
        this.dDataset=mydepoDataset;
        this.listener=listener;
        this.oglist=Arrays.asList(mydepoDataset);
        examplelistfull= oglist;
    }



    @Override
    public void onBindViewHolder(DepoViewHolder holder, int position) {

        final newdepo depo=examplelistfull.get(position);
        holder.deponame.setText(depo.getDepoName());
        holder.deponame.setTextColor(Color.BLACK);

        holder.deponumber.setText(depo.getDepoNumber());
        holder.deponumber.setTextColor(Color.BLACK);



    }




    @Override
    public DepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.bus_list_depo_item,parent,false);
        return new DepoViewHolder(view);
    }





    public class DepoViewHolder extends RecyclerView.ViewHolder {
        public TextView deponame;
        public TextView deponumber ;
        public Button callphone;

        public DepoViewHolder(View  itemView ) {

            super(itemView);
            deponame=(TextView)itemView.findViewById(R.id.deponame);
            deponumber=(TextView)itemView.findViewById(R.id.deponumber);
            callphone=(Button)itemView.findViewById(R.id.button17);


            callphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.callDepo(examplelistfull.get(getAdapterPosition()));
                }
            });





        }


    }

    @Override
    public int getItemCount() {
        return examplelistfull.size();
    }



    //filter

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<newdepo> filteredList = new ArrayList<>();



            if(charSequence ==null|| charSequence.length()==0){
                examplelistfull.addAll(oglist);
            }
            else {


                String filterPattern = charSequence.toString().toLowerCase().trim();



                    for (newdepo item:oglist){
                        if(item.getDepoName().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);

                        }
                    }

                    examplelistfull=filteredList;



            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }



        //publishing results

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
          examplelistfull.addAll((List)filterResults.values);
          notifyDataSetChanged();
        }
    };








    public interface DepoAdapterListener{
        void callDepo(newdepo depo);
    }



}


