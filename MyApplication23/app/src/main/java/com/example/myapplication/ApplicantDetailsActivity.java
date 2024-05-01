package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

// ApplicantDetailsActivity.java

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// ApplicantDetailsActivity.java

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ApplicantDetailsActivity extends AppCompatActivity {

    private ImageView imageViewProfile;
    private TextView textViewFullName, textViewCourse , textviewNumber;
    private Button buttonCall, buttonMessage;

    private Applicant selectedApplicant;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewFullName = findViewById(R.id.textViewFullName);
        textViewCourse = findViewById(R.id.textViewCourse);
        textviewNumber = findViewById(R.id.textviewNumber);
        buttonCall = findViewById(R.id.callbtn);
        buttonMessage = findViewById(R.id.msgbtn);

        // Retrieve selected applicant from intent
        selectedApplicant = getIntent().getParcelableExtra("applicant");

        // Display details of selected applicant
        if (selectedApplicant != null) {
            // Load profile image using Glide or Picasso
            // imageViewProfile.setImage...

            textViewFullName.setText(selectedApplicant.getFullName());
            textViewCourse.setText(selectedApplicant.getCourse());
            textviewNumber.setText("0"+ selectedApplicant.getNumber());

            // Handle call button click
            buttonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Make a phone call
                    String phoneNumber = "0"+selectedApplicant.getNumber();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            });

            // Handle message button click
            buttonMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Send a message
                    String phoneNumber ="0"+ selectedApplicant.getNumber();
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + phoneNumber));
                    startActivity(intent);
                }
            });
        } else {
            // Handle case where no applicant data is received
            Toast.makeText(this, "No applicant data found", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if no data is available
        }
    }
}