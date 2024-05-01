package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CourseRegistration extends AppCompatActivity {
    Button submitBtn;
    TextView coursenameInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);
        submitBtn = findViewById(R.id.submitBtn);
        coursenameInput = findViewById(R.id.coursenameInput);

        Intent i = getIntent();
        String crsname = i.getStringExtra("courseName");
        coursenameInput.setText(crsname);
        coursenameInput.setEnabled(false);

        // Get references to the spinners
        Spinner levelSpinner = findViewById(R.id.levelSpinner);
        Spinner roleSpinner = findViewById(R.id.roleSpinner);

        // Define arrays containing options for the spinners
        String[] levelOptions = {"Bacalaureat", "Bac+2","Bac+4", "Bac+3", "Bac+5", "Master","Professional Certification"};
        String[] roleOptions = {"Student","Engineer","Developer", "Designer", "Scientist", "Teacher", "Consultant", "Manager", "Entrepreneur", "Administrator", "Architect", "Artist", "Writer", "Technician", "Coordinator", "Specialist", "Investigator" };

        // Create adapters and set them to the spinners
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, levelOptions);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, roleOptions);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String level = levelSpinner.getSelectedItem().toString();
                String role = roleSpinner.getSelectedItem().toString();
                String courseName = coursenameInput.getText().toString();
                String university = ""; // Add university retrieval if applicable

                // Get user ID from Firebase Authentication
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user != null ? user.getUid() : "";

                // Create a new document in the "applicants" collection
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> applicantData = new HashMap<>();
                applicantData.put("courseName", courseName);
                applicantData.put("level", level);
                applicantData.put("role", role);
                applicantData.put("university", university);
                applicantData.put("userId", userId);

                db.collection("applicants")
                        .add(applicantData)
                        .addOnSuccessListener(documentReference -> {
                            // Show a toast message indicating successful submission
                            Toast.makeText(CourseRegistration.this, "Course entered successfully", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle any errors
                            Log.e("EntercourseActivity", "Error adding document", e);
                            Toast.makeText(CourseRegistration.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

    }
}