package com.example.androidgymapp;

import java.util.List;

public class Exercise {
    private List<ExerciseType> repetitions;
    Exercise(List<ExerciseType> reps){
        repetitions=reps;

    }
    public int getNumberOfSets(){
        return repetitions.size();
    }
    public void addSet(byte reps,float weight){
        repetitions.add(new ExerciseType(reps,weight));
    }
}
