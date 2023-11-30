package com.example.lourer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Poster extends AppCompatActivity {

    private EditText editNom;
    private EditText editDescription;
    private EditText editTelephone;
    private Button btnPoster;
    private  Button btnRetoure;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editNom = findViewById(R.id.editTextNom);
        editDescription = findViewById(R.id.editTextDescription);
        editTelephone = findViewById(R.id.telephone);
        btnPoster = findViewById(R.id.btnPoster);
        btnRetoure = findViewById(R.id.btnRetour);

        TextView exp = (TextView) findViewById(R.id.textViewExplanation);
        exp.getText();

        btnPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAuth.getCurrentUser() != null) {

                    String userId = mAuth.getCurrentUser().getUid();
                    String nom = editNom.getText().toString();
                    String desc = editDescription.getText().toString();
                    String tel = editTelephone.getText().toString();

                    Objects objects = new Objects(userId,nom,desc,tel);

                    db.collection("Objects").add(objects)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    showToast("Objet poster avec succès!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showToast("Échec de la declaration de l'objet. Réessayez.");
                                }
                            });
                }
            }
        });

        btnRetoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(Poster.this, UserSpace.class);
                startActivity(r);
                finish();
            }
        });


    }

    private void showToast(String message) {
        Toast.makeText(Poster.this, message, Toast.LENGTH_SHORT).show();
    }
}