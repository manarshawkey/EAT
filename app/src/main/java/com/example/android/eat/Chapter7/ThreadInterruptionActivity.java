package com.example.android.eat.Chapter7;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadInterruptionActivity extends AppCompatActivity {

    private static final String LOG_TAG = ThreadInterruptionActivity.class.getSimpleName();

    private MyThread myThread = new MyThread();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_interruption);
        Utils.setActivityTitle(this, "Thread Interruption Example");

        setupAlertDialog();
    }

    private void setupAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thread Interruption Example");
        builder.setMessage("On pressing OK, a thread will start executing, the thread " +
                "repeatedly prints some log messages, until it is interrupted. " +
                "The thread is interrupted on pausing or destroying the activity. If interrupted, " +
                "an interrupted exception is thrown and the execution of the run " +
                "method is stopped. Check the log message to see that the printing stops after interruption.");
        builder.setPositiveButton("OK", ((dialogInterface, i) -> {myThread.start();}));
        builder.show();
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                Log.d(LOG_TAG, "thread starts running now");
                try {
                    SystemClock.sleep(1000);
                    if (isInterrupted())
                        throw new InterruptedException(); //throwing an interrupted exception resets the interrupted flag to false
                    Log.d(LOG_TAG, "thread is done executing");
                }catch (InterruptedException e){
                    Log.d(LOG_TAG, "thread is interrupted");
                    //so when we catch the exception we need to interrupt again to set the flag to true to exit the while loop
                    currentThread().interrupt();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
        myThread.interrupt();
    }
    private static String makeHttpRequest() throws IOException {
        URL url = new URL("https://google.com");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int responseCode = urlConnection.getResponseCode();
        if(responseCode != 200) {
            return null;
        }
        StringBuilder response = new StringBuilder();
        try{
            String line;
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null){
                response.append(line);
                line = bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response.toString();
    }
}