package com.example.androidgymapp;


import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static ArrayList<Workout> workouts= new ArrayList<>();
    public static ArrayList<Set> sets= new ArrayList<>();
    public static ArrayList<Exercise> allExercises= new ArrayList<>();
    public static Workout workoutBeingEdited=null;
    public static Exercise exerciseBeingEdited = null;
    public static Set setBeingEdited= null;
    private static final String[] exerciseTypes= {"Lat Pulldown", "Shoulder Press","Row","Chest Press","Weighted Squat","Weighted Pull Up","Weighted Dip"};
    private static String exerciseType= "";
    private static ArrayList<Set>  updatedOrAddedSets = new ArrayList<>();
    private static ArrayList<Exercise>  updatedOrAddedExercises = new ArrayList<>();
    public static void addWorkout(Workout workout){
        workouts.add(workout);
        allExercises=new ArrayList<>();
    }
    public static void addExercise(Exercise exercise){
        allExercises.add(exercise);
        sets=new ArrayList<>();
    }
    public static void addSet(Set set){
        sets.add(set);
    }

    public static void addRepetitions(Set rep){
        sets.add(rep);
    }
    public static void setSets(ArrayList<Set> newArray){
        sets=newArray;
    }
    public static void setExercises(ArrayList<Exercise> newArray){
        allExercises=newArray;
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

    public static Exercise getExerciseBeingEdited() {
        return exerciseBeingEdited;
    }

    public static Set getSetBeingEdited() {
        return setBeingEdited;
    }

    public static Workout getWorkoutBeingEdited() {
        return workoutBeingEdited;
    }

    public static void setExerciseBeingEdited(Exercise exerciseBeingEdited) {
        DataManager.exerciseBeingEdited = exerciseBeingEdited;
    }

    public static void setSetBeingEdited(Set setBeingEdited) {
        DataManager.setBeingEdited = setBeingEdited;
    }

    public static void setWorkoutBeingEdited(Workout workoutBeingEdited) {
        DataManager.workoutBeingEdited = workoutBeingEdited;
    }

    public  static  void setExerciseTypeToDefault(){
        exerciseType="";
    }


    public static boolean removeUpdatedOrAddedExercises(Exercise exercise) {
        return DataManager.updatedOrAddedExercises.remove(exercise);
    }
    public static boolean removeUpdatedOrAddedSets(Set set) {
        return DataManager.updatedOrAddedSets.remove(set);
    }

    public static void addUpdatedOrAddedExercises(Exercise exercise) {
        DataManager.updatedOrAddedExercises.add(exercise);
    }

    public static void addUpdatedOrAddedSets(Set set) {
        DataManager.updatedOrAddedSets.add(set);
    }

    public static void clearUpdatedOrAddedExercise(){
        DataManager.updatedOrAddedExercises = new ArrayList<>();
    }

    public static void clearUpdatedOrAddedSets(){
        DataManager.updatedOrAddedSets = new ArrayList<>();
    }
    public static List<Set> getSets(){return sets;}
    public static void update(){

        sets = new ArrayList<>();
        allExercises= new ArrayList<>();
        workouts = new ArrayList<>();

    }

    public static ArrayList<Exercise> getUpdatedOrAddedExercises() {
        return updatedOrAddedExercises;
    }

    public static ArrayList<Set> getUpdatedOrAddedSets() {
        return updatedOrAddedSets;
    }

    public static String getDataString(){
        StringBuilder sb = new StringBuilder();
        sb.append(workouts);
        sb.append(sets);
        sb.append(allExercises);
        sb.append(workoutBeingEdited);
        sb.append(exerciseBeingEdited);
        sb.append(setBeingEdited);
        sb.append(exerciseTypes);
        sb.append(exerciseType);
        sb.append(updatedOrAddedExercises);
        sb.append(updatedOrAddedSets);
        return  sb.toString();

    }
}
