package com.example.androidgymapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Spannable;

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
    public boolean addExercise(Exercise exercise,SQLiteDatabase db, long workoutID){
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
        }
        return  res;}

    public boolean addSet(Set set, SQLiteDatabase db, long exerciseId){
        ContentValues cv= new ContentValues();
        cv.put("weight", set.getWeight());
        cv.put("exercise_parent_id", (int)exerciseId);
        long result=db.insert("sets",null,cv);
        if (result<0)
            return false;

        else return true;
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
        db.close();

        return Id;
    }

}
