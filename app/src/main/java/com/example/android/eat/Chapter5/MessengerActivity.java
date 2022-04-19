package com.example.android.eat.Chapter5;

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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.eat.R;

public class MessengerActivity extends AppCompatActivity {

    private final String LOG_TAG = MessengerActivity.class.getSimpleName();

    /** Messenger for communicating with the service. */
    Messenger mService = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean bound;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            Log.d(LOG_TAG, "service is connected.");
            mService = new Messenger(service);
            bound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        //sayHello(new View(this));
        setUpSendMessageButton();
    }

    private void setUpSendMessageButton() {
        Button sendMessage = findViewById(R.id.button_sendMsgToService);
        sendMessage.setOnClickListener(view -> {
            if(bound){
                Toast.makeText(this, "bound successfully", Toast.LENGTH_SHORT).show();
                Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
                try {
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to the service
        boolean isBound = bindService(new Intent(this, MessengerService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        Log.d(LOG_TAG, "binding status: " + isBound);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }
}