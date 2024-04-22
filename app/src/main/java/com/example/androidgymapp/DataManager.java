package com.example.androidgymapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    private static List<Workout> workouts= new ArrayList<>();
    public static List<Set> sets= new ArrayList<>();
    public static List<Exercise> allExercises= new ArrayList<>();
    private static String[] exerciseTypes= {"Weighted Squat","Weighted Pull Up","Weighted Dip"};
    private static String exerciseType= "";
    public static void addWorkout(Workout workout){
        workouts.add(workout);
        allExercises=new ArrayList<>();
    }
    public static void addExercise(Exercise exercise){
        allExercises.add(exercise);
        sets=new ArrayList<>();
    }

    public static void addRepetitions(Set rep){
        sets.add(rep);
    }
    public static void setExercises(ArrayList<Set> newArray){
        sets=newArray;
    }

    public static List<Workout> getWorkouts() {
        return workouts;
    }
    public static List<Exercise> getExercises() {
        return allExercises;
    }

    public static String[] getExerciseTypes() {
        return exerciseTypes;
    }

    public static String getExerciseType() {
        return exerciseType;
    }
    public static void setExerciseType(String type){
        exerciseType=type;
    }

    public static List<Set> getSets(){return sets;}
}
