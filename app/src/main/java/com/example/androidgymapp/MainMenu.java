package com.example.androidgymapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidgymapp.databinding.FragmentMainMenuBinding;

import java.util.List;

public class MainMenu extends Fragment {

    private FragmentMainMenuBinding binding;
    private ListView listView;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        listView=(ListView) binding.getRoot().findViewById(R.id.workout_list);
        DataBaseHelper db= new DataBaseHelper(getContext());
        DataManager.setWorkouts(db.getWorkoutsFromDB());
        CustomBaseAdapterWorkouts adapter= new CustomBaseAdapterWorkouts(getContext(),DataManager.getWorkouts());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavHostFragment.findNavController(MainMenu.this)
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
                NavHostFragment.findNavController(MainMenu.this)
                        .navigate(R.id.action_FirstFragment_to_workoutEdit)
        );
        binding.stat.setOnClickListener(v ->
                NavHostFragment.findNavController(MainMenu.this)
                        .navigate(R.id.action_FirstFragment_to_statistics)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}