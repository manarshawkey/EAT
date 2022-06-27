package com.example.android.eat.Chapter6;

import android.util.Log;


/*
   ** Potential Memory Leak Example: Non static inner class **
   * In this example, Class 'Outer' has an inner class 'SampleThread'
   * that performs a long-running operation. The inner class is a
   * member of the enclosing object, hence it has a reference to the
   * all its members. The thread in the inner class consequently has
   * a reference to the outer class that will never be marked as
   * reclaimable by the Garbage Collector until the thread is done
   * executing even if the thread is not using any of the member of
   * the outer class.
   *
   * Note: the code works fine without any errors or warnings.
* */
public class Outer {
    final static String LOG_TAG = Outer.class.getSimpleName();

    final int [] arr = new int[10000000];
    public void sampleMethod(){
        for(int i = 0; i < arr.length; i++)
            arr[i] = i;
        new SampleThread().start();
    }
    private class SampleThread extends Thread{
        @Override
        public void run(){
            Log.d(LOG_TAG, "executing runnable");
            try {
                sleep(10000);
                Log.d(LOG_TAG, "done sleeping");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
