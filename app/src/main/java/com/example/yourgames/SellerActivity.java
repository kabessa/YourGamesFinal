package com.example.yourgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SellerActivity extends AppCompatActivity {

    // Criando objetos.
    private EditText register_company;
    private EditText register_phone;
    private EditText register_key;
    private Button button_seller;

    // Criando um Array de String pra mensagens.
    String[] message = {"Preencha todos os campos", "Registro realizado com sucesso"};

    // Criando uma String para ID do usuário.
    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        // Parte responsável por exibir a tela em FullScreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Iniciando método StartComponents.
        StartComponents();

        // Pega a referência da Toolbar e seta a toolbar como um ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_seller);

        // Criando um evento de clique para redirecionar o usuário ao home.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Definindo um OnClick para o botão registrar.
        button_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Captura o que o usuário está digitando.
                String company = register_company.getText().toString();
                String phone = register_phone.getText().toString();
                String key = register_key.getText().toString();

                // Parte responsável por fazer as verificações.
                if (company.isEmpty() || phone.isEmpty() || key.isEmpty()) {

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {

                    // Chamando o método RegisterSeller.
                    RegisterSeller(view);
                }
            }
        });
    }

    // Conectando objetos a uma referência.
    private void StartComponents() {
        register_company = findViewById(R.id.register_company);
        register_phone = findViewById(R.id.register_phone);
        register_key = findViewById(R.id.register_key);
        button_seller = findViewById(R.id.button_seller);
    }

    // Criando um método pra salvar o nome do usuário.
    private void RegisterSeller(View view) {

        // Captura o que o usuário está digitando.
        String company = register_company.getText().toString();
        String phone = register_phone.getText().toString();
        String key = register_key.getText().toString();

        // Inicializando o banco de dados Firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Criando um Map para adicionar o nome do usuário.
        Map<String, Object> emp = new HashMap<>();
        emp.put("Empresa", company);
        Map<String, Object> tel = new HashMap<>();
        tel.put("Telefone", phone);
        Map<String, Object> cha = new HashMap<>();
        cha.put("Chave", key);

        // Pega o ID de cada usuário.
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Criando uma coleção de Usuários/Informações/Empresa e salva os dados do Map.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.collection("Informações").document("Empresa").set(emp).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        // Criando uma coleção de .../Informações/Telefone e salva os dados do Map.
        documentReference.collection("Informações").document("Telefone").set(tel).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        // Criando uma coleção de .../Informações/Chave e salva os dados do Map.
        documentReference.collection("Informações").document("Chave").set(cha).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        // Definindo um tempo e redirecionando o usuário para a tela UserActivity.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), UserActivity.class));
            }
        }, 3000);

        // Criando um Snackbar para exibir mensagens.
        Snackbar snackbar = Snackbar.make(view, message[1], Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }
}