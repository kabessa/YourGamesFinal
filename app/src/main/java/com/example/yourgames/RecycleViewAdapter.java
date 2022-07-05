package com.example.yourgames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userName;
    public RecycleViewAdapter(int funcId, ArrayList arr, ArrayList arr2, ArrayList arr3, ArrayList arr4, ArrayList arr5, ArrayList arr6) {
        this.images = arr;
        this.names = arr2;
        this.price = arr3;
        this.desc = arr4;
        this.array5 = arr5;
        this.userID = arr6;
        this.id = funcId;
    }
    ArrayList<String> images;
    ArrayList<String> names;
    ArrayList<String> price;
    ArrayList<String> desc;
    ArrayList<String>array5;
    ArrayList<String>userID;
    int id;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder =  new MyViewHolder(view);

        return myViewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Glide.with(holder.itemView.getContext()).load(images.get(position)).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DocumentReference documentReference = db.collection("Usuários").document(user_ID);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                        if(id ==2)
                        {
                            userName = documentSnapshot.getString("Nome");
                            Intent intent = new Intent(holder.textView.getContext(), DeleteGame.class);
                            intent.putExtra("image", images.get(position));
                            intent.putExtra("title", names.get(position));
                            intent.putExtra("desc", desc.get(position));
                            intent.putExtra("url", array5.get(position));
                            intent.putExtra("price", price.get(position));
                            intent.putExtra("userID", userName);
                            holder.itemView.getContext().startActivity(intent);

                        }else
                        {
                            userName = documentSnapshot.getString("Nome");
                            Intent intent = new Intent(holder.textView.getContext(), Informacoes.class);
                            intent.putExtra("image", images.get(position));
                            intent.putExtra("title", names.get(position));
                            intent.putExtra("desc", desc.get(position));
                            intent.putExtra("url", array5.get(position));
                            intent.putExtra("price", price.get(position));
                            intent.putExtra("userID", userName);
                            holder.itemView.getContext().startActivity(intent);

                        }

                        }
                });



            }
        });

        holder.textView.setText(names.get(position));
        holder.button1.setText(price.get(position));
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);


                intent.setData(Uri.parse(array5.get(position)));
                holder.itemView.getContext().startActivity(intent);

            }
        });



        holder.button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                if(id ==1){
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String  user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        db.collection("Usuários").document(user_ID).collection("Favoritos").document( names.get(position) ).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>()
                                {
                                    @Override
                                    public void onSuccess(Void aVoid)
                                    {
                                        Log.d("Sucess","Documento excluído com sucessp");

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener()
                                {
                                    @Override
                                    public void onFailure(@NonNull Exception e)
                                    {
                                        Log.w("Falha ao excluir","O documento não pôde ser excluído", e);
                                    }
                                });


                }else{
                    String name = holder.textView.getText().toString();

                    // Inicializando Firestore.
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Criando um Map para adicionar os dados do jogo.
                    Map<String, Object> game = new HashMap<>();
                    game.put("Nome", name);
                    game.put("Preco", price.get(position));
                    game.put("Descrição", desc.get(position));
                    game.put("Url", images.get(position));

                    // busca o id do Usuário que está cadastrando.
                    String user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    // Criando uma coleção de usuários.
                    DocumentReference documentReference = db.collection("Usuários").document(user_ID);

                    documentReference.collection("Favoritos").document(name).set(game).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("db_successful", "Dados salvos com sucesso");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("db_failure", "Erro ao salvar os dados" + e);
                        }
                    });

                    Toast.makeText(v.getContext(), "Favoritou "+holder.textView.getText(),Toast.LENGTH_SHORT).show();

                }


            }


        });


    }

    @Override
    public int getItemCount() {
        return images.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        Button button1;
        Button button2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

                textView = itemView.findViewById(R.id.textView2);
                imageView = itemView.findViewById(R.id.imageView);
                button1 = itemView.findViewById(R.id.button);
                button2 = itemView.findViewById(R.id.button2);



        }
    }
}
