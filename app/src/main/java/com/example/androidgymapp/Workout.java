package com.example.androidgymapp;
import java.time.*;
import java.util.List;
public class Workout {
    private int id;
    private ZonedDateTime startDateTime;
    private int durationInMinutes;
    private List<Exercise> exercises;
}
