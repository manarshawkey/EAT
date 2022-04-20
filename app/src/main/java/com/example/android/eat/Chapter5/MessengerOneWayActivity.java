package com.example.android.eat.Chapter5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;

import com.example.android.eat.R;

public class MessengerOneWayActivity extends AppCompatActivity {

    private final String LOG_TAG = MessengerOneWayActivity.class.getSimpleName();

    private boolean mBound = false;
    private Messenger mRemoteServiceMessenger = null;

    private final ServiceConnection mRemoteConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d(LOG_TAG, "ServiceConnection::onServiceConnected()");
            mRemoteServiceMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteServiceMessenger = null;
            mBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_one_way);

        setActivityTitle();

        setupOneWaySendButton();

    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isBound = bindService(new Intent(this, MessengerService.class),
                mRemoteConnection, Context.BIND_AUTO_CREATE);
        Log.d(LOG_TAG, "binding status in onStart(): " + isBound);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mRemoteConnection);
            mBound = false;
        }
    }

    private void setupOneWaySendButton() {
        Button oneWaySend = findViewById(R.id.button_oneWaySend);
        oneWaySend.setOnClickListener(view -> {
            if(mBound){
                Log.d(LOG_TAG, "service is bound");
                try {
                    mRemoteServiceMessenger.send(Message.obtain(null, 2, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setActivityTitle(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Messenger One Way Message Passing");
        }
    }
}