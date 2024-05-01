package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText signupUsername, signupPhone, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUsername = findViewById(R.id.username);
        signupPhone = findViewById(R.id.phone);
        signupEmail = findViewById(R.id.email);
        signupPassword = findViewById(R.id.password);
        loginRedirectText = findViewById(R.id.signupText);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("SignupActivity", "Sign Up button clicked");
                            if (validateInputs()) {
                                Log.d("SignupActivity", "Inputs validated");
                                final String username = signupUsername.getText().toString().trim();
                                final String phone = signupPhone.getText().toString().trim();
                                final String email = signupEmail.getText().toString().trim();
                                final String password = signupPassword.getText().toString().trim();

                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("SignupActivity", "User successfully registered with email: " + email);
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    if (user != null) {
                                                        Log.d("SignupActivity", "User UID: " + user.getUid());
                                                        // Store additional user data in Firestore
                                                        DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(user.getUid());

                                                        Map<String, Object> userData = new HashMap<>();
                                                        userData.put("username", username);
                                                        userData.put("phoneNumber", phone);
//                                                        userData.put("email", email);

                                                        userRef.set(userData)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Log.d("SignupActivity", "User data successfully added to Firestore");
                                                                        // User data saved successfully
                                                                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                                                        startActivity(i);
                                                                        Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        // Handle failure to save user data
                                                                        Log.e("SignupActivity", "Error adding document", e);
                                                                        Toast.makeText(SignupActivity.this, "Registration failed: Error adding user data to Firestore", Toast.LENGTH_LONG).show();
                                                                    }
                                                                });

                                                    }
                                                } else {
                                                    Exception exception = task.getException();
                                                    if (exception != null) {
                                                        Log.e("SignupActivity", "createUserWithEmailAndPassword:failure", exception);
                                                        Toast.makeText(SignupActivity.this, "Registration failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Log.e("SignupActivity", "createUserWithEmailAndPassword:failure - Unknown error");
                                                        Toast.makeText(SignupActivity.this, "Registration failed: Unknown error", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    });


                    loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs() {
        String username = signupUsername.getText().toString().trim();
        String phone = signupPhone.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        signupUsername.setError(null);
        signupPhone.setError(null);
        signupEmail.setError(null);
        signupPassword.setError(null);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Invalid email address. Please enter a valid email.");
            signupEmail.requestFocus();
            return false;
        }

        if (!TextUtils.isDigitsOnly(phone) || phone.length() != 10) {
            signupPhone.setError("Invalid phone number. Please enter a valid 10-digit phone number.");
            return false;
        }

        if (password.length() < 6) {
            signupPassword.setError("Password should be at least 6 characters long.");
            return false;
        }

        if (!password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[A-Z]).{8,}$")) {
            signupPassword.setError("Password must contain at least 8 characters, including one letter, one number, and one uppercase letter.");
            return false;
        }

        if (username.length() < 4 || username.length() > 20) {
            signupUsername.setError("Username should be between 4 and 20 characters.");
            return false;
        }

        return true;
    }
}
