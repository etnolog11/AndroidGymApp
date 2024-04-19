package com.example.androidgymapp;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DataManager {

    private static List<Workout> workouts= new ArrayList<>(Arrays.asList(new Workout[]
            {new Workout(LocalDateTime.now(), 55, (byte) 3, "sdf", null)}));
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

    public static List<Workout> getWorkouts() {
        return workouts;
    }
    public static List<Exercise> getExercises() {
        return allExercises;
    }
}
