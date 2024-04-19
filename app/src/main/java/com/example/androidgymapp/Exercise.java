package com.example.androidgymapp;

import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class Exercise {
    private String name;
    private List<ExerciseType> repetitions;
    Exercise(List<ExerciseType> reps,String name){
        repetitions=reps;
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public int getRepetitions(){
        return repetitions.stream().mapToInt(v-> v.getRepetitions()).sum();
    }
    public String getRepetitionsString(){
        StringBuilder sb =new StringBuilder();
        sb.append(repetitions.get(0).getRepetitions());
        for (int i = 1; i<repetitions.size();i++) {
            sb.append(" "+repetitions.get(i).getRepetitions());
        }
        return sb.toString();
    }
    public Double getAvgWeight(){
        return new BigDecimal((repetitions.stream().mapToDouble(v->v.getWeight()*v.getRepetitions()).sum()/getRepetitions()*100)/100).setScale(2,RoundingMode.HALF_EVEN).toBigInteger().doubleValue();
    }
    public int getNumberOfSets(){
        return repetitions.size();
    }
    public void addSet(byte reps,float weight){
        repetitions.add(new ExerciseType(reps,weight));
    }
}
