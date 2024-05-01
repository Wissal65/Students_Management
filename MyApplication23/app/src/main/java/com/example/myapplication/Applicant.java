package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;

public class Applicant implements Parcelable {
    private String fullName;
    private String course;
    private String number;
    private static HashMap<String, Integer> profileImageMap = new HashMap<>();
    private int profileImageResource;

    public Applicant() {
        // Required empty public constructor for Firestore
    }

    public Applicant(String fullName, String course, String number) {
        this.fullName = fullName;
        this.course = course;
        this.number = number;
    }

    protected Applicant(Parcel in) {
        fullName = in.readString();
        course = in.readString();
        number = in.readString();
        profileImageResource = in.readInt();
    }

    public static final Creator<Applicant> CREATOR = new Creator<Applicant>() {
        @Override
        public Applicant createFromParcel(Parcel in) {
            return new Applicant(in);
        }

        @Override
        public Applicant[] newArray(int size) {
            return new Applicant[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getProfileImageResource() {
        return profileImageResource;
    }

    public void setProfileImageResource(int profileImageResource) {
        this.profileImageResource = profileImageResource;
    }

    public void generateProfileImageResource(int userIndex) {
        // Check if the full name already exists in the map
        if (profileImageMap.containsKey(fullName)) {
            // If exists, assign the same profile image resource
            profileImageResource = profileImageMap.get(fullName);
        } else {
            // If not exists, generate a new profile image resource and store it in the map
            int[] profileImages = {R.drawable.user1, R.drawable.user2, R.drawable.user4, R.drawable.user5};
            int profileIndex = userIndex % profileImages.length;
            profileImageResource = profileImages[profileIndex];
            profileImageMap.put(fullName, profileImageResource);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(course);
        dest.writeString(number);
        dest.writeInt(profileImageResource);
    }
}