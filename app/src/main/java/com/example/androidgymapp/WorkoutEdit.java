package com.example.androidgymapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidgymapp.databinding.FragmentWorkoutEditBinding;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class WorkoutEdit extends Fragment {

    private FragmentWorkoutEditBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentWorkoutEditBinding.inflate(inflater, container, false);
        ListView listView=(ListView)binding.getRoot().findViewById(R.id.exercise_list_view);
        Workout workoutEdited = DataManager.getWorkoutBeingEdited();
        View view= binding.getRoot();
        CustomBaseAdapterExercises adapter = new CustomBaseAdapterExercises(getContext(),DataManager.allExercises);
        if (workoutEdited==null)
        {
            listView.setAdapter(adapter);
            Log.i("WorkoutEdit","workout to edit is null");
        }
        else{
            Log.i("WorkoutEdit","workout to edit is not null");
            adapter =new CustomBaseAdapterExercises(getContext(),workoutEdited.getExercises());
            listView.setAdapter(adapter);
            TextView dataEntry= (TextView) view.findViewById(R.id.input_date);
            TextView timeEntry= (TextView) view.findViewById(R.id.input_time);
            TextView lengthEntry= (TextView) view.findViewById(R.id.input_length);
            TextView nameEntry =   (TextView) view.findViewById(R.id.input_name);
            TextView scoreEntry =   (TextView) view.findViewById(R.id.input_score);
            LocalDateTime dateTime = workoutEdited.getStartDateTime();
            String Day = (dateTime.getDayOfMonth()<10?"0":"")+Integer.toString(dateTime.getDayOfMonth());
            String Month = (dateTime.getMonthValue()<10?"0":"")+Integer.toString(dateTime.getMonthValue());
            String date = Day+Month+dateTime.getYear();
            String hour =(dateTime.getHour()<10?"0":"")+ Integer.toString(dateTime.getHour());
            String minute =(dateTime.getMinute()<10?"0":"")+ Integer.toString(dateTime.getMinute());
            String time =hour+":"+minute;
            dataEntry.setText(date);
            timeEntry.setText(time);
            lengthEntry.setText(Integer.toString(workoutEdited.getDurationInMinutes()));
            nameEntry.setText(workoutEdited.getName());
            scoreEntry.setText(Byte.toString(workoutEdited.getScore()));
        }
        final CustomBaseAdapterExercises finadapter= adapter;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavHostFragment.findNavController(WorkoutEdit.this)
                        .navigate(R.id.action_workoutEdit_to_editExercise);
                DataManager.setExerciseBeingEdited(finadapter.getData().get(position));
                DataManager.setSets(finadapter.getData().get(position).getSets());
                DataManager.setExerciseType(finadapter.getData().get(position).getName());
            }
        });
        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addExercise.setOnClickListener(v -> NavHostFragment.findNavController(WorkoutEdit.this).navigate(R.id.action_workoutEdit_to_editExercise));
        binding.saveExercise.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addWorkout(view);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addWorkout(View view){
        TextView dataEntry= (TextView) view.findViewById(R.id.input_date);
        TextView timeEntry= (TextView) view.findViewById(R.id.input_time);
        TextView lengthEntry= (TextView) view.findViewById(R.id.input_length);
        TextView nameEntry =   (TextView) view.findViewById(R.id.input_name);
        TextView scoreEntry =   (TextView) view.findViewById(R.id.input_score);
        String data=dataEntry.getText().toString();
        String time=timeEntry.getText().toString();
        String length=lengthEntry.getText().toString();
        String name=nameEntry.getText().toString();
        String score= scoreEntry.getText().toString();
        if (data.isEmpty()||time.isEmpty()||length.isEmpty()||name.isEmpty()||score.isEmpty()){
            ExceptionNotification.somethingWentWrong(getActivity(),"Fill out all of the fields");
            return;
        }
        int lengthint;
        byte scoreint;
        try {
            lengthint=Integer.parseInt(length);
            if (lengthint==0) throw new NumberFormatException();
        }
        catch (NumberFormatException e){
            ExceptionNotification.somethingWentWrong(getActivity(),"Incorrect length");
            return;
        }
        try {
            scoreint= Byte.parseByte(score);
            if (scoreint>10||scoreint<=0) throw new NumberFormatException();
        }
        catch (NumberFormatException e){
            ExceptionNotification.somethingWentWrong(getActivity(),"Incorrect score");
            return;
        }
        LocalDate date=null;
        LocalTime time1=null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            date = LocalDate.parse(data,formatter);
            time1= LocalTime.parse(time);

        }
        catch (Exception e){
            ExceptionNotification.somethingWentWrong(getActivity(),"Incorrect date or time");
            return;
        }
        LocalDateTime dateTime= date.atTime(time1);
        Workout workout =DataManager.getWorkoutBeingEdited();
        DataBaseHelper db= new DataBaseHelper(this.getContext());
        if (workout==null){
        Workout wrk =new Workout(dateTime,lengthint,scoreint,name,DataManager.allExercises);
        DataManager.addWorkout(wrk);
        db.addWorkout(wrk);
        Log.i("WorkoutEdit","Added Workout");}
        else{
            Log.i("WorkoutEdit","Updated Workout");
            workout.setStartDateTime(dateTime);
            workout.setDurationInMinutes(lengthint);
            workout.setScore(scoreint);
            workout.setName(name);
            workout.setExercises(DataManager.allExercises);
            DataManager.setExercises(new ArrayList<>());
            DataManager.setWorkoutBeingEdited(null);
            db.updateWorkout(workout);
        }
        NavHostFragment.findNavController(WorkoutEdit.this)
                    .navigate(R.id.action_workoutEdit_to_FirstFragment);

    }

}