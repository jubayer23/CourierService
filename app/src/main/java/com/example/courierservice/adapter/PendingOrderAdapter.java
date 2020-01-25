package com.example.courierservice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.courierservice.R;
import com.example.courierservice.Utility.CommonMethods;
import com.example.courierservice.appdata.GlobalAppAccess;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.model.PendingOrder;

import java.util.ArrayList;
import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewHolder> implements Filterable {

    private List<PendingOrder> shopStocks;
    private List<PendingOrder> originalList;
    private final OnItemClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_number, tv_pickup, tv_destination, tv_due_date, tv_new;
        Button  btn_details, btn_route, btn_start;
        CardView item_container;

        MyViewHolder(View view) {
            super(view);

            item_container = view.findViewById(R.id.item_container);

            tv_order_number = view.findViewById(R.id.tv_order_number);
            tv_pickup = view.findViewById(R.id.tv_pickup_address);
            tv_destination = view.findViewById(R.id.tv_destination_address);

            tv_due_date = view.findViewById(R.id.tv_due_date);
            tv_new = view.findViewById(R.id.tv_new);


            btn_details = view.findViewById(R.id.btn_details);
            btn_route = view.findViewById(R.id.btn_route);
            btn_start = view.findViewById(R.id.btn_start);
            btn_start = view.findViewById(R.id.btn_start);

        }

        void bindClick(final PendingOrder item, final OnItemClickListener listener) {



            if (null == listener) return;

            btn_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDetailsClickListener(item);
                }
            });

            btn_route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRouteClickListener(item);
                }
            });
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStartClickListener(item);
                }
            });
        }


    }


    public PendingOrderAdapter(List<PendingOrder> stockList, OnItemClickListener listener) {
        this.shopStocks = stockList;
        this.listener = listener;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pending_order, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PendingOrder rawStock = shopStocks.get(position);

        holder.tv_order_number.setText(rawStock.getOrderNumber().toString());
        holder.tv_pickup.setText(rawStock.getPickUpAddress());
        holder.tv_destination.setText(rawStock.getDropOffAddress());
        holder.tv_due_date.setText(String.valueOf(rawStock.getDueTime()));

        holder.tv_due_date.setText(CommonMethods.getDateInStringFromMillis(String.valueOf(rawStock.getDueTime()), GlobalAppAccess.DATE_FORMAT_LOCAL));

        ArrayList<Integer> notiOrderNumbers = MydApplication.getInstance().getPrefManger().getNotiOrderNumber();

        if(notiOrderNumbers.contains(rawStock.getOrderNumber())){
            holder.tv_new.setVisibility(View.VISIBLE);
        }else{
            holder.tv_new.setVisibility(View.GONE);
        }

        if (position % 2 == 0)
            holder.item_container.setBackgroundColor(ContextCompat.getColor(holder.item_container.getContext(), R.color.gray_lightest));
        else
            holder.item_container.setBackgroundColor(ContextCompat.getColor(holder.item_container.getContext(), R.color.white));

        holder.bindClick(rawStock, listener);
       // holder.setForSold(rawStock);

    }

    @Override
    public int getItemCount() {
        return shopStocks.size();
    }

    public interface OnItemClickListener {
        void onRouteClickListener(PendingOrder item);
        void onDetailsClickListener(PendingOrder item);
        void onStartClickListener(PendingOrder item);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                shopStocks = (List<PendingOrder>) results.values;
                PendingOrderAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<PendingOrder> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<PendingOrder> getFilteredResults(String constraint) {
        List<PendingOrder> results = new ArrayList<>();

        for (PendingOrder item : originalList) {

            if (item.getOrderNumber().toString().toLowerCase().contains(constraint)) {
                results.add(item);
            }

        }
        return results;
    }
}
