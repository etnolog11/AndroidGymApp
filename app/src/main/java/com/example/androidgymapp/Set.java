package com.example.androidgymapp;


public class Set {
    private byte repetitions;
    private float weight;
    Set(byte repetitions, float weight ){
        this.repetitions= repetitions;
        this.weight= weight;
    }

    public byte getRepetitions() {
        return repetitions;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return repetitions+" reps with "+weight+"kg";
    }
}
