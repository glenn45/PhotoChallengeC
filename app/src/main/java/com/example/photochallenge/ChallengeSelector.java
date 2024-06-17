package com.example.photochallenge;

import java.util.List;
import java.util.Random;

public class ChallengeSelector {
    private Random random;
    private ChallengeGenerator challengeGenerator;


    public ChallengeSelector() {
        random = new Random();
        challengeGenerator = new ChallengeGenerator();
    }


    public String selectOneChallenge() {
        List<String> challenges = challengeGenerator.getChallenges();
        return challenges.get(random.nextInt(challenges.size()));
    }

}



