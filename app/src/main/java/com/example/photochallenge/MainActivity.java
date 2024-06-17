package com.example.photochallenge;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button dailychallenge;
    private TextView timerTextView;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update UI with timer value
            updateTimerUI();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        dailychallenge = findViewById(R.id.dailyChallenge);
        timerTextView = findViewById(R.id.timerTextView);

        IntentFilter intentFilter = new IntentFilter("TIMER_UPDATE");
        registerReceiver(broadcastReceiver, intentFilter);

        dailychallenge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Challenge.class));
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    private void updateTimerUI() {
        // Update the TextView with the current time
        long currentTimeMillis = System.currentTimeMillis();
        String timerText = getTimerText(currentTimeMillis);
        timerTextView.setText(timerText);
    }
    private String getTimerText(long currentTimeMillis) {
        long seconds = currentTimeMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;
        hours = hours % 24;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }


}