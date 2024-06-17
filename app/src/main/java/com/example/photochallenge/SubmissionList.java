package com.example.photochallenge;

import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.widget.ImageView;

import java.io.File;

public class SubmissionList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_submission_list);
        LinearLayout scrollViewLinearLayout = findViewById(R.id.scrollViewLinearLayout);
        File submissionDirectory = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Submission");

        if (submissionDirectory.exists() && submissionDirectory.isDirectory()) {
            File[] files = submissionDirectory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        // Create ImageView for each image file
                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));
                        imageView.setImageURI(Uri.fromFile(file)); // Load image to ImageView
                        imageView.setPadding(0, 10, 0, 10); // Add some padding

                        scrollViewLinearLayout.addView(imageView); // Add ImageView to LinearLayout
                    }
                }
            }
        }
    }
}