package com.example.yourgames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;


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

public class FavoriteFragment extends Fragment {

    private AdapterView.OnItemClickListener listener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
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

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_favorite,container,false);

        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // Parte responsável por recuperar os dados presente na biblioteca usuário com base na ID.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.collection("Favoritos").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        precos.add(snapshot.getString("Preco"));
                        icons.add(snapshot.getString("Url"));
                        desc.add(snapshot.getString("Descrição"));
                        urlLoja.add(snapshot.getString("Url Loja"));
                    }
                    recyclerView = root.findViewById(R.id.recycler_viewFavorite);
                    layoutManager = new GridLayoutManager(FavoriteFragment.this.getContext(),2);
                    recyclerView.setLayoutManager(layoutManager);
                    recycleViewAdapter = new RecycleViewAdapter(1,icons,names,precos,desc,urlLoja);

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