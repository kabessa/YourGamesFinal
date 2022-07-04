package com.example.yourgames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourgames.databinding.FragmentGamesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class StoreFragment extends Fragment {

    private AdapterView.OnItemClickListener listener;
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView.LayoutManager layoutManager;
    RecycleViewAdapter recycleViewAdapter;
    ArrayList<String>icons = new ArrayList<>();
    ArrayList<String>names = new ArrayList<>();
    ArrayList<String>precos = new ArrayList<>();
    ArrayList<String>desc = new ArrayList<>();
    ArrayList<String>urlLoja = new ArrayList<>();
    String user_ID;
    private FragmentGamesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_games,container,false);
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Realizar a busca de dados na coleção Loja
         db.collection("Loja").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                // Realizando verificações.
                if (documentSnapshot != null) {
                    names.clear();
                    precos.clear();
                    icons.clear();
                    desc.clear();
                    urlLoja.clear();
                    for(DocumentSnapshot snapshot : documentSnapshot){

                        names.add(snapshot.getString("Nome"));
                        precos.add("Comprar R$ "+snapshot.getString("Preco"));
                        icons.add(snapshot.getString("Url"));
                        desc.add(snapshot.getString("Descrição"));
                        urlLoja.add(snapshot.getString("Url Loja"));
                    }
                    recyclerView = root.findViewById(R.id.recycler_view);
                    layoutManager = new GridLayoutManager(StoreFragment.this.getContext(),2);
                    recyclerView.setLayoutManager(layoutManager);
                    recycleViewAdapter = new RecycleViewAdapter(2,icons,names,precos,desc,urlLoja);
                    recyclerView.setAdapter(recycleViewAdapter);

                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}