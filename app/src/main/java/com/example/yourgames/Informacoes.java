package com.example.yourgames;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourgames.R;


public class Informacoes extends AppCompatActivity {


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);


        ImageView img = findViewById(R.id.imageGame);

        TextView text = findViewById(R.id.textTitle);


        String title = getIntent().getStringExtra("title");
        int imgVal = getIntent().getIntExtra("image",R.drawable.arkimage);

        img.setImageResource(imgVal);
        text.setText(title);
    }


}