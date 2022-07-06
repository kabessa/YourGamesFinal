package com.example.yourgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditGame extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        Button selectImg = findViewById(R.id.button_select_imgEdit);
        Button saveEdit   = findViewById(R.id.button_saveEdit);
        ImageView textImg     = findViewById(R.id.imageSelectedEdit);
        TextView titleT   = findViewById(R.id.name_Edit)   ;
        TextView textDesc = findViewById(R.id.desc_Edit)   ;
        TextView textUrl = findViewById(R.id.url_Edit)     ;
        TextView textpreco    = findViewById(R.id.price_Edit)  ;


        String imgVal = getIntent().getStringExtra("image") ;
        String title  = getIntent().getStringExtra ("title");
        String desc   = getIntent().getStringExtra  ("desc");
        String url    = getIntent().getStringExtra   ("url");
        String price  = getIntent().getStringExtra ("price");




        textUrl.setText(url);
        textpreco.setText(price);
        selectImg.setAlpha(0);

        Glide.with(this).load(imgVal).into(textImg);
        titleT.setText(title);
        textDesc.setText(desc);
        String oldtitle  = title;

        saveEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                String newName = titleT.getText().toString();
                String newDesc =  textDesc.getText().toString();
                String newPrice = textpreco.getText().toString();
                String newUrlLoja = textUrl.getText().toString();
                String newUrl = imgVal;


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String   user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // Criando um Map para Atualizar os dados do jogo.
                Map<String, Object> game = new HashMap<>();
                game.put("Nome", newName);
                game.put("Preco", newPrice);
                game.put("Descrição", newDesc);
                game.put("Url Loja",newUrlLoja);
                game.put("Url",newUrl);
                game.put("autor_id",user_ID);

                db.collection("Loja").document(oldtitle).set(game);
                // Criando/ Buscndo uma coleção de usuários.
                DocumentReference documentReference = db.collection("Usuários").document(user_ID);

                documentReference.collection("Jogos").document(oldtitle).set(game).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused)
                    {
                        Log.d("db_successful", "Dados salvos com sucesso");
                        //Adicionando o jogo a Loja
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("db_failure", "Erro ao salvar os dados" + e.toString());
                    }
                });








            }



        });
    }
}