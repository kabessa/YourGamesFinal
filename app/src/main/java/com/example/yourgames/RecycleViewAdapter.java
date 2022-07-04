package com.example.yourgames;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{

    public RecycleViewAdapter(int funcId,ArrayList arr, ArrayList arr2, ArrayList arr3, ArrayList arr4, ArrayList arr5) {
        this.array1 = arr;
        this.array2 = arr2;
        this.array3 = arr3;
        this.array4 = arr4;
        this.array5 = arr5;
        this.id = funcId;
    }
    ArrayList<String>array1;
    ArrayList<String>array2;
    ArrayList<String>array3;
    ArrayList<String>array4;
    ArrayList<String>array5;
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
     //   holder.imageView.setImageResource(array1.get(position));

        Glide.with(holder.itemView.getContext()).load(array1.get(position)).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.textView.getContext(), Informacoes.class);
                intent.putExtra("image",array1.get(position));
                intent.putExtra("title",array2.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.textView.setText(array2.get(position));
        holder.button1.setText(array3.get(position));
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
                        FirebaseFirestore db;
                        db = FirebaseFirestore.getInstance();
                        String  user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        db.collection("Usuários").document(user_ID).collection("Favoritos").document( array2.get(position) ).delete()
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
                    game.put("Preco", array3.get(position));
                    game.put("Descrição", array4.get(position));
                    game.put("Url",array1.get(position));

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
        return array1.size();
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
