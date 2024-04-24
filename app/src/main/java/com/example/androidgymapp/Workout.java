package com.example.androidgymapp;
import java.time.*;
import java.util.List;
public class Workout {
    private LocalDateTime startDateTime;
    private int durationInMinutes;
    private List<Exercise> exercises;
    private byte score;
    private  String name;
    private  long workoutID;

    Workout(LocalDateTime start,int durationInMinutes, byte score,String name,List<Exercise> exer){
        startDateTime= start;
        this.durationInMinutes=durationInMinutes;
        this.score= score;
        this.name=name;
        exercises=exer;
    }
    Workout(LocalDateTime start,int durationInMinutes, byte score,String name,List<Exercise> exer, long id){
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public long getWorkoutID() {
        return workoutID;
    }

    public void setWorkoutID(long workoutID) {
        this.workoutID = workoutID;
    }
}
