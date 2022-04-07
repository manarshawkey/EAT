// IAsynchronousCallback.aidl
package com.example.android.eat;

// Declare any non-default types here with import statements

interface IAsynchronousCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void handleResult(String threadName);
}