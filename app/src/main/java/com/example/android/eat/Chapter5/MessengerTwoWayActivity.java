package com.example.android.eat.Chapter5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class MessengerTwoWayActivity extends AppCompatActivity {

    private final String LOG_TAG = MessengerTwoWayActivity.class.getSimpleName();
    private Messenger mRemoteServiceMessenger;
    private boolean mBound = true;
    static final int MSG_TWO_WAY = 2;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mRemoteServiceMessenger = new Messenger(iBinder);
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
        setContentView(R.layout.activity_messenger_two_way);

        Utils.setActivityTitle(this, "Messenger Two Way");

        setUpSendMessageButton();
        Utils.displayDialog("This example illustrates two-way communication " +
                "between an activity and a service executing in different " +
                "processes. The activity sends a message with a replyTo argument " +
                ", to a remote service. The remote service receives the Message and send " +
                "a new message back to the activity. On pressing the button " +
                "the what value of the message sent back from the service to the activity " +
                "should be displayed. That is the value '3'", "Two-way Communication", this);
    }

    private void setUpSendMessageButton() {
        Button twoWayMessage = findViewById(R.id.button_twoWaySendMessage);
        twoWayMessage.setOnClickListener(view -> {
            if(mBound){
                Message msg = Message.obtain(null, MSG_TWO_WAY, 0, 0);
                msg.replyTo = new Messenger(new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Log.d(LOG_TAG, "Message sent back - Msg.what = " + msg.what);
                        Toast.makeText(MessengerTwoWayActivity.this,
                                "Message sent back from service to activity - Msg.what = " + msg.what, Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    mRemoteServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent bindingService = new Intent(this, MessengerService.class);
        bindService(bindingService, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
}