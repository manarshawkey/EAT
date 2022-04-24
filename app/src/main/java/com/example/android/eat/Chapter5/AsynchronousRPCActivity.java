package com.example.android.eat.Chapter5;

/* Chapter 5: Asynchronous RPC subsection */
/*
* The strength of synchronous RPC lies in its simplicity, however,
* it comes at a cost, because the calling threads are blocked.
* This also applies to process-local calls, but often the
* execution of remote calls is done in code unknown to the client developer.
* The amount of time that calling threads spend blocked may differ
* as the remote implementation changes. Hence, synchronous remote
* calls may have unpredictable impacts the application responsiveness.
* Impacts on the UI thread are commonly avoided by executing all
* remote calls on worker threads, running asynchronously with the UI
* thread. However, if the server thread is blocked, the client thread
* will also block indeterminately, keeping the thread and all its
* object references alive. This risks memory leaks.
*/

/*
* Asynchronous RPC as a solution:
* instead of letting the client implement its own asynchronous
* policy, every remote method call can be defined to execute asynchronously.
* The client thread initiates a transaction with asynchronous RPC and
* returns immediately. The binder gives the transaction to the server
* process and closes the connection from the client to the server.
* Asynchronous methods must return void, and must not have arguments
* declared out or inout. To retrieve results, the implementation will
* use a callback.
*/
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.example.android.eat.IAsynchronous;
import com.example.android.eat.IAsynchronousCallback;
import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class AsynchronousRPCActivity extends AppCompatActivity {

    private final String LOG_TAG = AsynchronousRPCActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyncronous_rpcactivity);

        Utils.setActivityTitle(this, "Asynchronous RPC");

        String msg = "This is a simple example of asynchronous RPC. " +
                "The remote interface is defined by one method that contains " +
                "a callback interface. When the remote interface method executes, " +
                "it gets the currently executing thread name -that is the remote thread- " +
                "and it passes it to the callback's 'handleResult' method. " +
                "This callback is a reverse RPC, that is a call from the server to the client. " +
                "Watch the log message for the output of the callback's 'handleResult' method, that " +
                "is the name of the remote thread on the server.";
        Utils.displayDialog( msg, "Asynchronous RPC", this);
        IAsynchronous.Stub server = new IAsynchronous.Stub() {
            @Override
            public void getThreadName(IAsynchronousCallback callback) throws RemoteException {
                String threadName = Thread.currentThread().getName();
                callback.handleResult(threadName);
            }
        };
        IAsynchronousCallback.Stub clientCallback = new IAsynchronousCallback.Stub() {
            @Override
            public void handleResult(String threadName) throws RemoteException {
                Log.d(LOG_TAG, threadName);
            }
        };
        try {
            server.getThreadName(clientCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}