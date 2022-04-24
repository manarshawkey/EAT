package com.example.android.eat.Chapter5;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class MessengerService extends Service {

    private final String LOG_TAG = MessengerService.class.getSimpleName();
    /**
     * Command to the service to display a message
     */
    static final int MSG_SAY_HELLO = 1;

    /**
     * Handler of incoming messages from clients.
     */
    static class IncomingHandler extends Handler {
        private final Context applicationContext;

        IncomingHandler(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show();
                    break;
                case MessengerTwoWayActivity.MSG_TWO_WAY:
                    try {
                        msg.replyTo.send(Message.obtain(null, 3));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    Messenger mMessenger;

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        mMessenger = new Messenger(new IncomingHandler(this));
        return mMessenger.getBinder();
    }
}
