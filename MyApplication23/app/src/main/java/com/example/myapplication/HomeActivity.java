package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    Button entercoursebtn;
    ImageView logoutbtn;
    TextView helloSign;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        entercoursebtn = findViewById(R.id.entercoursebtn);
        logoutbtn = findViewById(R.id.logoutbtn);
        helloSign = findViewById(R.id.hellosign);
        ListView listView = findViewById(R.id.listt);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullName = documentSnapshot.getString("username");
                            if (fullName != null && !fullName.isEmpty()) {
                                String greetingMessage = "Hello,\n" + fullName;
                                helloSign.setText(greetingMessage);
                            } else {
                                helloSign.setText("Hello,\nUser");
                            }
                        } else {
                            Log.d("HomeActivity", "No such document");
                            helloSign.setText("Hello,\nUser");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("HomeActivity", "Error retrieving user data: ", e);
                        helloSign.setText("Hello,\nUser");
                    });
        } else {
            Log.d("HomeActivity", "User is null");
            helloSign.setText("Hello,\nUser");
        }

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("HTML5 Mastery", "1h45", R.drawable.html));
        courses.add(new Course("CSS3 Styling Techniques", "1h", R.drawable.csspn));
        courses.add(new Course("JavaScript Advanced Concepts", "2h30", R.drawable.js));
        courses.add(new Course("Responsive Web Design", "1h45", R.drawable.responsive));
        courses.add(new Course("Android App Development", "2h30", R.drawable.androiddevpn));
        courses.add(new Course("iOS App Development", "2h45", R.drawable.iospn));
        courses.add(new Course("Database Management with SQL", "2h", R.drawable.sqlpn));
        courses.add(new Course("Machine Learning Fundamentals", "3h", R.drawable.machinelearningpn));
        courses.add(new Course("Graphic Design Basics", "1h30", R.drawable.graphicpn));
        courses.add(new Course("Vue.js Crash Course", "1h45", R.drawable.vuepn));
        courses.add(new Course("Angular Framework Overview", "2h15", R.drawable.angular));
        courses.add(new Course("Bootstrap Essentials", "1h", R.drawable.bootstrappn));
        courses.add(new Course("WordPress Development", "2h30", R.drawable.wordpresspn));
        courses.add(new Course("Game Development with Unity", "3h", R.drawable.unitypn));
        courses.add(new Course("Python Programming", "2h", R.drawable.py));
        courses.add(new Course("Java Fundamentals", "2h45", R.drawable.javapn));
        courses.add(new Course("UX Design Principles", "1h30", R.drawable.uxpn));
        courses.add(new Course("UI Design Basics", "1h", R.drawable.uipn));
        courses.add(new Course("React.js Essentials", "2h15", R.drawable.react));


        // Create adapter
        CourseAdapter adapt = new CourseAdapter(this, courses);
        listView.setAdapter(adapt);


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                // 2. Navigate the user back to the login screen or any other appropriate screen.
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}
