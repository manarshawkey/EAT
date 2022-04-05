package com.example.android.eat.Chapter5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.util.LogPrinter;


import com.example.android.eat.ISynchronous;
import com.example.android.eat.R;

import java.util.concurrent.CountDownLatch;

public class SynchronousRPCActivity extends AppCompatActivity {

    private final String LOG_TAG = SynchronousRPCActivity.class.getSimpleName();

    private final CountDownLatch mLatch = new CountDownLatch(1);

    /**
     * Binder object of the server process
     */
    private final ISynchronous.Stub mBinder = new ISynchronous.Stub() {
        @Override
        public String getThreadNameFast() throws RemoteException {
            return Thread.currentThread().getName();
        }

        @Override
        public String getThreadNameSlow(long sleep) throws RemoteException {
            SystemClock.sleep(sleep);
            return Thread.currentThread().getName();
        }

        @Override
        public String getThreadNameBlocking() throws RemoteException {
            try {
                mLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Thread.currentThread().getName();
        }

        @Override
        public String getThreadNameUnblock() throws RemoteException {
            mLatch.countDown();
            return Thread.currentThread().getName();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronous_rpcactivity);

        setActivityTitle();

        ISynchronous mISynchronous = ISynchronous.Stub.asInterface(mBinder);

        Thread t = new Thread(() -> {
            String threadNameUnblock = null;
            try {
                Log.d(LOG_TAG, "another thread unblocking.");
                threadNameUnblock = mISynchronous.getThreadNameUnblock();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(LOG_TAG, "ThreadNameUnblock: " + threadNameUnblock);

        });
        t.start();
        Thread t1 = new Thread(() -> {
            try {
                String threadNameFast = mISynchronous.getThreadNameFast();
                Log.d(LOG_TAG, "ThreadNameFast: " + threadNameFast);
                String threadNameSlow = mISynchronous.getThreadNameSlow(3000);
                Log.d(LOG_TAG, "ThreadNameSlow: " + threadNameSlow);
                String threadNameBlocking = mISynchronous.getThreadNameBlocking();
                Log.d(LOG_TAG, "ThreadNameBlocking: " + threadNameBlocking);


            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }
    private void setActivityTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Synchronous RPC");
        }
    }
}