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
    private final ConsumeAndQuitThread mConsumeAndQuitThread = new ConsumeAndQuitThread();
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

        mConsumeAndQuitThread.start();
        Button consumeAndQuitButton = findViewById(R.id.button_consumeAndQuit);
        consumeAndQuitButton.setOnClickListener(view -> {
            Log.d(LOG_TAG, "consume and quit button clicked");
            for(int i = 0; i < 10; i++){
                int finalI = i;
                new Thread(()->{
                   //SystemClock.sleep(new Random().nextInt(10));
                   mConsumeAndQuitThread.enqueueData(finalI);
                }).start();
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

    private static class ConsumeAndQuitThread extends Thread implements MessageQueue.IdleHandler{

        public Handler mHandler;
        private boolean isFirstIdle = true;

        public ConsumeAndQuitThread(){
            super("ConsumeAndQuitThread"); //define thread name
        }

        @Override
        public void run() {
            /*
            * Attempt to attach the main looper to another thread,
            * throws a runtime exception
            */
            Looper.prepareMainLooper();
            //Looper.prepare();

            mHandler = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    SystemClock.sleep(new Random().nextInt(10));
                    Log.d(LOG_TAG, "consuming message: " + msg.what);
                    Log.d(LOG_TAG, "args1: " + msg.arg1 + " args2: " + msg.arg2);
                }
            };
            Log.d(LOG_TAG, "adding an idle handler.");
            Looper.myQueue().addIdleHandler(this);
            Looper.loop();
        }


        /*
         * This use case is only effective when the producer threads
         * enqueue messages without delay, because only then the queue
         * will be idle at the very beginning -no messages in the queue-
         * and after processing all messages, that the producer thread has
         *  no idle time between processing messages.
         * THIS DOES NOT WORK AS EXPECTED THOUGH. CONTINUE DEBUGGING :)
         */
        @Override
        public boolean queueIdle() {
            if(isFirstIdle){ //true only when the queue is first empty, let it pass
                Log.d(LOG_TAG, "Message queue is first idle.");
                isFirstIdle = false;
                return true; //the idleHandler is still active and can receive tasks to execute
            }
            //it is idle after executing all the messages in the queue,
            //time to terminate the thread
            Log.d(LOG_TAG, "terminating the thread, when the queue" +
                    " is idle after processing the all the messages.");
            mHandler.getLooper().quit();
            return false;
        }
        public void enqueueData(int i){
            if(mHandler != null){
                Log.d(LOG_TAG, "enqueueing message: " + i);

               switch (i % 2){
                    case 0:
                        mHandler.sendMessage(constructSimpleDataMessage(i));
                        break;
                    case 1:
                        mHandler.sendMessage(constructTaskMessage(i));
                        break;
                    default:
                        break;
               }
            }
        }
        private Message constructSimpleDataMessage(int i){
            return mHandler.obtainMessage(i, i+1, i+2);
        }

        private Message constructTaskMessage(int i){
            return Message.obtain(mHandler, () -> {
                SystemClock.sleep(1);
                Log.d(LOG_TAG, "Executing a task Message: " + i);
            });
        }
    }
}