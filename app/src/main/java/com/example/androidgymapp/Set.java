package com.example.androidgymapp;


public class Set {
    private byte repetitions;
    private float weight;
    private long setId = -1;
    private long exerciseId = -1;
    Set(byte repetitions, float weight ){
        this.repetitions= repetitions;
        this.weight= weight;
    }
    Set(byte repetitions, float weight ,long setId,long exerciseId){
        this.repetitions= repetitions;
        this.weight= weight;
        this.setId=setId;
        this.exerciseId=exerciseId;
    }

    public byte getRepetitions() {
        return repetitions;
    }

    public float getWeight() {
        return weight;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public long getSetId() {
        return setId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setSetId(long setId) {
        this.setId = setId;
    }

    public void setRepetitions(byte reps){
        repetitions =reps;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return repetitions+" reps with "+weight+"kg";
    }
}
