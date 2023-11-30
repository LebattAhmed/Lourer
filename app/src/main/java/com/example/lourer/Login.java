package com.example.lourer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText inputemail, inputpassword;
    Button signin;
    FirebaseAuth mAuth;
    ProgressBar bar;
    TextView regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputemail = (EditText) findViewById(R.id.email);
        inputpassword = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.btnLogin);
        bar = (ProgressBar) findViewById(R.id.pro);
        regis = (TextView) findViewById(R.id.reg);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            redirectToUserSpace();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                userSignIn();
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void userSignIn(){

        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            bar.setVisibility(View.GONE);
            Toast.makeText(Login.this, "Entre email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            bar.setVisibility(View.GONE);
            Toast.makeText(Login.this, "Entre password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        bar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            redirectToUserSpace();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void redirectToUserSpace() {

        String userId = mAuth.getCurrentUser().getUid();

        Intent intent = new Intent(Login.this, UserSpace.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}