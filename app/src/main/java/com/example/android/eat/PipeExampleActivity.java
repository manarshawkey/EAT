package com.example.android.eat;


/*
 * This example illustrates how pipes can process
 * text that a user enters in an EditText.
 * To keep the UI thread responsive, each character
 * entered by the user is passed to a worker thread,
 * which presumably handles some time consuming processing.
 * Here, we're only printing the char to the logs.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PipeExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_example);

    }
}