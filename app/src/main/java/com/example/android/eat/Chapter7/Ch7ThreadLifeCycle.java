package com.example.android.eat.Chapter7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.android.eat.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ch7ThreadLifeCycle extends AppCompatActivity {

    public static final String LOG_TAG = Ch7ThreadLifeCycle.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch7_thread_life_cycle);
        Thread.setDefaultUncaughtExceptionHandler(new ErrorReportExceptionHandler());

        //try to throw an exception without catching it ?
        //throw new Exception("attempt to throw an exception from the UI thread.");


    }
}

class ErrorReportExceptionHandler implements Thread.UncaughtExceptionHandler{

    private final Thread.UncaughtExceptionHandler defaultHandler;

    public ErrorReportExceptionHandler(){
        Log.d(Ch7ThreadLifeCycle.LOG_TAG, "constructing ");
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        reportTofile(throwable);
        defaultHandler.uncaughtException(thread, throwable);
    }
    private void reportTofile(Throwable throwable){
        File exceptionsLog = new File("ExceptionsLog.txt");
        if(!exceptionsLog.exists()){
            Log.d(Ch7ThreadLifeCycle.LOG_TAG, "file DNE");
            try {
                boolean create = exceptionsLog.createNewFile();
                if(!create){
                    Log.d(Ch7ThreadLifeCycle.LOG_TAG, "couldn't create file.");
                }
                FileWriter writer = new FileWriter(exceptionsLog);
                writer.write(throwable.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.d(Ch7ThreadLifeCycle.LOG_TAG, "file already exists");
        }

    }
}