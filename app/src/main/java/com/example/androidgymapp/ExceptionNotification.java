package com.example.androidgymapp;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class ExceptionNotification {

    public static void somethingWentWrong(FragmentActivity activity,String text){
        Toast myToast = Toast.makeText( activity, text, Toast.LENGTH_SHORT);
        myToast.show();
    }
}
