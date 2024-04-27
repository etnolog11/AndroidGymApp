package com.example.androidgymapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidgymapp.databinding.FragmentSetsEditBinding;

public class SetEdit extends Fragment {

    private FragmentSetsEditBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSetsEditBinding.inflate(inflater, container, false);
        View result = binding.getRoot();
        Set set =DataManager.getSetBeingEdited();
        if (set!=null){
        TextView repsInput = (TextView) result.findViewById(R.id.repetitions_input);
        TextView weightInput = (TextView) result.findViewById(R.id.weight_input);
        repsInput.setText(Integer.toString(set.getRepetitions()));
        weightInput.setText(Float.toString(set.getWeight()));}
        return result;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.saveExercise.setOnClickListener(v -> addOrUpdate(view)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public  void  addOrUpdate (View result){
        TextView repsInput = (TextView) result.findViewById(R.id.repetitions_input);
        TextView weightInput = (TextView) result.findViewById(R.id.weight_input);
        float weight;
        byte repetitions;
        try {
            weight = Float.parseFloat(weightInput.getText().toString());
            repetitions= Byte.parseByte(repsInput.getText().toString());

        } catch (NumberFormatException e) {
            ExceptionNotification.somethingWentWrong(getActivity(),"Enter valid numbers");
            return;}
        Set set =DataManager.getSetBeingEdited();
        if (set!=null){
            set.setRepetitions(repetitions);
            set.setWeight(weight);
        }
        else{
            set =new Set(repetitions,weight);
            addSet(set);
        }
        if (!DataManager.getUpdatedOrAddedSets().contains(set))
            DataManager.addUpdatedOrAddedSets(set);
        DataManager.setSetBeingEdited(null);
        NavHostFragment.findNavController(SetEdit.this).navigate(R.id.action_repEdit_to_editExercise);

    }
    public void addSet(Set set){
        DataManager.addSet(set);

    }
}