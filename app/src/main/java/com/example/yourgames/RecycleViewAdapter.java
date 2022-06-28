package com.example.yourgames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{

    public RecycleViewAdapter(ArrayList arr, ArrayList arr2, ArrayList arr3, Fragment frag) {
        this.array1 = arr;
        this.array2 = arr2;
        this.array3 = arr3;
        this.fragment = frag;

    }
    ArrayList<Integer>array1 = new ArrayList<>();
    ArrayList<String>array2 = new ArrayList<>();
    ArrayList<String>array3 = new ArrayList<>();
    Fragment fragment;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder =  new MyViewHolder(view);

        return myViewHolder;





    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(array1.get(position));
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
        holder.button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Favoritou "+holder.textView.getText(),Toast.LENGTH_SHORT).show();
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

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    Toast.makeText(itemView.getContext(), "clicou ",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
