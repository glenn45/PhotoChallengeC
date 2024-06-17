package com.example.photochallenge;

import java.util.ArrayList;
import java.util.List;

public class MoodGenerator {
    private List<String> moodPhoto;

    public MoodGenerator() {
        moodPhoto = new ArrayList<>();
        moodPhoto.add("Warm");
        moodPhoto.add("Cool");
    }

    public List<String> getMood() {
        return moodPhoto;
    }
}
