package com.example.android.eat.Chapter5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

import java.io.FileDescriptor;

public class BindingActivity extends AppCompatActivity {

    private final String LOG_TAG = BindingActivity.class.getSimpleName();
    private LocalService mService;
    private boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(LOG_TAG, "ServiceConnection::onServiceConnected()");
            LocalService.LocalBinder binder = (LocalService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //Log.d(LOG_TAG, "ServiceConnection::onServiceDisconnected()");
            mBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        setupTestButton();
        setupActivityTitle();
        Utils.displayDialog("On Clicking the button, a call to a bound service's public " +
                "method 'generateRandomInt()' is initiated, and the generated number " +
                "is displayed in a Toast message.", "Local Service", this);
    }

    private void setupTestButton() {
        Button bindingTestButton = findViewById(R.id.button_bindingTest);
        bindingTestButton.setOnClickListener(view -> {
            if(mBound) {
                int num = mService.getRandomNumber();
                Log.d(LOG_TAG, "number: " + num);
                Toast.makeText(this, "num is: " + num, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "mBound is false", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart()");
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        boolean bindingResult = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.d(LOG_TAG, "Binding result: " + bindingResult);
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop()");
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }
    private void setupActivityTitle(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Local Binding");
        }
    }

}