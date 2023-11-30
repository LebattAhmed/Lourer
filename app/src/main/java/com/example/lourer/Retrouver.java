package com.example.lourer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Retrouver extends AppCompatActivity {

    private EditText editTextRecherche;
    private Button btnRechercher;
    private  Button btnRetoure;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrouver);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextRecherche = findViewById(R.id.editTextNom);
        btnRechercher = findViewById(R.id.btnTrouver);
        btnRetoure = findViewById(R.id.btnRetour);

        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechercherObjet();
            }
        });

        btnRetoure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(Retrouver.this, UserSpace.class);
                startActivity(r);
                finish();
            }
        });
    }

    private void rechercherObjet() {

        String recherche = editTextRecherche.getText().toString();

        if (mAuth.getCurrentUser() != null) {

            db.collection("Objects").whereEqualTo("nom", recherche).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                String nomObjet = documentSnapshot.getString("nom");
                                String numeroTelephone = documentSnapshot.getString("numero");

                                envoyerSMS(numeroTelephone, "Votre objet "+ nomObjet + " a été retrouvé. Contacter moi pour le prendre ");
                                showToast("Message envoyer avec success");

                            }

                            if (queryDocumentSnapshots.isEmpty()) {
                                showToast("l'objet est vide !");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToast("Échec l'objet n'existe pas");
                        }
                    });
        }
    }

    private void envoyerSMS(String numeroTelephone, String message) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numeroTelephone, null, message, null, null);
    }

    private void showToast(String message) {
        Toast.makeText(Retrouver.this, message, Toast.LENGTH_SHORT).show();
    }
}