package com.example.android.eat.Chapter7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.eat.R;

public class ThreadRetainWithFragmentActivity extends AppCompatActivity {

    public static final String LOG_TAG = ThreadRetainWithFragmentActivity.class.getSimpleName();
    private ThreadFragment mThreadFragment;
    private TextView mTextView;
    private final String THREAD_FRAGMENT_TAG = "threadFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_retain_with_fragment);

        Log.d(LOG_TAG, "onCreate()");
        mTextView = findViewById(R.id.textView_thread_retain_with_fragment);
        setupStartThreadButton();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mThreadFragment = (ThreadFragment) fragmentManager.findFragmentByTag(THREAD_FRAGMENT_TAG);
        if(mThreadFragment == null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            mThreadFragment = new ThreadFragment();
            transaction.add(mThreadFragment, THREAD_FRAGMENT_TAG);
            transaction.commit();
        }

    }

    private void setupStartThreadButton() {
        Button button = findViewById(R.id.button_thread_retain_with_fragment_start_thread);
        button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Retaining a Thread using a Fragment");
            builder.setMessage("On pressing OK, a thread instance will start " +
                    "executing, the thread is contained inside a retainable fragment " +
                    "instance, the fragment is attached to the activity object. When the " +
                    "activity is destroyed, on configuration change for example, " +
                    "the fragment is not destroyed, but it is retained in the new activity" +
                    " instance, the executing thread is retained within the " +
                    "freament and its execution result is used in the new activity. " +
                    "On clicking OK, rotate the device, the text will change after few seconds after " +
                    "rotation.");
            builder.setPositiveButton("OK", ((dialogInterface, i) -> {
                mThreadFragment.execute();
            }));
            builder.show();
        });
    }

    public void setText(String text) {
        runOnUiThread(()->{
            mTextView.setText(text);
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy()");
    }
}