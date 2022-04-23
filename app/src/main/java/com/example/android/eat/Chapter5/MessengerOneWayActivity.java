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
import android.widget.Toast;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class MessengerOneWayActivity extends AppCompatActivity {

    private final String LOG_TAG = MessengerOneWayActivity.class.getSimpleName();

    private boolean mBound = false;
    private Messenger mRemoteServiceMessenger = null;

    private final ServiceConnection mRemoteConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
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

        Utils.displayDialog("In this example, this activity representing a client" +
                        " process communicates with a service executing in a server process." +
                        " The service has an associated messenger and it passes a reference of it " +
                        "to the client process when the binding completes. On pressing the button, a message" +
                        " is sent from the client process to the server process, a toast msg with the text" +
                        " 'hello' is displayed as a result of executing the message in the server process.",
                "One Way Communication", this);

        setupOneWaySendButton();

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, WorkerThreadService.class),
                mRemoteConnection, Context.BIND_AUTO_CREATE);
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
                try {
                    mRemoteServiceMessenger.send(Message.obtain(null, MessengerService.MSG_SAY_HELLO,
                            0, 0));
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