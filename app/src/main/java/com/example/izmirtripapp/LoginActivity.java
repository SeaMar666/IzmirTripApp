package com.example.izmirtripapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button signupButton, loginBtn;
    TextInputLayout usernameText, passwordText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        usernameText = findViewById(R.id.usernameLogin);
        passwordText = findViewById(R.id.passwordLogin);

        signupButton = findViewById(R.id.signup_screen);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    private void userLogin() {
        String userEnteredUsername = usernameText.getEditText().getText().toString().trim();
        String userEnteredPassword = passwordText.getEditText().getText().toString().trim();

        if (userEnteredUsername.isEmpty()) {
            usernameText.setError("Email is required.");
            usernameText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEnteredUsername).matches()){
            usernameText.setError("Please enter a valid email.");
            usernameText.requestFocus();
            return;
        }

        if (userEnteredPassword.isEmpty()) {
            passwordText.setError("Email is required.");
            passwordText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEnteredUsername, userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomePage.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"Login successful!", Toast.LENGTH_LONG).show();

                }else
                    Log.e("Login Error", "signInWithEmailAndPassword", task.getException());
                Toast.makeText(LoginActivity.this,"Failed to login!", Toast.LENGTH_LONG).show();

                System.out.println(userEnteredUsername);
                System.out.println(userEnteredPassword);

            }
        });

        signupButton = findViewById(R.id.signup_screen);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}