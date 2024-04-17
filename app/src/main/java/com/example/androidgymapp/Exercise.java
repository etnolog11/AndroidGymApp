import java.util.List;

public class Exercise {
    private ExerciseType exerciseType;
    private int id;
    private List<Integer> repetitions;

    public int getNumberOfSets(){
        return repetitions.size();
    }
    public void addSet(int times){
        repetitions.add(times);
    }
}
