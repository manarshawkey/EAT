package com.example.android.eat;
/*
 * Purpose: this example simulates a long-running operation
 * that is started when the user clicks a button. The long-running
 * operation is executed on a background thread, meanwhile, the UI thread
 * displays a progress bar that is removed when the background thread
 * reports the results back to the UI thread.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Random;

public class HandlerExampleActivity extends AppCompatActivity {

    private static final String LOG_TAG = HandlerExampleActivity.class.getSimpleName();
    private static final int SHOW_PROGRESS_BAR = 1;
    private static final int HIDE_PROGRESS_BAR = 0;
    private final Handler mUiHandler;
    ProgressBar mProgressBar;
    private BackgroundThread mBackgroundThread;

    public HandlerExampleActivity() {
        mUiHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == SHOW_PROGRESS_BAR) {
                    Log.d(LOG_TAG, "MainLooper is handling a message " +
                            "to show progress bar");
                    mProgressBar.setVisibility(View.VISIBLE);
                }else if(msg.what == HIDE_PROGRESS_BAR){
                    Log.d(LOG_TAG, "MainLooper is handling a message " +
                            "to hide progress bar");
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();
        mBackgroundThread.doWork();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundThread.exit();
    }

    private class BackgroundThread extends Thread{
        Handler mBackgroundHandler;

        @Override
        public void run() {
            Log.d(LOG_TAG, "Background Thread run method.");
            //associate a looper with this thread
            Looper.prepare();
            //initialize a handler
            mBackgroundHandler = new Handler();
            //start dispatching messages
            Looper.loop();
        }
        public void doWork(){
            Log.d(LOG_TAG, "BackgroundThread::doWork()");
            if(mBackgroundHandler == null)
                mBackgroundHandler = new Handler();
            mBackgroundHandler.post(() -> {
                //obtain a message to show the progress bar in the main ui thread
                Message uiMessage = mUiHandler.obtainMessage(SHOW_PROGRESS_BAR);
                Log.d(LOG_TAG, "enqueuing msg to show progress bar");
                mUiHandler.sendMessage(uiMessage);
                //sleep for a little while
                int sleepTime = new Random().nextInt(5000);
                //obtain a message to hide progress bar in the main ui thread
                uiMessage = mUiHandler.obtainMessage(HIDE_PROGRESS_BAR);
                Log.d(LOG_TAG, "enqueuing msg to hide progress bar");
                mUiHandler.sendMessageDelayed(uiMessage, sleepTime);
            });

        }
        public void exit(){
            Log.d(LOG_TAG, "Background Thread Looper is quitting safely.");
            mBackgroundHandler.getLooper().quitSafely();
        }
    }
}