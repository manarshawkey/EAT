package com.example.android.eat.Chapter5;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/*
* In this example, a Service executes in the server process,
* and communicates with an activity in the client process.
* Hence, the service implements a Messenger and passes it
* to the activity -the client- which in return can pass
* Message objects to the Service.
* */
public class WorkerThreadService extends Service {

    private static final String LOG_TAG = WorkerThread.class.getSimpleName();
    WorkerThread mWorkerThread;
    Messenger mWorkerMessenger;
    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate();
        mWorkerThread = new WorkerThread(); //this line is not in example by the book   .
        mWorkerThread.start();
    }

    /*
    * Worker thread has prepared a looper and a handler, in the onCreate()
    */

    private void onWorkerPrepared(){
        /*
        * A handler to the worker thread is connected to the Messenger upon construction,
        * this handler will process incoming messages from client processes.
        * */
        Log.d(LOG_TAG, "onWorkerPrepared()");
        mWorkerMessenger = new Messenger(mWorkerThread.mWorkerHandler);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        /*
        * A binding client receives the IBinder object of the Messenger,
        * so that the client can communicate with the associated
        * Handler in the Service.
        * */
        Log.d(LOG_TAG, "onBind()");
        mWorkerThread = new WorkerThread();
        mWorkerMessenger = new Messenger(mWorkerThread.mWorkerHandler);
        return mWorkerMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy()");
        super.onDestroy();
        mWorkerThread.quit();
    }

    private class WorkerThread extends Thread{
        Handler mWorkerHandler;

        @Override
        public void run() {
            Log.d(LOG_TAG, "WorkerThread::run()");
            Looper.prepare();
            mWorkerHandler = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    /*
                    * Process the incoming messages.
                    */
                    Log.d(LOG_TAG, "Handling message: " + msg.what);
                }
            };
            //associate this newly created handler to the Messenger,
            //so that it process the incoming messages from client processes.
            onWorkerPrepared();
        }
        public void quit(){
            Log.d(LOG_TAG, "WorkerThread::quit()");
            mWorkerHandler.getLooper().quit();
        }
    }
}