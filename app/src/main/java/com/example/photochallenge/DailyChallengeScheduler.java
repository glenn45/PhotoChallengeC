package com.example.photochallenge;
import android.annotation.SuppressLint;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import android.widget.TextView;

public class DailyChallengeScheduler {
    private Timer timer;

    public String moodText,challengeText;

    public DailyChallengeScheduler() {
        timer = new Timer();
    }

    @SuppressLint("DiscouragedApi")
    public void scheduleDailyTask() {
        TimerTask dailyTask = new TimerTask() {
            @Override
            public void run() {
                ChallengeSelector selector = new ChallengeSelector();
                String challenge = selector.selectOneChallenge();
                MoodSelector moodselector = new MoodSelector();
                String mood = moodselector.selectMoodChangellenge();
                System.out.println("Today's challenge is:");
                System.out.println(challenge);
                System.out.println(mood);
                moodText = mood;
                challengeText = challenge;



            }
        };

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startTime = calendar.getTime();

        // If the scheduled time has already passed today, schedule for tomorrow
        if (startTime.before(new Date())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            startTime = calendar.getTime();
        }

        long period = 24 * 60 * 60 * 1000; // 24 hours
        timer.scheduleAtFixedRate(dailyTask, startTime, period);
    }

    public static void main(String[] args) {
        DailyChallengeScheduler scheduler = new DailyChallengeScheduler();
        scheduler.scheduleDailyTask();
    }
}
