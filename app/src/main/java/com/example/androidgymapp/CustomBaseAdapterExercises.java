package com.example.androidgymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapterExercises extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Exercise> data;
    CustomBaseAdapterExercises(Context ctx, ArrayList<Exercise> exercises){
        context=ctx;
        inflater=LayoutInflater.from(ctx);
        data=exercises;
    }

    public ArrayList<Exercise> getData() {
        return data;
    }

    @Override

    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.activity_exercise_list,null);
        Exercise exerciseEntry= DataManager.getExercises().get(position);
        TextView avgWeight= (TextView) convertView.findViewById(R.id.average_weight);
        TextView exerciseName= (TextView) convertView.findViewById(R.id.exercise_name);
        TextView repetitions= (TextView) convertView.findViewById(R.id.repetitions);
        exerciseName.setText(exerciseEntry.getName());
        repetitions.setText(exerciseEntry.getRepetitionsString());
        avgWeight.setText(exerciseEntry.getAvgWeight().toString());
        Button deleteButton= (Button) convertView.findViewById(R.id.delete_exercise);
        deleteButton.setOnClickListener(v->{
            long idToDelete= DataManager.getExercises().remove(position).getExerciseId();
            DataBaseHelper db = new DataBaseHelper(context);
            db.deleteExerciseById(idToDelete);
            notifyDataSetChanged();
        });
        return convertView;
    }
}
