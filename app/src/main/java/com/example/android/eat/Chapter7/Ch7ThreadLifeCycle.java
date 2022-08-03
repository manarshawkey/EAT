package com.example.android.eat.Chapter7;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class Ch7ThreadLifeCycle extends AppCompatActivity {

    public static final String LOG_TAG = Ch7ThreadLifeCycle.class.getSimpleName();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch7_thread_life_cycle);

        Thread.setDefaultUncaughtExceptionHandler(new ErrorReportExceptionHandler());


        Button uncaughtExceptionButton = findViewById(R.id.button_uncoughtException);
        uncaughtExceptionButton.setOnClickListener(view -> {
            //try to throw an exception without catching it ?

            String message = "There is an uncaught exception handler" +
                    " attached to the main thread, whenever an unexpected error occurs, " +
                    "such as division by zero, the handler's 'uncaughtException() " +
                    "method is called first before terminating the thread, giving " +
                    "application a chance to finish the thread gracefully, or at least, " +
                    "making a note of the error to a network or a file resource. " +
                    "On pressing OK, a division by zero operation is attempted, which will " +
                    "cause the app to crash. But before crashing the error will be logged.";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Uncaught Exception Example");
            builder.setMessage(message);
            builder.create();
            builder.setPositiveButton("OK", (dialogInterface, i) -> introduceAnUncaughtException());
            builder.show();
        });

    }

    private void introduceAnUncaughtException() {
        //int [] a = new int[4];
        //a[4] = 10;
        int x = 10/0;
    }
}

class ErrorReportExceptionHandler implements Thread.UncaughtExceptionHandler{

    private final Thread.UncaughtExceptionHandler defaultHandler;

    public ErrorReportExceptionHandler(){
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    }
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        reportToLogs(throwable);
        defaultHandler.uncaughtException(thread, throwable);
    }
    private void reportToLogs(Throwable throwable){
        Log.d(Ch7ThreadLifeCycle.LOG_TAG, throwable.getMessage());
    }
}