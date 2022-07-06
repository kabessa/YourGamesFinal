package com.example.yourgames;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yourgames.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Informacoes extends AppCompatActivity {



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);


        ImageView img = findViewById(R.id.imageGame);
        TextView titleT = findViewById(R.id.textTitle);
        TextView textDesc = findViewById(R.id.texDescricao);
        TextView txtAutor = findViewById(R.id.textAutor);
        Button comprar = findViewById(R.id.buttonComprarInf);
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

        comprar.setText(price);
        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });



    }


}