package com.example.wongki.modularization;

public class Main {
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
}
