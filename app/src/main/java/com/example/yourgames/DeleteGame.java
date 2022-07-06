package com.example.yourgames;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


public class DeleteGame extends AppCompatActivity {



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_game);


        ImageView img = findViewById(R.id.imageGameDel);
        TextView titleT = findViewById(R.id.textTitleDel);
        TextView textDesc = findViewById(R.id.texDescricaodel);
        TextView txtAutor = findViewById(R.id.textAutordel);
        Button delete = findViewById(R.id.buttonDelete);
        Button edit = findViewById(R.id.edit);

        String url;

        String title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        String desc = getIntent().getStringExtra("desc");
        String price = getIntent().getStringExtra("price");
        String imgVal = getIntent().getStringExtra("image");
        String userName = getIntent().getStringExtra("userID");


        Glide.with(this).load(imgVal).into(img);
        titleT.setText(title);
        txtAutor.setText(userName);
        textDesc.setText(desc);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                String  user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                db.collection("Usuários").document(user_ID).collection("Jogos").document( title ).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Log.d("Sucess","Documento excluído com sucessp");

                                db.collection("Loja").document(title).delete();
                                db.collection("Usuários").document(user_ID).collection("Favoritos").document( title ).delete();


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
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteGame.this, EditGame.class);
                intent.putExtra("image",imgVal);
                intent.putExtra("title", titleT.getText());
                intent.putExtra("desc", textDesc.getText());
                intent.putExtra("url", url);
                intent.putExtra("price", price);

                startActivity(intent);
            }
        });



    }


}