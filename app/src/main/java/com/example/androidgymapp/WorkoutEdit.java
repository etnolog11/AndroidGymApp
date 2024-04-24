package com.example.androidgymapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        listView.setAdapter(new CustomBaseAdapterExercises(getContext(),DataManager.allExercises));
        return binding.getRoot();

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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        Workout wrk =new Workout(dateTime,lengthint,scoreint,name,DataManager.allExercises);
        DataManager.addWorkout(wrk);
        DataBaseHelper db= new DataBaseHelper(this.getContext());
        db.addWorkout(wrk);
        NavHostFragment.findNavController(WorkoutEdit.this)
                    .navigate(R.id.action_workoutEdit_to_FirstFragment);

    }

}