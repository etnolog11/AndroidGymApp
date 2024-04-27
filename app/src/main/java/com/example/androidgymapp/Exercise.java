package com.example.androidgymapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class Exercise {
    private long exerciseId = -1;
    private long workoutId = -1;
    private String name;
    private ArrayList<Set> repetitions;
    Exercise(ArrayList<Set> reps,String name){
        repetitions=reps;
        this.name=name;
    }
    Exercise(ArrayList<Set> reps, String name,long exerciseId, long workoutId){
        repetitions=reps;
        this.name=name;
        this.workoutId=workoutId;
        this.exerciseId=exerciseId;
    }
    public String getName(){
        return name;
    }
    public int getRepetitions(){
        return repetitions.stream().mapToInt(v-> v.getRepetitions()).sum();
    }
    public ArrayList<Set> getSets(){
        return repetitions;
    }
    public String getRepetitionsString(){
        if (repetitions.isEmpty())
            return "";
        StringBuilder sb =new StringBuilder();
        sb.append(repetitions.get(0).getRepetitions());
        for (int i = 1; i<repetitions.size();i++) {
            sb.append(" "+repetitions.get(i).getRepetitions());
        }
        return sb.toString();
    }
    public Double getAvgWeight(){
        if (getRepetitions()==0)
            return 0.0;
        else

            return ((double)(int)(repetitions.stream().mapToDouble(v->v.getWeight()*v.getRepetitions()).sum()/getRepetitions()*100))/100;
    }
    public int getNumberOfSets(){
        return repetitions.size();
    }
    public void addSet(byte reps,float weight){
        repetitions.add(new Set(reps,weight));
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
