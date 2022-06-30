package com.example.yourgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    // Criando objetos.
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Parte responsável por exibir a tela em FullScreen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Pega a referência da Toolbar e seta a toolbar como um ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        // Cria um evento de clique para o ícone de navegação.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        // Chamando o método setNavigationDrawer.
        setNavigationDrawer();
    }

    // Parte responsável por inflar o menu com os botões do ActionBar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // Parte responsável por dar função a cada item presente no Toolbar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Pega o ID do item no menu.
        int id = item.getItemId();

        // Condições que especificam o que cada item ao ser clicado deve fazer.
        if (id == R.id.action_account) {

            // Redirecionando o usuário para a tela UserActivity.
            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Parte responsável por dar função a cada item presente no NavigationView.
    private void setNavigationDrawer() {

        // Inicializando o DrawerLayout.
        drawerLayout = findViewById(R.id.drawer_layout);

        // Inicializando o NavigationView.
        NavigationView navigationView = findViewById(R.id.navigation_page);

        // Implementa o evento setNavigationItemSelectedListener do NavigationView.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // Seta o fragment Igual a null.
                Fragment fragment = null;

                // Pega o ID do item no menu.
                int itemId = menuItem.getItemId();

                // Pega a referência da Toolbar.
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);

                // Checa o ID selecionado e substitui por um fragmento e seta o título do Toolbar.
                if (itemId == R.id.store_item) {
                    fragment = new StoreFragment();
                    toolbar.setTitle("Loja");
                }
                else if (itemId == R.id.favorite_item) {
                    fragment = new FavoriteFragment();
                    toolbar.setTitle("Favoritos");
                }
                else if (itemId == R.id.games_item) {
                    fragment = new GamesFragment();
                    toolbar.setTitle("Meus Jogos");
                }
                else if (itemId == R.id.add_item) {
                    Intent intent = new Intent(HomeActivity.this,CadGames.class);
                    startActivity(intent);
                }

                // Parte responsável por exibir um FrameLayout caso não haja nenhum fragmento selecionado.
                if (fragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Substitui um Fragmento por FrameLayout e comita as alterações.
                    transaction.replace(R.id.constraint_layout, fragment);
                    transaction.commit();

                    // Fecha todas as DrawerViews abertas.
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }
}