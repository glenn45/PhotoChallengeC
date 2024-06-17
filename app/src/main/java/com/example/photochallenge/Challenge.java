package com.example.photochallenge;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.Manifest;





public class Challenge extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;
    private String currentPhotoPath;
    TextView challenge,mood,styletitle,styledesc;
    boolean istoday = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_challenge);

        challenge = findViewById(R.id.challengeText);

        mood = findViewById(R.id.moodText);

        ChallengeSelector challengeSelector = new ChallengeSelector();
        String challenget = challengeSelector.selectOneChallenge();
        challenge.setText(challenget);

        //Description

        styletitle = findViewById(R.id.stylecalled);
        styledesc = findViewById(R.id.styledesc);

        styletitle.setText(challenget);

        if (styletitle.getText().equals("Food Photography")){
            styledesc.setText("Description sa Food Photograpy");
        }
        else if (styletitle.getText().equals("Portrait Photography")){
            styledesc.setText("Description sa Portrait Photography");
        }
        else if (styletitle.getText().equals("Pet Photography")){
            styledesc.setText("Description sa Pet Photography");
        }
        else if (styletitle.getText().equals("Lanscape Photography")){
            styledesc.setText("Description sa Lanscape Photography");
        }





        MoodSelector moodSelector = new MoodSelector();
        String challengem = moodSelector.selectMoodChangellenge();
        mood.setText(challengem);

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File folder1 = new File(storageDir, "Submission");;
        createFolderIfNotExist(folder1);

        Button viewSubmission = findViewById(R.id.viewSubmission);

        viewSubmission.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Challenge.this, SubmissionList.class));
            }
        });




    }
    private void createFolderIfNotExist(File folder) {
        if (!folder.exists()) {
            boolean created = folder.mkdir();
            if (created) {
                Log.d("FolderCreation", "Folder created: " + folder.getName());
            } else {
                Log.d("FolderCreation", "Folder not created: " + folder.getName());
            }
        } else {
            Log.d("FolderCreation", "Folder already exists: " + folder.getName());
        }
    }
    public void onUploadClick(View view) {
        checkPermissionAndSelectImage();
    }
    private void checkPermissionAndSelectImage() {
        if (ContextCompat.checkSelfPermission(Challenge.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Challenge.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            selectImage();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Call super method first

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(Challenge.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                saveImageToCustomDirectory(selectedImageUri);
            } else {
                Toast.makeText(Challenge.this, "Failed to retrieve image!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void saveImageToCustomDirectory(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS); // Get external storage directory for your app
            File submissionDirectory = new File(storageDir, "Submission");
            if (!submissionDirectory.exists()) {
                submissionDirectory.mkdirs(); // create directories if not exists
            }

            // Generate a unique file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".jpg";
            File outputFile = new File(submissionDirectory, imageFileName);

            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();

            Toast.makeText(Challenge.this, "Image saved to " + outputFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Challenge.this, "Failed to save image!", Toast.LENGTH_SHORT).show();
        }
    }

}