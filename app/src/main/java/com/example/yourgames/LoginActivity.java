package com.example.yourgames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // Criando objetos.
    private EditText user_email;
    private EditText user_password;
    private Button button_login;
    private TextView register_link;

    // Criando um Array de String pra mensagens.
    String[] message = {"Preencha todos os campos", "Login efetuado com sucesso"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Parte responsável por exibir a tela em FullScreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Iniciando método StartComponents.
        StartComponents();

        // Definindo um OnClick para o botão entrar.
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Captura o que o usuário está digitando.
                String email = user_email.getText().toString();
                String password = user_password.getText().toString();

                // Parte responsável por fazer as verificações.
                if (email.isEmpty() || password.isEmpty()) {

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, message[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {

                    // Chamando o método AuthenticateUser.
                    AuthenticateUser(view);
                }
            }
        });

        // Definindo um OnClick e redirecionando o usuário para RegisterActivity.
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Conectando objetos a uma referência.
    private void StartComponents() {
        user_email = findViewById(R.id.game_name);
        user_password = findViewById(R.id.user_password);
        button_login = findViewById(R.id.button_cadastro);
        register_link = findViewById(R.id.register_link);
    }

    // Criando um método para redirecionar o usuário para tela principal após um tempo de tela.
    private void MainScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), HomeActivity.class));
                finish();
            }
        }, 3000);
    }

    // Criando um método para redirecionar o usuário para o HomeActivity caso ele já esteja logado.
    private void Logged() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    // Criando um método para autenticar o usuário.
    private void AuthenticateUser(View view) {

        // Captura o que o usuário está digitando.
        String email = user_email.getText().toString();
        String password = user_password.getText().toString();

        // Iniciar FirebaseAuth para logar com a conta usuário.
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // Validando e redirecionando o usuário para tela HomeActivity.
                if (task.isSuccessful()) {

                    // Chamando o método MainScreen.
                    MainScreen();

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, message[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }

                // Verificação com mensagem de erro.
                else {
                    String failure;
                    try {
                        throw task.getException();
                    }
                    catch (Exception exception) {
                        failure = "E-mail ou senha incorretos / Inexistentes";
                    }

                    // Criando um Snackbar para exibir mensagens.
                    Snackbar snackbar = Snackbar.make(view, failure, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    // Criando um ciclo de vida.
    @Override
    protected void onStart() {
        super.onStart();

        // Validação para ver se o usuário apresenta estar ou não logado.
        FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();

        if (userCurrent != null) {

            // Chamando o método Logged.
            Logged();
        }
    }
}