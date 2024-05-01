package com.example.myapplication;

// ApplicantAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ApplicantAdapter extends ArrayAdapter<Applicant> {

    private Context mContext;
    private int mResource;

    public ApplicantAdapter(Context context, int resource, ArrayList<Applicant> applicants) {
        super(context, resource, applicants);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        ImageView imageViewProfile = convertView.findViewById(R.id.imageViewProfile);
        TextView textViewFullName = convertView.findViewById(R.id.textViewFullName);
        TextView textViewCourse = convertView.findViewById(R.id.textViewCourse);

        Applicant applicant = getItem(position);

        if (applicant != null) {
            // Set profile image, full name, and course
            // imageViewProfile.setImage...
            imageViewProfile.setImageResource(applicant.getProfileImageResource());
            textViewFullName.setText(applicant.getFullName());
            textViewCourse.setText(applicant.getCourse());
        }

        return convertView;
    }
}

