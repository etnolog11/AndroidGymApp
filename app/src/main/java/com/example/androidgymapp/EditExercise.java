package com.example.androidgymapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.androidgymapp.databinding.FragmentExcerciseEditBinding;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class EditExercise extends Fragment {

    private FragmentExcerciseEditBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentExcerciseEditBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.addReps.setOnClickListener(v -> NavHostFragment.findNavController(EditExercise.this).navigate(R.id.action_editExercise_to_repEdit));
        binding.saveExercise.setOnClickListener(v->addExercise(view));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void addExercise(View view){

        DataManager.addExercise(new Exercise(DataManager.exercises,"Pull Ups"));

        NavHostFragment.findNavController(EditExercise.this)
                .navigate(R.id.action_editExercise_to_workoutEdit);
    }
}