package com.example.androidgymapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.androidgymapp.databinding.FragmentExcerciseEditBinding;

public class ExerciseEdit extends Fragment {

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
        ListView listView=(ListView) view.findViewById(R.id.set_list);
        listView.setAdapter(new CustomBaseAdapterRepetitions(getContext(),DataManager.sets));
        binding.addReps.setOnClickListener(v -> NavHostFragment.findNavController(ExerciseEdit.this).navigate(R.id.action_editExercise_to_repEdit));
        binding.saveExercise.setOnClickListener(v->addExercise(view));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void addExercise(View view){

        DataManager.addExercise(new Exercise(DataManager.sets,"Pull Ups"));

        NavHostFragment.findNavController(ExerciseEdit.this)
                .navigate(R.id.action_editExercise_to_workoutEdit);
    }
}