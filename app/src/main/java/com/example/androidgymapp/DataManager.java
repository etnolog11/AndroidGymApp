package com.example.androidgymapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    private static List<Workout> workouts= new ArrayList<>(Arrays.asList(new Workout[]
            {new Workout(LocalDateTime.now(), 55, (byte) 3, "sdf", null)}));
    public static List<Set> sets= new ArrayList<>();
    public static List<Exercise> allExercises= new ArrayList<>();
    public static void addWorkout(Workout workout){
        workouts.add(workout);
        allExercises=null;
    }
    public static void addExercise(Exercise exercise){
        allExercises.add(exercise);
        sets=null;
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
    public static List<Set> getSets(){return sets;}
}
