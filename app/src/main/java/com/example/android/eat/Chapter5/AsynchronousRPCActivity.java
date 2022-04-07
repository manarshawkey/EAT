package com.example.android.eat.Chapter5;

/* Chapter 5: Asynchronous RPC subsection */
/*
* The strength of synchronous RPC lies in its simplicity, however,
* it comes at a cost, because the calling threads are blocked.
* This also applies to process-local calls, but often the
* execution of remote calls is done in code unknown to the client developer.
* The amount of time that calling threads spend blocked may differ
* as the remore implementation changes. Hence, synchronous remote
* calls may have unpredictable impacts the application responsiveness.
* Impacts on the UI thread are commonly avoided by executing all
* remote calls on worker threads, running asynchronously with the UI
* thread. However, if the server thread is blocked, the client thread
* will also block indeterminately, keeping the thread and all its
* object references alive. This risks memory leaks.
*/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.android.eat.R;

public class AsynchronousRPCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyncronous_rpcactivity);
    }
}