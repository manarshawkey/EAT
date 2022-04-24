package com.example.android.eat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Utils {
    public static void displayDialog(String message, String title, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> { });
        builder.create().show();
    }
    public static void setActivityTitle(AppCompatActivity activity, String title){
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
}
