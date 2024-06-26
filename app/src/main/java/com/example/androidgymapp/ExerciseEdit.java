package com.example.androidgymapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.androidgymapp.databinding.FragmentExcerciseEditBinding;

public class ExerciseEdit extends Fragment {

    private FragmentExcerciseEditBinding binding;
    private  NavController navController ;
    private final int[] notAllowedScreens= new int []{R.id.repEdit};

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentExcerciseEditBinding.inflate(inflater, container, false);
        navController =NavHostFragment.findNavController(ExerciseEdit.this);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView=(ListView) view.findViewById(R.id.set_list);
        Spinner spinner= (Spinner)view.findViewById(R.id.choose_exercise);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataManager.setExerciseType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setAdapter(new CustomBaseAdapterRepetitions(getContext(),DataManager.sets));
        final CustomBaseAdapterRepetitions finadapter = (CustomBaseAdapterRepetitions) listView.getAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataManager.setSetBeingEdited(finadapter.getData().get(position));
                navController
                        .navigate(R.id.action_editExercise_to_repEdit);
            }
        });
        spinner.setAdapter(new ArrayAdapter<>(this.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,DataManager.getExerciseTypes()));
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
        int spinnerPosition = myAdap.getPosition(DataManager.getExerciseType());
         Exercise exercise = DataManager.getExerciseBeingEdited();
        if (exercise!=null){
            spinnerPosition=myAdap.getPosition(exercise.getName());
        }
        spinner.setSelection(spinnerPosition);
        binding.addReps.setOnClickListener(v -> {navController.navigate(R.id.action_editExercise_to_repEdit);
        });
        binding.saveExercise.setOnClickListener(v->addOrUpdateExercise());
    }



    public void onStart(){
        super.onStart();
        NavBackStackEntry entry =navController.getPreviousBackStackEntry();
        DataManager.setSetBeingEdited(null);
        if (entry!=null){
            boolean isNotAllowed = false;
            int destinationId = entry.getDestination().getId();
            for (int id: notAllowedScreens)
            {
                isNotAllowed|=id==destinationId;
            }
            if (isNotAllowed) {
                navController.popBackStack(destinationId, true);
                Log.i("ExerciseEdit","Popping the stack");
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void addOrUpdateExercise(){
        Exercise exercise;
        if(DataManager.getExerciseBeingEdited()!=null){
            exercise = updateExercise();
        }
        else{
            exercise =addExercise();
        }
        if (!DataManager.getUpdatedOrAddedExercises().contains(exercise) )
            DataManager.addUpdatedOrAddedExercises(exercise);
        navController
                .navigate(R.id.action_editExercise_to_workoutEdit);
    }
    private Exercise updateExercise(){
        Log.i("ExerciseEdit", "Updated Exercise");
        Exercise exercise=DataManager.getExerciseBeingEdited();
        exercise.setName(DataManager.getExerciseType());
        return exercise;
    }
    private Exercise addExercise(){
        Log.i("ExerciseEdit", "Added Exercise");
        Exercise exercise= new Exercise(DataManager.sets,DataManager.getExerciseType());
        DataManager.addExercise(exercise);
        return exercise;
    }
}