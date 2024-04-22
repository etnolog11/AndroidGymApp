package com.example.androidgymapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidgymapp.databinding.FragmentRepetitionsEditBinding;

public class SetEdit extends Fragment {

    private FragmentRepetitionsEditBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRepetitionsEditBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.saveExercise.setOnClickListener(v -> addRep(view)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void addRep(View view){
        {
            TextView weightScanner= (TextView) view.findViewById(R.id.weight_input);
            TextView repetitionsScanner= (TextView) view.findViewById(R.id.repetitions_input);
            try {
                float weight = Float.parseFloat(weightScanner.getText().toString());
                byte repetitions= Byte.parseByte(repetitionsScanner.getText().toString());
                DataManager.addRepetitions(new Set(repetitions,weight));
            } catch (NumberFormatException e) {
                ExceptionNotification.somethingWentWrong(getActivity(),"Enter valid numbers");
                return;
            }
            NavHostFragment.findNavController(SetEdit.this).navigate(R.id.action_repEdit_to_editExercise);
        }
    }
}