package com.hunter.naiie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.naiie.R;
import com.hunter.naiie.model.ItemData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
    ItemData itemData[];

    public MyAdapter(ItemData[] itemData) {
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.service_name,null);

        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {

        holder.textView.setText(itemData[position].getName());
        holder.imageView.setImageResource(itemData[position].getImgId());

    }

    @Override
    public int getItemCount() {
        return itemData.length;
    }

    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.ser_img);
            textView=itemView.findViewById(R.id.ser_name);
        }
    }
}
