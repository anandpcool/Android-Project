package com.hunter.naiie.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hunter.naiie.Common;
import com.hunter.naiie.R;
import com.hunter.naiie.adapter.ServiceListAdapter;
import com.hunter.naiie.model.ServiceList;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceFragment extends Fragment {

    //ArrayList<ServiceList> serviceListArrayList;
    RecyclerView recyclerView;
    AlertDialog alertDialog;
    ServiceListAdapter serviceListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView = getActivity().findViewById(R.id.catlist);

        //serviceListArrayList = new ArrayList<>();

        //Add Adapter

        serviceListAdapter=new ServiceListAdapter(Common.listArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (Common.listArrayList.size()<0)
        {
            Toast.makeText(getActivity(), "Please add service", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(serviceListAdapter);


        getActivity().findViewById(R.id.addnewcat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCatDialog();
            }
        });
    }

    private void showCatDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.service_list, null);

        final EditText s_name = view.findViewById(R.id.ser_name);
        final EditText s_price = view.findViewById(R.id.ser_price);
        Button save_service = view.findViewById(R.id.save_service);
        builder.setView(view);

        save_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service_name = s_name.getText().toString().trim();
                String service_price = s_price.getText().toString().trim();
                if (service_name.isEmpty()) {
                    s_name.setError("Invalid");
                    s_name.requestFocus();
                } else if (service_price.isEmpty()) {
                    s_price.setError("Invalid");
                    s_price.requestFocus();
                } else {
                    saveServiceToArrayList(service_name, service_price);
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog=builder.create();
        alertDialog.show();


    }

    private void saveServiceToArrayList(String s_name, String s_price) {

        Common.listArrayList.add(new ServiceList(s_name,s_price));
        serviceListAdapter.notifyDataSetChanged();



    }
}
