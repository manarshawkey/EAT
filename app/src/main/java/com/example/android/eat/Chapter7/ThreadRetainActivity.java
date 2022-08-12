package com.example.android.eat.Chapter7;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.eat.R;

public class ThreadRetainActivity extends AppCompatActivity {

    private static final String LOG_TAG = ThreadRetainActivity.class.getSimpleName();
    private TextView mTextView;
    private static MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_retain);
        setupStartThreadButton();

        mTextView = findViewById(R.id.textView_thread_retain);
        Object retainedObject = getLastCustomNonConfigurationInstance();
        if(retainedObject != null){
            myThread = (MyThread) retainedObject;
            //register the new activity instance to the retained thread
            //so that when the thread finishes, the UI of the new activity is updated.
            myThread.attach(this);
        }
    }

    private void setText(final String text){
        runOnUiThread(() -> {
            mTextView.setText(text);
        });
    }
    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if(myThread != null && myThread.isAlive()){
            return myThread;
        }
        return null;
    }

    private void setupStartThreadButton(){
        Button button = findViewById(R.id.button_thread_retain_start_thread);
        button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thread Retain Example I ");
            builder.setMessage("After clicking ok, please make sure to rotate the device." +
                    " On clicking OK, an instance of a thread is initialized and it's started. " +
                    "The thread execution simulates a long running task. A 'Hello there' " +
                    "message is displayed in a textview, indicating that the thread is done executing, " +
                    "and the result returned is displayed in the UI. " +
                    "If the device is rotated while the thread is still executing, " +
                    "the activity is destroyed and a new activity object is created, but we retain" +
                    " the thread object so that its progress is not lost, and we use the result " +
                    "of its execution to change the ui of the new activity object." +
                    " After clicking OK, please make sure to rotate the device, the textView" +
                    " at the rotated activity should change within seconds after the rotation.");
            builder.setPositiveButton("OK", ((dialogInterface, i) -> {
                myThread = new MyThread(this);
                myThread.start();
            }));
            builder.show();
        });
    }

    private static class MyThread extends Thread{
        private ThreadRetainActivity mActivity;

        public MyThread(ThreadRetainActivity activity){
            mActivity = activity;
        }
        private void attach(ThreadRetainActivity activity){
            Log.d(LOG_TAG, "attaching the new activity object to the thread.");
            mActivity = activity;
        }

        @Override
        public void run() {
            SystemClock.sleep(5000);
            mActivity.setText("hello there!");
        }
    }
}