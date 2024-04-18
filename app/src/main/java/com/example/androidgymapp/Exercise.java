package com.example.androidgymapp;

import java.util.List;

public class Exercise {
    private int id;
    private List<ExerciseType> repetitions;

    public int getNumberOfSets(){
        return repetitions.size();
    }
    public void addSet(byte reps,float weight){
        repetitions.add(new ExerciseType(reps,weight));
    }
}
