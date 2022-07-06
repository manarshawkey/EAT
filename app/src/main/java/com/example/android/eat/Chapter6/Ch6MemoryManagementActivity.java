package com.example.android.eat.Chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class Ch6MemoryManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch6_memory_management);
        Utils.setActivityTitle(this, "Memory Management");
        setupPotentialMemoryLeakButton();
        setUpNoMemoryLeakWithAStaticInnerClassButton();
    }
    private void setUpNoMemoryLeakWithAStaticInnerClassButton(){
        Button button = findViewById(R.id.button_noMemLeakStaticInner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.displayDialog("This is a runnable instance executing as an anonymous" +
                                " inner class, separated from the execution environment" +
                                "(Thread). The Thread class is a static inner, it holds" +
                                " a reference to the outer class not the outer object so" +
                                " no expected memory leaks",
                        "No Memory Leak using a Static Inner Class",
                        Ch6MemoryManagementActivity.this);
                new OuterI().sampleMethod();
            }
        });
    }
    private void setupPotentialMemoryLeakButton(){
        Button button = findViewById(R.id.button_potentialMemoryLeak);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.displayDialog("On pressing this button, a long running thread in a non-static " +
                                "inner class is started. The inner class belongs to an outer class" +
                                " that has one of its members an array of a very large size " +
                                "this array is never marked as reclaimable by a GC root unless " +
                                "the thread in the inner class finishes execution even though " +
                                "this array is not referenced from the runnable.",
                        "Potential Memory Leak", Ch6MemoryManagementActivity.this);
                new Outer().sampleMethod();
            }
        });
    }
}