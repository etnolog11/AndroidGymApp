package com.example.androidgymapp;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static List<Workout> workouts= new ArrayList<>();
    public static List<ExerciseType> exercises= new ArrayList<>();
    public static List<Exercise> allExercises= new ArrayList<>();
    public static void addWorkout(Workout workout){
        workouts.add(workout);
        allExercises=null;
    }
    public static void addExercise(Exercise exercise){
        allExercises.add(exercise);
        exercises=null;
    }

    public static void addRepetitions(ExerciseType rep){
        exercises.add(rep);
    }
    public static void setExercises(ArrayList<ExerciseType> newArray){
        exercises=newArray;
    }

}
