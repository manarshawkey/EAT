// IAsynchronous.aidl
package com.example.android.eat;

// Declare any non-default types here with import statements
import com.example.android.eat.IAsynchronousCallback;
interface IAsynchronous {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void getThreadName(IAsynchronousCallback callback);

}