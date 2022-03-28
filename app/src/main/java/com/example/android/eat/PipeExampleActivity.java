package com.example.android.eat;


/*
 * This example illustrates how pipes can process
 * text that a user enters in an EditText.
 * To keep the UI thread responsive, each character
 * entered by the user is passed to a worker thread,
 * which presumably handles some time consuming processing.
 * Here, we're only printing the char entered to the logs.
 */
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipeExampleActivity extends AppCompatActivity {

    private final static String LOG_TAG = PipeExampleActivity.class.getSimpleName();
    private EditText mEditText;
    private PipedReader mReader;
    private PipedWriter mWriter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_example);

        setupActivityTitle();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pipe Example");
        alertDialogBuilder.setMessage("This example illustrates how pipes can process\n" +
                " text that a user enters in an EditText." +
                " To keep the UI thread responsive, each character" +
                " entered by the user is passed to a worker thread," +
                " which presumably handles some time consuming processing." +
                " Here, we're only printing the char entered to the logs." +
                " Type a sequence of characters and watch the logs!");
        alertDialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> { });

        alertDialogBuilder.create().show();

        mEditText = findViewById(R.id.editText_pipeExample);
        mReader = new PipedReader();
        mWriter = new PipedWriter();
        try {
            mWriter.connect(mReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(before < count){
                    try {
                        Log.d(LOG_TAG, "writing");
                        mWriter.write(charSequence.subSequence(before, count).toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        TextHandlerTask textHandlerTask = new TextHandlerTask(mReader);
        textHandlerTask.start();
    }
    private static class TextHandlerTask extends Thread{
        private final PipedReader reader;
        TextHandlerTask(PipedReader reader){
            this.reader = reader;
        }

        @Override
        public void run() {
            try {

                while (Thread.currentThread().isInterrupted()) {
                    int result = reader.read();

                    while (result != -1) {
                        Log.d(LOG_TAG, "" + (char) result);
                        result = reader.read();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setupActivityTitle(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Pipe Example");
        }
    }
}