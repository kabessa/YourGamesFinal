package com.example.yourgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Parte responsável por exibir a tela em FullScreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Pega a referência da Toolbar e seta a toolbar como um ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_help);

        // Criando um evento de clique para redirecionar o usuário ao home.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}