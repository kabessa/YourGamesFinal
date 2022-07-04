package com.example.yourgames;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yourgames.databinding.FragmentGamesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CadGames extends AppCompatActivity {

    String user_ID;
    private EditText game_name;
    private EditText price_name;
    private EditText desc_name;

    private EditText urlText;
    private EditText desenv_name;
    private Button button_cad;
    private Button button_img;
    private Uri selectedUri;
    private ImageView imgSelect;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_games);

        game_name = findViewById(R.id.game_name);
        price_name = findViewById(R.id.price_name);
        desc_name = findViewById(R.id.game_desc);
        urlText = findViewById(R.id.game_url);
        button_img = findViewById(R.id.button_select_img);
        button_cad = findViewById(R.id.button_cadastro);
        imgSelect = findViewById(R.id.imageSelected);




        button_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImg();
            }
        });

        button_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveGameData();
                Toast.makeText(CadGames.this, "Cadastrou ",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==0){
            selectedUri = data.getData();

            Bitmap bitmap = null;
            try {
               bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(),selectedUri);
               imgSelect.setImageDrawable(new BitmapDrawable(bitmap));
               button_img.setAlpha(0);
            }catch (IOException e){

            }
        }

    }

    public void SelectImg(){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,0);

    }









    // Método para Salvar os dados do game
    private void SaveGameData() {







        // Obtenção dos dados dos edit Text.
        String name = game_name.getText().toString();
        String price = price_name.getText().toString();
        String desc = desc_name.getText().toString();
        String urlLoja = urlText.getText().toString();





        //// Adicionando Imagem ao BD
        String fileName = name;




        StorageReference ref = FirebaseStorage.getInstance().getReference("/images/"+fileName);
        ref.putFile(selectedUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        Log.i("Url: ",uri.toString());
                            String url = uri.toString();


                        // Inicializando Firestore.
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        // Criando um Map para adicionar os dados do jogo.
                        Map<String, Object> game = new HashMap<>();
                        game.put("Nome", name);
                        game.put("Preco", price);
                        game.put("Descrição", desc);
                        game.put("Url Loja",urlLoja);
                        game.put("Url",url);




                        // busca o id do Usuário que está cadastrando.
                        user_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        db.collection("Loja").document(user_ID).collection("Jogos").document(name).set(game);
                        // Criando/ Buscndo uma coleção de usuários.
                        DocumentReference documentReference = db.collection("Usuários").document(user_ID);

                        documentReference.collection("Jogos").document(name).set(game).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.e("A operação falhou",e.getMessage(),e);
            }
        });
    }
}