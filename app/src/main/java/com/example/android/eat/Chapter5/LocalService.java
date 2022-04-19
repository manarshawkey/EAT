package com.example.android.eat.Chapter5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

/*
* developer.android.com:
* if your service is private to your own application and
* runs int he same process as the client (which is common),
* you should create your interface by extending the Binder
* class and returning an instance of it from onBind().
* The client receives the Binder and can use it to directly
* access public methods available in either the Binder
* implementation or the Service. This is the preferred technique
* when your service is merely a background worker for your own
* application. The only reason you would not create your interface
* this way is because your service is used by other applications
* or across separate processes.
*
*   ***How to set it up***
* 1- In your service, create an instance of Binder that does one
* of the following:
*   - Contains public methods that the client can call.
*   - Returns the current Service instance, which has public methods
*    the client can call.
*   - Returns an instance of another class hosted by the service with
*    public methods the client can call.
* 2- Return this instance of Binder from the onBind() callback method
* and make calls to the bound service using the methods provided.
* 3- In the client, receive the Binder from onServiceConnected() callback
*  method and make calls to the bound service using the methods provided.
* */

public class LocalService extends Service {

    private final String LOG_TAG = LocalService.class.getSimpleName();

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder{
        LocalService getService(){
            return LocalService.this;
        }
    }

    private final Random mGenerator = new Random();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind()");
        return binder;
    }
    /* public method for the client to invoke */
    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
