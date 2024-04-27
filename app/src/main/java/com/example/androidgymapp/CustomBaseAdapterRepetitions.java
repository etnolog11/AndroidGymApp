package com.example.androidgymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapterRepetitions extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Set> data;
    CustomBaseAdapterRepetitions(Context ctx, ArrayList<Set> exercises){
        context=ctx;
        inflater=LayoutInflater.from(ctx);
        data=exercises;
    }
    public ArrayList<Set> getData(){
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
        convertView=inflater.inflate(R.layout.activity_set_list,null);
        Set setEntry= DataManager.getSets().get(position);
        TextView weight= (TextView) convertView.findViewById(R.id.weight);
        TextView repetitions= (TextView) convertView.findViewById(R.id.repetitions_set);
        weight.setText(Float.toString(setEntry.getWeight()));
        repetitions.setText(Byte.toString(setEntry.getRepetitions()));
        Button deleteButton= (Button) convertView.findViewById(R.id.delete_button_set);
        deleteButton.setOnClickListener(v->{

            long idToDelete=DataManager.getSets().remove(position).getSetId();
            DataBaseHelper db = new DataBaseHelper(context);
            db.deleteSetById(idToDelete);
            notifyDataSetChanged();
        });
        return convertView;
    }
}
