package com.example.android.eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class LooperActivity extends AppCompatActivity {

    LooperThread mLooperThread;
    private static final String LOG_TAG = LooperActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);

        /*
         * Start the worker thread, so it is ready to
         * process messages from the queue.
         */
        mLooperThread = new LooperThread();
        mLooperThread.start();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            /*
             * There is race condition between the setup of mHandler on a background thread
             * and this usage on the UI thread. Hence, validate that mHandler is available.
             */
            if(mLooperThread.mHandler != null){
                /*
                 * Initialize a Message-object with the what argument arbitrarily set to 0.
                 */
                Message msg = mLooperThread.mHandler.obtainMessage(0);
                /*
                 * Insert the message in the queue.
                 */
                mLooperThread.mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * Definition of the worker thread, acts as a consumer of the message queue
     */
    private static class LooperThread extends Thread{
        public Handler mHandler;

        @Override
        public void run() {
            /*
             * Associate a looper with this thread. This also implicitly associate
             * a message queue with the thread.
             */
            Looper.prepare();

            /*
             * Setting up a handler, to be used by the producer thread,
             * to insert messages to the queue.
             * Using the default constructor, this handler will bind to
             * the Looper of the current thread, that we just attached to
             * the thread in the previous step.
             */
            mHandler = new Handler(Looper.myLooper()){

                /**
                 * This callback runs when the message has been dispatched
                 * to the worker thread
                 */
                @Override
                public void handleMessage(@NonNull Message msg) {
                    Log.d(LOG_TAG, "Handler::handleMessage()");
                    if(msg.what == 0){
                        doLongRunningOperation();
                    }
                }
            };

            /*
             * Start dispatching messages from the queue
             * to the consumer thread.
             * This is a blocking call, so the worker thread will not finish (?)
             */
            Looper.loop();
        }
    }
    private static void doLongRunningOperation(){
        Log.d(LOG_TAG, "doLongRunningProcess()");
        HttpURLConnection urlConnection;
        try{
            URL urlObject = new URL("https://www.google.com/");
            urlConnection = (HttpURLConnection) urlObject.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();
            Log.d(LOG_TAG, "Response code: " + urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy(), quitting the looper");

        /*
         * Terminate the background thread. The call to Looper.quit()
         * stops the dispatching of messages and releases Looper.loop()
         * from blocking so the run method can finish, leading to the
         * termination of the thread.
         * quit(): no more message dispatching including messages that
         * passed the dispatch barrier.
         */
        /*
         * quitSafely(): instead, discards messages that has not
         * passed the dispatch barrier. Messages that have passed
         * and ready to be dispatched are processed first, then
         * the looper is terminated.
         */

        mLooperThread.mHandler.getLooper().quit();
    }
}