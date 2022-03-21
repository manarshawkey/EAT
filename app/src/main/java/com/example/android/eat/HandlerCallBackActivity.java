package com.example.android.eat;

/*
 * Implementing the Handler.Callback interface is an
 * alternative way to extend the handler class.
 * The Callback interface defines a handleMessage method
 * which acts as a preprocessor of messages passed to the
 * handler. It has a boolean return value, where true
 * meaning that no further processing of the message
 * is required and false indicating that the message
 * needs further processing in which case the message is
 * passed on to the Handler.handleMessage method.
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HandlerCallBackActivity extends AppCompatActivity
            implements Handler.Callback {

    private Handler mHandler;
    private static final String LOG_TAG = HandlerExampleActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_call_back);

        mHandler = new Handler(this){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(LOG_TAG, "Message " + msg.what + " is being handled by " +
                        "Handler.handleMessage()");
            }
        };
        Button button = findViewById(R.id.button_handlerCallback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HandlerCallBackActivity.this);
                dialogBuilder.setTitle("Handler callback");
                dialogBuilder.setMessage(R.string.dialogMessage_handlerCallback);
                dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Message msg = mHandler.obtainMessage(1);
                        mHandler.sendMessage(msg);
                    }
                });
                dialogBuilder.create().show();
            }
        });
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        switch (message.what){
            case 1:
                Log.d(LOG_TAG, "Message " + message.what + " is being processed" +
                        " in Handler.Callback.handleMessage()");
                return true;
            case 2:
                Log.d(LOG_TAG, "Message " + message.what + " is passed on to the " +
                        "Handler.handleMessage() to be processed.");
                return false;
            default:
                return false;
        }
    }
}