package com.example.photochallenge;

import java.util.List;
import java.util.Random;

public class MoodSelector {
    private Random random;
    private MoodGenerator moodGenerator;
    public MoodSelector() {
        random = new Random();
        moodGenerator = new MoodGenerator();
    }
    public String selectMoodChangellenge(){
        List<String> challenges = moodGenerator.getMood();
        return challenges.get(random.nextInt(challenges.size()));
    }

}
