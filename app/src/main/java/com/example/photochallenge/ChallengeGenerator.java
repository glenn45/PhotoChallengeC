package com.example.photochallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChallengeGenerator {
    private List<String> challenges;



    public ChallengeGenerator() {
        challenges = new ArrayList<>();
        challenges.add("Food Photography");
        challenges.add("Portrait Photography");
        challenges.add("Pet Photography");
        challenges.add("Lanscape Photography");
        // Add more challenges as needed
    }


    public List<String> getChallenges() {
        return challenges;
    }


}
