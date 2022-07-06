package com.example.android.eat.Chapter6;

import android.util.Log;

/**
 * ** No Memory Leak Example **
 * On most occasions, the programmer wants to separate the
 * execution environment (Thread) from the task (Runnable).
 * If you create a new Runnable as an inner class, it will
 * hold a reference to the outer class during the execution,
 * even if it is run by a static inner class.
 *
 * The thread hold a reference to the outer class not the outer
 * object so no expected memory leak.
 *
 * **/

public class OuterI {
    private final String LOG_TAG = OuterI.class.getSimpleName();

    public void sampleMethod(){
        SampleThread sampleThread = new SampleThread(new Runnable() {
            @Override
            public void run() {
                Object sampleObject = new Object();
                Log.d(LOG_TAG, "sampleMethod is executing: " +
                        "This is a runnable instance executing as an anonymous" +
                        " inner class, separated from the execution environment " +
                        "(Thread). The Thread class is a static inner, it holds" +
                        " a reference to the outer class not the outer object so" +
                        " no expected memory leaks");
            }
        });
        sampleThread.start();
    }

    private static class SampleThread extends Thread{
        public SampleThread(Runnable runnable){
            super(runnable);
        }
    }
}
