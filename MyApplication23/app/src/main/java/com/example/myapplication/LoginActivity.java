package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.email);
        loginPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupRedirectText = findViewById(R.id.signupText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    String email = loginEmail.getText().toString().trim();
                    String password = loginPassword.getText().toString().trim();
                    loginUser(email, password);
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateFields() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Email cannot be empty");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmail.setError("Invalid email address");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Password cannot be empty");
            return false;
        }

        return true;
    }


private void loginUser(String email, String password) {
    // Check if the user is admin
    if (email.equals("admin@gmail.com") && password.equals("adminA77")){
        // Redirect to AdminActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(intent);
        return; // Exit the method to avoid Firebase authentication
    }

    // Proceed with Firebase authentication for regular users
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Login success
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this , HomeActivity.class);
                        startActivity(i);
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Authentication failed, Please verify your email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}
}
