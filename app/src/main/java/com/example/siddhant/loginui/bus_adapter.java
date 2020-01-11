package com.example.siddhant.loginui;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class bus_adapter extends RecyclerView.Adapter<bus_adapter.BusviewHolder> implements Filterable{

    public  View v;

    private List<Bus_data> busfilteredlist;


    private BusAdapterListener listener;




    private String src;
    private String des;

    private Context context;
    private Bus_data[] data;
    List<Bus_data> oglst;

    public bus_adapter(Bus_data[] data, String src, String des, Context context,BusAdapterListener blistener)
    {
        this.src=src;
        this.des=des;
        this.context=context;
        this.listener=blistener;
        this.data=data;
        this.oglst =Arrays.asList(data);
        this.busfilteredlist=oglst;
    }




    @Override
    public BusviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.list_item,parent,false);
        return new BusviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BusviewHolder holder, int position) {

        final Bus_data bus=busfilteredlist.get(position);
        holder.src.setText(this.src);
        holder.des.setText(this.des);
        holder.bus_no.setText(bus.getBus_number());
        holder.bus_name.setText(bus.getBus_name());
        holder.bustime.setText(bus.getBus_time());
        holder.busnumberofstops.setText(bus.getNum_of_stops());


    }

    @Override
    public int getItemCount() {
        return busfilteredlist.size();
    }





    @Override
    public Filter getFilter()
    {
        return examplefilter;
    }

    private final Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Bus_data> filtered_list = new ArrayList<>();



            if (charSequence==null || charSequence.length()==0) {
                busfilteredlist.addAll(oglst);


            } else {


                String filterpattern = charSequence.toString().toLowerCase().trim();



                    for (Bus_data item : oglst) {
                        if (item.getBus_name().toLowerCase().contains(filterpattern)) {
                            filtered_list.add(item);

                        }
                    }



                busfilteredlist=filtered_list;

            }

            FilterResults results = new FilterResults();
            results.values =busfilteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            busfilteredlist.addAll((List) results.values);
            notifyDataSetChanged();


        }
    };



    //Fiterable ends










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
                    listener.OnBusSelected(busfilteredlist.get(getAdapterPosition()));
                }
            });

        }
    }



    public interface BusAdapterListener{
        void OnBusSelected(Bus_data bus);
    }






}
