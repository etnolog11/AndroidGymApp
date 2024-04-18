package com.example.androidgymapp;
import java.time.*;
import java.util.List;
public class Workout {
    private LocalDateTime startDateTime;
    private int durationInMinutes;
    private List<Exercise> exercises;
    private byte score;
    private  String name;

    Workout(LocalDateTime start,int durationInMinutes, byte score,String name,List<Exercise> exer){
        startDateTime= start;
        this.durationInMinutes=durationInMinutes;
        this.score= score;
        this.name=name;
        exercises=exer;
    }
}
