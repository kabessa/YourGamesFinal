package com.example.yourgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourgames.databinding.FragmentGamesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadGames extends AppCompatActivity {

    String user_ID;
    private EditText game_name;
    private Button button_cad;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_games);

        game_name = findViewById(R.id.game_name);
        button_cad = findViewById(R.id.button_cadastro);


        button_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveGameData();
                Toast.makeText(CadGames.this, "Favoritou ",Toast.LENGTH_SHORT).show();

            }
        });

    }









    // Método para Salvar os dados do game
    private void SaveGameData() {

        // Captura o que o usuário está digitando.
        String name = game_name.getText().toString();

        // Inicializando Firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criando um Map para adicionar os dados do jogo.
        Map<String, Object> game = new HashMap<>();
        game.put("Nome", name);



        // busca o id do Usuário que está cadastrando.
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Criando uma coleção de usuários.
        DocumentReference documentReference = db.collection("Games").document(user_ID);
        documentReference.set(game).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db_successful", "Dados salvos com sucesso");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_failure", "Erro ao salvar os dados" + e.toString());
            }
        });
    }
}