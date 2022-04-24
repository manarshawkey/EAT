package com.example.android.eat.Chapter5;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

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
        super.onCreate();
        mWorkerThread = new WorkerThread(); //this line is not in example by the book.
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

        /*
        * BUG: can't invoke the method getBinder on a null reference.
        * Sounds like a race condition. The mWorkerMessenger object is
        * assigned in the worker thread that starts within the service onCreate
        * method. The call to onBind maybe is happening while the execution
        * of the onWorker prepared -within which mWorkerMessenger is assigned-
        * is not finished yet.
        * */
        return mWorkerMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWorkerThread.quit();
    }

    private class WorkerThread extends Thread{
        Handler mWorkerHandler;

        @Override
        public void run() {
            Looper.prepare();
            mWorkerHandler = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    /*
                    * Process the incoming messages.
                    */
                    Log.d(LOG_TAG, "Handling message: " + msg.what);
                    Toast.makeText(WorkerThreadService.this, "hello", Toast.LENGTH_SHORT).show();
                }
            };
            //associate this newly created handler to the Messenger,
            //so that it process the incoming messages from client processes.
            onWorkerPrepared();
            Looper.loop();
        }
        public void quit(){
            mWorkerHandler.getLooper().quit();
        }
    }
}
