package com.example.yourgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ChangeActivity extends AppCompatActivity {

    // Criando objetos.
    private EditText change_name;
    private EditText change_company;
    private EditText change_phone;
    private ImageView button_nome;
    private ImageView button_company;
    private ImageView button_phone;

    // Criando uma String para ID do usuário.
    String user_ID;

    // Criando um Array de String pra mensagens.
    String[] message = {"Preencha o campo para alterar", "Nome alterado com sucesso", "Empresa alterada com sucesso", "Telefone alterado com sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        // Parte responsável por exibir a tela em FullScreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Iniciando método StartComponents.
        StartComponents();

        // Pega a referência da Toolbar e seta a toolbar como um ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_change);
        setSupportActionBar(toolbar);

        // Criando um evento de clique para redirecionar o usuário ao user.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        // Definindo um OnClick para o botão alterar nome.
        button_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Captura o que o usuário está digitando.
                String name = change_name.getText().toString();

                // Parte responsável por fazer as verificações.
                if (name.isEmpty()) {

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
                    ChangeName(view);
                }
            }
        });

        // Definindo um OnClick para o botão alterar empresa.
        button_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Captura o que o usuário está digitando.
                String company = change_company.getText().toString();

                // Parte responsável por fazer as verificações.
                if (company.isEmpty()) {

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
                    ChangeCompany(view);
                }
            }
        });

        // Definindo um OnClick para o botão alterar telefone.
        button_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Captura o que o usuário está digitando.
                String phone = change_phone.getText().toString();

                // Parte responsável por fazer as verificações.
                if (phone.isEmpty()) {

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
                    ChangePhone(view);
                }
            }
        });
    }

    // Conectando objetos a uma referência.
    private void StartComponents() {
        change_name = findViewById(R.id.change_name);
        change_company = findViewById(R.id.change_company);
        change_phone = findViewById(R.id.change_phone);
        button_nome = findViewById(R.id.button_nome);
        button_company = findViewById(R.id.button_company);
        button_phone = findViewById(R.id.button_phone);
    }

    // Criando um método pra alterar o nome usuário.
    private void ChangeName(View view) {

        // Captura o que o usuário está digitando.
        String name = change_name.getText().toString();

        // Inicializando o banco de dados Firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criando um Map para adicionar o nome do usuário.
        Map<String, Object> change = new HashMap<>();
        change.put("Nome", name);

        // Pega o ID de cada usuário.
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Criando uma coleção de usuários.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.collection("Informações").document("Nome").set(change).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db_successful", "Dados salvos com sucesso");

                // Criando um Snackbar para exibir mensagens.
                Snackbar snackbar = Snackbar.make(view, message[1], Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_failure", "Erro ao salvar os dados" + e.toString());
            }
        });
    }

    // Criando um método pra alterar a empresa.
    private void ChangeCompany(View view) {

        // Captura o que o usuário está digitando.
        String company = change_company.getText().toString();

        // Inicializando o banco de dados Firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criando um Map para adicionar a empresa.
        Map<String, Object> change = new HashMap<>();
        change.put("Empresa", company);

        // Pega o ID de cada usuário.
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Criando uma coleção de Companhias.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.collection("Informações").document("Empresa").set(change).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db_successful", "Dados salvos com sucesso");

                // Criando um Snackbar para exibir mensagens.
                Snackbar snackbar = Snackbar.make(view, message[2], Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_failure", "Erro ao salvar os dados" + e.toString());
            }
        });
    }

    // Criando um método pra alterar o telefone.
    private void ChangePhone(View view) {

        // Captura o que o usuário está digitando.
        String phone = change_phone.getText().toString();

        // Inicializando o banco de dados Firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criando um Map para adicionar o telefone.
        Map<String, Object> change = new HashMap<>();
        change.put("Telefone", phone);

        // Pega o ID de cada usuário.
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Criando uma coleção de Contatos.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.collection("Informações").document("Telefone").set(change).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db_successful", "Dados salvos com sucesso");

                // Criando um Snackbar para exibir mensagens.
                Snackbar snackbar = Snackbar.make(view, message[3], Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_failure", "Erro ao salvar os dados" + e.toString());
            }
        });
    }
}