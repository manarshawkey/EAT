package com.example.android.eat;
interface ISynchronous{
    String getThreadNameFast();
    String getThreadNameSlow(long sleep);
    String getThreadNameBlocking();
    String getThreadNameUnblock();
}