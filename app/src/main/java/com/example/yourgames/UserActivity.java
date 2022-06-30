package com.example.yourgames;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;

public class UserActivity extends AppCompatActivity {

    // Criando objetos.
    private ImageView ic_photo;
    private ImageView user_photo;
    private ImageView save_photo;
    private Uri selected_uri;
    private TextView save_user;
    private TextView save_email;
    private Button button_logout;

    // Inicializando o banco de dados Firestore.
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Parte responsável por exibir a tela em FullScreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Iniciando método StartComponents.
        StartComponents();

        // Pega a referência da Toolbar e seta a toolbar como um ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        // Criando um evento de clique para redirecionar o usuário ao home.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Criando um evento de clique para carregar uma imagem da galeria.
        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Chamando o método SelectPhoto.
                SelectPhoto();
            }
        });

        // Criando um evento de clique para o botão deslogar.
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Método para sair da sessão e redirecionando o usuário pra tela de login.
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Conectando objetos a uma referência.
    private void StartComponents() {
        ic_photo = findViewById(R.id.ic_photo);
        user_photo = findViewById(R.id.user_photo);
        save_photo = findViewById(R.id.save_photo);
        save_user = findViewById(R.id.save_user);
        save_email = findViewById(R.id.save_email);
        button_logout = findViewById(R.id.button_logout);
    }

    // Parte responsável por inflar o menu com os botões do ActionBar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    // Parte responsável por dar função a cada item presente no Toolbar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Pega o ID do item no menu.
        int id = item.getItemId();

        // Condições que especificam o que cada item ao ser clicado deve fazer.
        if (id == R.id.action_change) {

            // Mostrando uma mensagem pro usuário e o redirecionando para tela.
            Toast.makeText(this, "Alterar Dados", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserActivity.this, ChangeActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //
        if (requestCode == 0) {
            selected_uri = data.getData();

            //
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selected_uri);
                save_photo.setImageDrawable(new BitmapDrawable(bitmap));
                user_photo.setAlpha(0);
                ic_photo.setVisibility(View.INVISIBLE);
            } catch (IOException e) {

            }
        }
    }

    //
    private void SelectPhoto() {

        //
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    // Criando um ciclo de vida para quando iniciado já buscar os dados no BD.
    @Override
    protected void onStart() {
        super.onStart();

        // Pega o ID de cada usuário.
        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Pega o email de cada usuário.
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Parte responsável por recuperar os dados presente na biblioteca usuário com base na ID.
        DocumentReference documentReference = db.collection("Usuários").document(user_ID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                // Realizando verificações.
                if (documentSnapshot != null) {
                    save_user.setText(documentSnapshot.getString("Nome"));
                    save_email.setText(email);
                }
            }
        });
    }
}