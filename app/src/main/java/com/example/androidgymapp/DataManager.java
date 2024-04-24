package com.example.androidgymapp;


import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static ArrayList<Workout> workouts= new ArrayList<>();
    public static ArrayList<Set> sets= new ArrayList<>();
    public static ArrayList<Exercise> allExercises= new ArrayList<>();
    private static String[] exerciseTypes= {"Lat Pulldown", "Shoulder Press","Row","Chest Press","Weighted Squat","Weighted Pull Up","Weighted Dip"};
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
    public static void setWorkouts(ArrayList<Workout> newArray){
        workouts=newArray;
    }

    public static ArrayList<Workout> getWorkouts() {
        return workouts;
    }
    public static ArrayList<Exercise> getExercises() {
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
    public static void update(){

        sets = new ArrayList<>();
        allExercises= new ArrayList<>();
        workouts = new ArrayList<>();

    }
}
