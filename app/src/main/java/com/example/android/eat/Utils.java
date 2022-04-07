package com.example.android.eat;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Utils {
    public static void displayDialog(String message, String title, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> { });
        builder.create().show();
    }
}
