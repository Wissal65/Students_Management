package com.example.myapplication;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        super(context, 0, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Course course = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_course, parent, false);
        }

        // Lookup view for data population
        TextView textViewCourseName = convertView.findViewById(R.id.textViewCourseName);
        TextView textViewDuration = convertView.findViewById(R.id.textViewDuration);
        Button buttonRegister = convertView.findViewById(R.id.entercoursebtn); // Changed button id
        ImageView imageViewCoursePhoto = convertView.findViewById(R.id.imageViewCoursePhoto);

        // Populate the data into the template view using the data object
        if (course != null) {
            textViewCourseName.setText(course.getName());
            textViewDuration.setText(course.getDuration());
            imageViewCoursePhoto.setImageResource(course.getPhotoResourceId());
        }

        // Set click listener for the enter course button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                Toast.makeText(getContext(), "Button clicked for course: " + course.getName(), Toast.LENGTH_SHORT).show();

                // Optionally, start the activity here
                Intent intent = new Intent(getContext(), CourseRegistration.class);
                // Pass any necessary data to the activity
                intent.putExtra("courseName", course.getName());
                getContext().startActivity(intent);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
