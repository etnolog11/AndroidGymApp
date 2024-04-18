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

public class RepEdit extends Fragment {

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
        binding.saveRepetitions.setOnClickListener(v ->{
               TextView weightScanner= (TextView) view.findViewById(R.id.weight_input);
               TextView repetitionsScanner= (TextView) view.findViewById(R.id.repetitions_input);
                    try {
                        float weight = Float.parseFloat(weightScanner.getText().toString());
                        byte repetitions= Byte.parseByte(repetitionsScanner.getText().toString());
                        DataManager.exercises.add(new ExerciseType(repetitions,weight));
                    } catch (NumberFormatException e) {
                        ExceptionNotification.somethingWentWrong(getActivity(),"NumberFormatException");
                    }

                    NavHostFragment.findNavController(RepEdit.this).navigate(R.id.action_repEdit_to_FirstFragment);
                    ExceptionNotification.somethingWentWrong(getActivity(),DataManager.exercises.toString());
        }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}