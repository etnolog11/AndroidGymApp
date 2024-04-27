package com.example.androidgymapp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
public class Workout {
    private LocalDateTime startDateTime;
    private int durationInMinutes;
    private ArrayList<Exercise> exercises;
    private byte score;
    private  String name;
    private  long workoutID = -1;

    Workout(LocalDateTime start,int durationInMinutes, byte score,String name,ArrayList<Exercise> exer){
        startDateTime= start;
        this.durationInMinutes=durationInMinutes;
        this.score= score;
        this.name=name;
        exercises=exer;
    }
    Workout(LocalDateTime start,int durationInMinutes, byte score,String name,ArrayList<Exercise> exer, long id){
        startDateTime= start;
        this.durationInMinutes=durationInMinutes;
        this.score= score;
        this.name=name;
        exercises=exer;
        workoutID=id;
    }

    public String getName() {
        return name;
    }

    public byte getScore() {
        return score;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public long getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(long workoutID) {
        this.workoutID = workoutID;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}
