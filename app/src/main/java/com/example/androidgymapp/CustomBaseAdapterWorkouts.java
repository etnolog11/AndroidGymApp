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

public class CustomBaseAdapterWorkouts extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Workout> data;
    CustomBaseAdapterWorkouts(Context ctx, ArrayList<Workout> workouts){
        context=ctx;
        inflater=LayoutInflater.from(ctx);
        data=workouts;
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Workout getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= inflater.inflate(R.layout.activity_workout_list,null);
        Workout entry= data.get(position);
        TextView scrtxtview= (TextView) convertView.findViewById(R.id.score_text);
        TextView nametxtview= (TextView) convertView.findViewById(R.id.name_text);
        TextView datetimetxtview= (TextView) convertView.findViewById(R.id.datetime_text);
        TextView lengthtxtview= (TextView)  convertView.findViewById(R.id.length_text);
        scrtxtview.setText(Byte.toString(entry.getScore()));
        nametxtview.setText(entry.getName());
        datetimetxtview.setText(entry.getStartDateTime().toString());
        lengthtxtview.setText(Integer.toString(entry.getDurationInMinutes()));
        Button deletebutton= (Button) convertView.findViewById(R.id.delete_button);
        deletebutton.setOnClickListener(v->{

            long idToDelete= DataManager.getWorkouts().remove(position).getWorkoutID();
            DataBaseHelper db = new DataBaseHelper(context);
            db.deleteWorkoutById(idToDelete);
            notifyDataSetChanged();
            });
        return convertView;
    }
}
