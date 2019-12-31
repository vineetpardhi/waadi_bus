package com.example.siddhant.loginui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class bus_adapter extends RecyclerView.Adapter<bus_adapter.BusviewHolder> implements Filterable{

    public  View v;

    private List<Bus_data> busfilteredlist;
    private OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void onItemClick(int postion);
    }


    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener=listener;
    }

    private String src;
    private String des;

    private Context context;
    private Bus_data[] data;

    public bus_adapter(Bus_data[] data, String src, String des, Context context)
    {
        this.src=src;
        this.des=des;
        this.context=context;

        this.data=data;
        this.busfilteredlist=Arrays.asList(data);
    }




    @Override
    public BusviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item,parent,false);
        return new BusviewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusviewHolder holder, int position) {


        holder.src.setText(this.src);
        holder.des.setText(this.des);
        holder.bus_no.setText(data[position].getBus_number());
        holder.bus_name.setText(data[position].getBus_name());
        holder.bustime.setText(data[position].getBus_time());
        holder.busnumberofstops.setText(data[position].getNum_of_stops());
    }

    @Override
    public int getItemCount() {
        return this.data.length;
    }

    public class BusviewHolder extends RecyclerView.ViewHolder {

        public TextView bus_name,bustime,busnumberofstops,src,des,bus_no;

        public BusviewHolder(View itemView) {
            super(itemView);

            v=itemView;

            bus_no=itemView.findViewById(R.id.bus_number);
            bus_name = itemView.findViewById(R.id.bus_n);
            bustime = itemView.findViewById(R.id.bus_t);
            busnumberofstops=itemView.findViewById(R.id.bus_st);
            src=itemView.findViewById(R.id.station_name1);
            des=itemView.findViewById(R.id.station_name2);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mlistener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION)
                        {
                            mlistener.onItemClick(position);


                        }
                    }


                }
            });

        }
    }

    public void changetext(Bus_data obj)
    {


        String s=obj.getBus_number();

        Intent intent=new Intent(context,bus_root.class);
        intent.putExtra("bus_number",s);
        intent.putExtra("src",src);
        intent.putExtra("des",des);
        context.startActivity(intent);



    }
    @Override
    public Filter getFilter()
    {
        return examplefilter;
    }

    private final Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Bus_data> oglst=Arrays.asList(data);
            List<Bus_data> filtered_list = new ArrayList<>();

            if (charSequence.equals(null) || charSequence.length() == 0) {
                busfilteredlist.addAll(oglst);

            } else {


                String filterpattern = charSequence.toString().toLowerCase().trim();


                for (Bus_data item :busfilteredlist ) {
                    if (item.getBus_name().toLowerCase().contains(filterpattern)) {
                        filtered_list.add(item);

                    }
                }





            }

            FilterResults results = new FilterResults();
            results.values =filtered_list;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            data=null;
            List<Bus_data> blst=new ArrayList<>();
            blst.addAll((List)results.values);
            data=new Bus_data[blst.size()];
            data=blst.toArray(data);
            notifyDataSetChanged();


        }
    };


}
