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
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    EditText inputemail, inputpassword;
    Button signup;
    FirebaseAuth mAuth;
    ProgressBar bar;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputemail = (EditText) findViewById(R.id.email);
        inputpassword = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.btnSingUp);
        bar = (ProgressBar) findViewById(R.id.pro);
        login = (TextView) findViewById(R.id.log);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                userSignUp();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void userSignUp(){

        String email = inputemail.getText().toString();
        String password = inputpassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            bar.setVisibility(View.GONE);
            Toast.makeText(SignUp.this, "Entre email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            bar.setVisibility(View.GONE);
            Toast.makeText(SignUp.this, "Entre password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        bar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            redirectToUserSpace();
                        } else {
                            Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void redirectToUserSpace() {

        String userId = mAuth.getCurrentUser().getUid();

        Intent intent = new Intent(SignUp.this, UserSpace.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }
}