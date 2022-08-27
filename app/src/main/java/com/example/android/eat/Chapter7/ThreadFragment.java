package com.example.android.eat.Chapter7;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class ThreadFragment extends Fragment {
    private ThreadRetainWithFragmentActivity mActivity;
    private MyThread myThread;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mActivity = (ThreadRetainWithFragmentActivity) activity;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mActivity = null;
    }
    public void execute(){
        myThread = new MyThread();
        myThread.start();
    }
    private class MyThread extends Thread{
        @Override
        public void run(){
            Log.d(ThreadRetainWithFragmentActivity.LOG_TAG, "thread starts here");
            final String text = getTextFromNetwork();
            mActivity.setText(text);
            Log.d(ThreadRetainWithFragmentActivity.LOG_TAG, "thread finishes execution");
        }

        private String getTextFromNetwork() {
            SystemClock.sleep(3000);
            return "Text from network";
        }
    }
}
