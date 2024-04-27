package com.example.androidgymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Spannable;
import android.util.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    DataBaseHelper(Context context ){
        super(context, "workout_data.db", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String workoutsTableCreationStatement = "CREATE TABLE IF NOT EXISTS workouts (\n" +
                "  workout_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  date_time TEXT,\n" +
                "  duration_in_min INTEGER,\n" +
                "  \n" +
                "  score INTEGER,\n" +
                "  name TEXT\n" +
                ");";
        String exercisesTableCreationStatement = "CREATE TABLE IF NOT EXISTS exercises (\n" +
                "  exercise_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  workout_parent_id INTEGER ,\n" +
                "  type TEXT\n" +
                ",FOREIGN KEY (workout_parent_id) references workouts(workout_id));";

        String setsTableCreationStatement = "CREATE TABLE IF NOT EXISTS sets (\n" +
                "  set_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  exercise_parent_id INTEGER ,\n" +
                "  weight REAL ,\n" +
                "  repetitions INTEGER\n" +
                ",FOREIGN KEY (exercise_parent_id) references exercises(exercise_id));";
        db.execSQL(workoutsTableCreationStatement);
        db.execSQL(exercisesTableCreationStatement);
        db.execSQL(setsTableCreationStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addWorkout(Workout workout){
        Log.i("DataBaseHelper", "Adding a workout to database");
        SQLiteDatabase db= getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("date_time", workout.getStartDateTime().toString());
        cv.put("duration_in_min", workout.getDurationInMinutes());
        cv.put("score", workout.getScore());
        cv.put("name", workout.getName());
        long result= db.insert("workouts",null,cv);
        boolean res;
        if (result<0){
            res=false;
        }
        else res=true;
        int workoutid= (int)getLastInsertedId(db);
        for (Exercise element:workout.getExercises()) {
            res&=addExercise(element,db,workoutid);
        }
        db.close();
        return  res;}
    private boolean addExercise(Exercise exercise,SQLiteDatabase db, long workoutID){
        ContentValues cv= new ContentValues();
        cv.put("type", exercise.getName());
        cv.put("workout_parent_id", (int)workoutID);
        long result= db.insert("exercises",null,cv);
        boolean res;
        if (result<0){
            res=false;
        }
        else res=true;
        int exerciseId= (int)getLastInsertedId(db);
        for (Set element:exercise.getSets()) {
            res&=addSet(element,db,exerciseId);
            DataManager.removeUpdatedOrAddedSets(element);
        }
        return  res;}
    public boolean addExerciseWithParentId(Exercise exercise,long workoutId ){
        SQLiteDatabase db= this.getWritableDatabase();
        boolean res = addExercise(exercise,db,workoutId);
        db.close();
        return res;
    }

    private boolean addSet(Set set, SQLiteDatabase db, long exerciseId){
        ContentValues cv= new ContentValues();
        cv.put("weight", set.getWeight());
        cv.put("repetitions", set.getRepetitions());
        cv.put("exercise_parent_id", (int)exerciseId);
        long result=db.insert("sets",null,cv);
        if (result<0)
            return false;

        else return true;
    }
    public boolean addSetWithParentId(Set set,long exerciseId ){
        Log.i("DataBaseHelper", "Addint a set to database: "+set.toString());
        SQLiteDatabase db= this.getWritableDatabase();
        boolean res = addSet(set,db,exerciseId);
        db.close();
        return res;
    }

    public long getLastInsertedId(SQLiteDatabase db) {
        long Id = -1;

        // Query to retrieve the ID of the last inserted workout
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if (cursor != null && cursor.moveToFirst()) {
            Id = cursor.getLong(0);
            cursor.close();
        }

        // Close the database connection
        return Id;
    }
    public  ArrayList<Workout> getWorkoutsFromDB(){
        ArrayList<Workout> workouts= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorWorkouts =db.rawQuery("SELECT * FROM workouts ;",null);
        if (cursorWorkouts.moveToFirst()){
        do{
            workouts.add(new Workout(LocalDateTime.parse(cursorWorkouts.getString(1)),
                    cursorWorkouts.getInt(2), (byte)cursorWorkouts.getInt(3),
                    cursorWorkouts.getString(4),getExercisesFromWorkoutDB(cursorWorkouts.getInt(0)),cursorWorkouts.getLong(0)));

        }
                while (cursorWorkouts.moveToNext());}
        return workouts;
    };
    public ArrayList<Exercise> getExercisesFromWorkoutDB(int id){
        ArrayList<Exercise> exercises= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorExercises =db.rawQuery("SELECT * FROM exercises WHERE workout_parent_id="+id+";",null);
        if (cursorExercises.moveToFirst()){
            do{
                exercises.add(new Exercise(getSetsFromWorkoutDB(cursorExercises.getInt(0)), cursorExercises.getString(2),cursorExercises.getLong(0),id));
            }
            while (cursorExercises.moveToNext());}
        return exercises;
    };
    public ArrayList<Set> getSetsFromWorkoutDB(int exerciseId){
        ArrayList<Set> sets =new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorSets = db.rawQuery("SELECT * FROM sets WHERE exercise_parent_id = "+exerciseId+";",null);
        if (cursorSets.moveToFirst()){
            do{
                sets.add(new Set((byte) cursorSets.getInt(3),cursorSets.getFloat(2),cursorSets.getLong(0),exerciseId));
            }
            while (cursorSets.moveToNext());}
        return sets;
    }
    public void deleteWorkoutById(long idToDelete){
        Log.i("DataBaseHelper", "Deleting a workout from database");
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("DELETE FROM workouts WHERE workout_id="+idToDelete+";");
        Cursor parentId= db.rawQuery("SELECT workout_parent_id FROM exercises WHERE workout_parent_id="+idToDelete+";",null);
        if (parentId.getCount()==0||!parentId.moveToFirst()){
            db.close();
            parentId.close();
            return;
        }
        long[] idsOfExercises=new long [parentId.getCount()];
        int i=0;
        do{
            idsOfExercises[i]=parentId.getLong(0);
            i++;
        }
        while (parentId.moveToNext());
        parentId.close();
        db.execSQL("DELETE FROM exercises WHERE workout_parent_id="+idToDelete+";");

        String [] values= new String[idsOfExercises.length];
        for (i =0;i< values.length;i++){
            values[i]=Long.toString(idsOfExercises[i]);
        }
         String str= String.join(" , ",values);
         str=str.substring(1,str.length()-1);
         db.execSQL("DELETE FROM sets WHERE exercise_parent_id in ("+str+");");
        db.close();
    }

    public void deleteExerciseById(long exerciseId){
        Log.i("DataBaseHelper", "Deleting an exercise from database");
        SQLiteDatabase db = getWritableDatabase();
        db.delete("exercises","exercise_id = "+exerciseId,null);
        db.delete("sets", "exercise_parent_id = "+exerciseId,null);
        db.close();
    }
    public void deleteSetById(long setId){
        Log.i("DataBaseHelper", "Deleting a set from database");
        SQLiteDatabase db =getWritableDatabase();
        db.delete("sets","set_id = "+setId,null);
        db.close();
    }

    public void updateWorkout(Workout workout){
        Log.i("DataBaseHelper", "Updating a workout from database");
        SQLiteDatabase db =getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date_time", workout.getStartDateTime().toString());
        cv.put("duration_in_min", workout.getDurationInMinutes());
        cv.put("score", workout.getScore());
        cv.put("name",workout.getName());
        long workoutParentId = workout.getWorkoutID();
        db.update("workouts", cv,"workout_id = "+workoutParentId,null);
        for (Exercise exercise : DataManager.getUpdatedOrAddedExercises()){
            long exerciseId = exercise.getExerciseId();
            if (exerciseId==-1){
                addExerciseWithParentId(exercise, workoutParentId);
            }
            else{
                updateExercise(exercise);
            }
        }
        DataManager.clearUpdatedOrAddedSets();
        DataManager.clearUpdatedOrAddedExercise();
        db.close();

    }
    public void updateExercise(Exercise exercise){
        Log.i("DataBaseHelper", "Updating an exercise from database");
        SQLiteDatabase db =getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type", exercise.getName());
        long exerciseId= exercise.getExerciseId();
        db.update("exercises", cv,"exercise_id = "+ exerciseId,null);
        for (Set set : exercise.getSets()){
            long setId = set.getSetId();
            if (setId==-1){
                addSetWithParentId(set, exerciseId);
            }
            else{
                updateSet(set);
            }
        }
        db.close();

    }
    public void updateSet(Set set){
        Log.i("DataBaseHelper", "Updating a set from database: "+set.toString());
        SQLiteDatabase db =getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("weight", set.getWeight());
        cv.put("repetitions", set.getRepetitions());
        db.update("sets", cv,"set_id = "+set.getSetId(),null);
        db.close();
    }
    public void clearDB(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("workouts","",null);
        db.delete("exercises","",null);
        db.delete("sets","",null);

    }
}
