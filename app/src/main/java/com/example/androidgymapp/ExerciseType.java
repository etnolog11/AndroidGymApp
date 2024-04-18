package com.example.androidgymapp;


public class ExerciseType {
    private byte repetitions;
    private float weight;
    ExerciseType (byte repetitions,float weight ){
        this.repetitions= repetitions;
        this.weight= weight;
    }
    @Override
    public String toString() {
        return repetitions+" reps with "+weight+"kg";
    }
}
