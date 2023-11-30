package com.example.lourer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserSpace extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_space);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        TextView expl = findViewById(R.id.textViewExplanation);
        expl.getText();
        TextView bnv = findViewById(R.id.bienvenu);
        bnv.getText();

        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnpost = findViewById(R.id.btnPoste);
        Button btnretrouve = findViewById(R.id.btnTrouve);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(UserSpace.this, Poster.class);
                startActivity(p);
                finish();
            }
        });

        btnretrouve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent t = new Intent(UserSpace.this, Retrouver.class);
               startActivity(t);
               finish();
            }
        });
    }

    private void logoutUser() {
        mAuth.signOut();
        Intent i = new Intent(UserSpace.this, Login.class);
        startActivity(i);
        finish();
    }
}