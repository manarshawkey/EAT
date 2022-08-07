package com.example.android.eat.Chapter7;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import com.example.android.eat.R;

public class Ch7ThreadLifeCycle extends AppCompatActivity {

    public static final String LOG_TAG = Ch7ThreadLifeCycle.class.getSimpleName();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch7_thread_life_cycle);

        Thread.setDefaultUncaughtExceptionHandler(new ErrorReportExceptionHandler());

        setupUncaughtExceptionButton();

        setupThreadDefAnonymousInnerClassButton();
        setUpThreadDefPublicClassButton();
        setupThreadDefStaticInnerClassButton();
        setupThreadInterruptionExampleButton();
    }
    private void setupThreadInterruptionExampleButton(){
        Button threadRetain = findViewById(R.id.button_thread_interruption_example);
        threadRetain.setOnClickListener(view -> {
            Intent intent = new Intent(Ch7ThreadLifeCycle.this, ThreadInterruptionActivity.class);
            startActivity(intent);
        });
    }
    private void setupThreadDefAnonymousInnerClassButton(){
        Button anonymousInnerclassButton = findViewById(R.id.button_thread_def_anonymous_inner_class);
        anonymousInnerclassButton.setOnClickListener(view -> {
            String message = "On pressing OK, a thread will be defined as an " +
                    "anonymous inner class and it will be started. With every click on" +
                    " this button, a new thread will be created, there is no way to " +
                    "interrupt the created threads because they're not referenced anywhere" +
                    " so the application can't really control them. If the threads are " +
                    "performing long running tasks, the application risks memory leaks.";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Define and start thread as an anonymous inner class");
            builder.setMessage(message);
            builder.setPositiveButton("OK", (dialogInterface, i) ->
                    new AnyObject().anyMethod());
            builder.show();
        });
    }
    private void setUpThreadDefPublicClassButton(){
        Button publicClassThreadButton = findViewById(R.id.button_thread_def_standalone_class);
        publicClassThreadButton.setOnClickListener(view -> {
            String message = "On pressing ok, a thread object will be initialized and started " +
                    "within a standalone class, " +
                    " the reference to the thread object is overwritten whenever a user presses ok," +
                    " the previously created threads aren't terminated, nor reused. This is a potential " +
                    "risk of memory leak, if the threads are performing long running tasks.";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Define a thread as Standalone class and start it");
            builder.setMessage(message);
            builder.setPositiveButton("ok", (dialogInterface, i) -> {
                new AnyObject1().anyMethod();
            });
            builder.show();
        });
    }
    private void setupThreadDefStaticInnerClassButton(){
        Button staticInnerClass = findViewById(R.id.button_thread_def_static_inner_class);
        staticInnerClass.setOnClickListener(view -> {
            String message = "On pressing ok, an object of a static inner class is created, " +
                    "a method of this object is invoked and it will overwrite a reference " +
                    "to a thread object and it will start the thread. Whenever OK is pressed, " +
                    "the method is invoked and the thread reference is overwritten. The old " +
                    "thread objects are not checked to be terminated nor reused. That's a potential " +
                    "memory leak, if the threads are performing long running tasks.";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message);
            builder.setTitle("Define a thread inside a static inner class");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                new AnyObject2().anyMethod();
            });
            builder.show();
        });
    }
    private void setupUncaughtExceptionButton() {
        Button uncaughtExceptionButton = findViewById(R.id.button_uncoughtException);
        uncaughtExceptionButton.setOnClickListener(view -> {

            String message = "There is an uncaught exception handler" +
                    " attached to the main thread, whenever an unexpected error occurs, " +
                    "such as division by zero, the handler's 'uncaughtException() " +
                    "method is called first before terminating the thread, giving " +
                    "application a chance to finish the thread gracefully, or at least, " +
                    "making a note of the error to a network or a file resource. " +
                    "On pressing OK, a division by zero operation is attempted, which will " +
                    "cause the app to crash. But before crashing the error will be logged.";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Uncaught Exception Example");
            builder.setMessage(message);
            builder.create();
            builder.setPositiveButton("OK", (dialogInterface, i) -> introduceAnUncaughtException());
            builder.show();
        });
    }

    //try to throw an exception without catching it
    private void introduceAnUncaughtException() {
        //int [] a = new int[4];
        //a[4] = 10;
        int x = 10/0;
    }

    //Thread Definition Examole 1: Anonymous Inner class
    public class AnyObject {
        @UiThread
        public void anyMethod(){
            //the thread is defined an an anonymous class, there is
            //no reference to the thread instance, this leaves the thread
            //out of control, hence, it can't be influenced by the application.
            //It can't be interrupted or terminated by the application if it is
            //no longer needed.

            //if the method anyMethod() is called in response to a button click
            //for example, whenever the button is clicked, new instance of
            //the thread is created over and over again, using up more memory with
            //every creation.
            new Thread(){
                public void run(){
                    //do a long running task
                    Log.d(LOG_TAG, "Anonymous inner class run method is executing");
                }
            }.start();
        }
    }
}

class ErrorReportExceptionHandler implements Thread.UncaughtExceptionHandler{

    private final Thread.UncaughtExceptionHandler defaultHandler;

    public ErrorReportExceptionHandler(){
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    }
    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        reportToLogs(throwable);
        defaultHandler.uncaughtException(thread, throwable);
    }
    private void reportToLogs(Throwable throwable){
        Log.d(Ch7ThreadLifeCycle.LOG_TAG, throwable.getMessage());
    }
}

//Thread Definition Example 2: Public Thread
//The thread can be defined as a standalone class, not
//directly defined by the class that runs it.

//The standalone class holds no reference to the outer class,
//but the number of classes that need to be defined can grow large.
class MyThread extends Thread{
    @Override
    public void run() {
        //perform a long running task
        Log.d(Ch7ThreadLifeCycle.LOG_TAG, "MyThread::run()");
    }
}
class AnyObject1{
    private MyThread myThread;
    //on every call to this method, the reference to myThread object is
    //overwritten in every execution and it's not usable anymore.
    //new threads are created, while the previously started threads
    //might still be alive.
    @UiThread
    public void anyMethod(){
        myThread = new MyThread();
        myThread.start();
    }
}
//Thread Definition Example 3: Static Inner Class
//Instead of defining the thread as an inner class, it can be
//defined as a static inner class, in which case, it will be defined
//in the class object not the instance. i.e it will hold a reference
//to the outer class not the enclosing outer class object.
//As a result, the memory allocated by an instance can't be
//leaked due to the thread reference -as the thread references the class itself not the instance-.
class AnyObject2{

    private static class MyThread extends Thread{
        @Override
        public void run() {
            //perform a long running task
            Log.d(Ch7ThreadLifeCycle.LOG_TAG, "thread as a static inner class::run()");
        }
    }
    private MyThread myThread;

    //Again, if anyMethod() is called frequently, myThread is overwritten
    //on every execution, and the previously started threads are not reused.
    public void anyMethod(){
        myThread = new MyThread();
        myThread.start();
    }
}

