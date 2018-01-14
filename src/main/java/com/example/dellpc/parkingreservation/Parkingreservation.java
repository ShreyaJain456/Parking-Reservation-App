package com.example.dellpc.parkingreservation;

import android.app.Application;

import com.firebase.client.Firebase;

public class Parkingreservation extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
