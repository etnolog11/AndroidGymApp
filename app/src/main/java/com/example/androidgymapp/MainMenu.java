package com.example.androidgymapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidgymapp.databinding.FragmentMainMenuBinding;

import java.util.ArrayList;


public class MainMenu extends Fragment {

    private FragmentMainMenuBinding binding;
    private ListView listView;
    private NavController navController;

    private final int[] notAllowedScreens= new int []{R.id.editExercise,R.id.workoutEdit,R.id.repEdit};

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        navController =NavHostFragment.findNavController(MainMenu.this);
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        listView=(ListView) binding.getRoot().findViewById(R.id.workout_list);
        DataBaseHelper db= new DataBaseHelper(getContext());
        DataManager.setWorkouts(db.getWorkoutsFromDB());
        CustomBaseAdapterWorkouts adapter= new CustomBaseAdapterWorkouts(getContext(),DataManager.getWorkouts());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navController
                        .navigate(R.id.action_FirstFragment_to_workoutEdit);
                DataManager.setWorkoutBeingEdited(adapter.getData().get(position));
                DataManager.setExercises(adapter.getData().get(position).getExercises());
            }
        });
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createNew.setOnClickListener(v ->
                navController
                        .navigate(R.id.action_FirstFragment_to_workoutEdit)
        );
        binding.stat.setOnClickListener(v ->{
                navController
                        .navigate(R.id.action_FirstFragment_to_statistics);
                        }
        );
        Log.i("MainMenu","On View Created"+this.requireActivity().getOnBackPressedDispatcher().hasEnabledCallbacks());
    }
    @Override
    public void onStart(){
        super.onStart();
        DataManager.setWorkoutBeingEdited(null);
        DataManager.setExercises(new ArrayList<>());
        DataManager.setWorkoutBeingEdited(null);
        NavBackStackEntry entry =navController.getPreviousBackStackEntry();
        if (entry!=null){
            boolean isNotAllowed = false;
            int destinationId = entry.getDestination().getId();
            for (int id: notAllowedScreens)
            {
                isNotAllowed|=id==destinationId;
            }
            if (isNotAllowed) {
               navController.popBackStack(destinationId, true);
               Log.i("MainMenu","Popping the stack");
            }
            }

        }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}