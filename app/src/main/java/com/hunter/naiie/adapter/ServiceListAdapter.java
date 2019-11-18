package com.hunter.naiie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter.naiie.R;
import com.hunter.naiie.model.ServiceList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceListHolder> {

    ArrayList<ServiceList>arrayList;

    public ServiceListAdapter(ArrayList<ServiceList> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ServiceListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.service_list_item, parent, false);
        return new ServiceListHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListHolder holder, int position) {

        ServiceList serviceList=arrayList.get(position);
        holder.s_name.setText(serviceList.getService_name());
        holder.s_price.setText(serviceList.getService_price());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ServiceListHolder extends RecyclerView.ViewHolder
    {
        TextView s_name,s_price;

        public ServiceListHolder(@NonNull View itemView) {
            super(itemView);

            s_name=itemView.findViewById(R.id.service_name);
            s_price=itemView.findViewById(R.id.service_price);
        }
    }
}
