// IAsynchronous.aidl
package com.example.android.eat;

// Declare any non-default types here with import statements
import com.example.android.eat.IAsynchronousCallback;
interface IAsynchronous {

    oneway void getThreadName(IAsynchronousCallback callback);
}