package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Applicant> applicantsList;

    private ApplicantAdapter adapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.listView);
        applicantsList = new ArrayList<>();
        adapter = new ApplicantAdapter(this, R.layout.list_item_applicant, applicantsList);

        listView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Retrieve applicants from Firestore
        retrieveApplicants();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Applicant selectedApplicant = applicantsList.get(position);
                Intent intent = new Intent(MainActivity2.this, ApplicantDetailsActivity.class);
                intent.putExtra("applicant", selectedApplicant);
                startActivity(intent);
            }
        });
    }

//    private void retrieveApplicants() {
//        db.collection("applicants")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                // Retrieve applicant data
//                                String courseName = document.getString("courseName");
//                                String uid = document.getString("userId");
//
//                                // Ensure UID is not null or empty
//                                if (uid != null && !uid.isEmpty()) {
//                                    // Retrieve additional user information using UID
//                                    db.collection("users")
//                                            .document(uid)
//                                            .get()
//                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//                                                        DocumentSnapshot userDocument = task.getResult();
//                                                        if (userDocument.exists()) {
//                                                            String fullName = userDocument.getString("username");
//                                                            String phoneNumber = userDocument.getString("phoneNumber");
//
//                                                            // Create Applicant object and add to list
//                                                            Applicant applicant = new Applicant(fullName, courseName, phoneNumber);
//                                                            applicant.generateProfileImageResource(); // Set a unique profile pic based on user's full name
//                                                            applicantsList.add(applicant);
//                                                            adapter.notifyDataSetChanged();
//                                                        } else {
//                                                            Toast.makeText(MainActivity2.this, "User document does not exist", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    } else {
//                                                        Toast.makeText(MainActivity2.this, "Error retrieving user information: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//                                } else {
//                                    Toast.makeText(MainActivity2.this, "UID is null or empty", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } else {
//                            Toast.makeText(MainActivity2.this, "Error loading applicants: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
    private void retrieveApplicants() {
    db.collection("applicants")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        int totalUsers = 0; // Variable to store the total number of users
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve applicant data
                            String courseName = document.getString("courseName");
                            String uid = document.getString("userId");
                            totalUsers++; // Increment totalUsers for each applicant

                            // Ensure UID is not null or empty
                            if (uid != null && !uid.isEmpty()) {
                                // Retrieve additional user information using UID
                                int finalTotalUsers = totalUsers;
                                db.collection("users")
                                        .document(uid)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot userDocument = task.getResult();
                                                    if (userDocument.exists()) {
                                                        String fullName = userDocument.getString("username");
                                                        String phoneNumber = userDocument.getString("phoneNumber");

                                                        // Create Applicant object and add to list
                                                        Applicant applicant = new Applicant(fullName, courseName, phoneNumber);
                                                        applicant.generateProfileImageResource(finalTotalUsers); // Pass totalUsers to generateProfileImageResource
                                                        applicantsList.add(applicant);
                                                        adapter.notifyDataSetChanged();
                                                    } else {
                                                        Toast.makeText(MainActivity2.this, "User document does not exist", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(MainActivity2.this, "Error retrieving user information: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(MainActivity2.this, "UID is null or empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity2.this, "Error loading applicants: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
}
}