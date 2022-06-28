package com.example.yourgames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourgames.databinding.FragmentGamesBinding;


import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private AdapterView.OnItemClickListener listener;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecycleViewAdapter recycleViewAdapter;
    ArrayList<Integer>icons = new ArrayList<>();
    ArrayList<String>names = new ArrayList<>();
    ArrayList<String>precos = new ArrayList<>();

    private FragmentGamesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_games,container,false);

        icons.add(R.drawable.arkimage);
        icons.add(R.drawable.miranhaimg);


        names.add("ARK: Survival Evolved");
        names.add("Homem-Aranha");

        precos.add("Comprar R$ 250,00");
        precos.add("Comprar R$ 250,00");


        recyclerView = root.findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(this.getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new RecycleViewAdapter(icons,names,precos,this);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}