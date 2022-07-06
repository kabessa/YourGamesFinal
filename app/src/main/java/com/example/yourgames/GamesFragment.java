package com.example.yourgames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourgames.databinding.FragmentGamesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
//import com.example.yourgames.ui.gallery.GalleryFragment;

import java.util.ArrayList;
import java.util.Map;


public class GamesFragment extends Fragment  {

    private AdapterView.OnItemClickListener listener;
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView.LayoutManager layoutManager;
    RecycleViewAdapter recycleViewAdapter;
    ArrayList<String>icons = new ArrayList<>();
    ArrayList<String>names = new ArrayList<>();
    ArrayList<String>precos = new ArrayList<>();
    ArrayList<String>desc = new ArrayList<>();
    ArrayList<String>userId = new ArrayList<>();
    ArrayList<String>urlLoja = new ArrayList<>();
    String user_ID;


    private FragmentGamesBinding binding;


    @Override
    public void onStart() {
        super.onStart();


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_games,container,false);
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Parte responsável por recuperar os dados presente na biblioteca usuário com base na ID.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.collection("Jogos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                // Realizando verificações.
                if (documentSnapshot != null) {
                    names.clear();
                    precos.clear();
                    icons.clear();
                    desc.clear();
                    urlLoja.clear();
                    userId.clear();
                    for(DocumentSnapshot snapshot : documentSnapshot){

                        names.add(snapshot.getString("Nome"));
                        precos.add("Comprar R$ "+snapshot.getString("Preco"));
                        icons.add(snapshot.getString("Url"));
                        desc.add(snapshot.getString("Descrição"));
                        urlLoja.add(snapshot.getString("Url Loja"));
                        userId.add(snapshot.getString("autor_id"));
                    }
                    recyclerView = root.findViewById(R.id.recycler_view);
                    layoutManager = new GridLayoutManager(GamesFragment.this.getContext(),2);
                    recyclerView.setLayoutManager(layoutManager);
                    recycleViewAdapter = new RecycleViewAdapter(2,icons,names,precos,desc,urlLoja,userId);
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