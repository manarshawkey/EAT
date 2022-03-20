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
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HandlerCallBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_call_back);
    }
}