package com.hunter.naiie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.naiie.R;
import com.hunter.naiie.adapter.MyAdapter;
import com.hunter.naiie.model.ItemData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,null);

        RecyclerView recyclerView= view.findViewById(R.id.hom_rv);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);

        // this is data fro recycler view
        ItemData itemsData[] = {
                new ItemData("Hair Cut", R.drawable.haircut),
                new ItemData("Hair Color", R.drawable.haircolor),
                new ItemData("Bleach", R.drawable.bleach),
                new ItemData("Eyebrow", R.drawable.eyebrowth),
                new ItemData("Hair Spa", R.drawable.hairspa),
                new ItemData("Trimming", R.drawable.beardtrimming)
        };
        //  create an adapter
        MyAdapter mAdapter = new MyAdapter(itemsData);
        //  set adapter
        recyclerView.setAdapter(mAdapter);
        // set layoutmanager
        recyclerView.setLayoutManager(layoutManager);
        //  set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;

    }




}
