package com.example.dellpc.parkingreservation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public class Timer extends Service {


    String text;
    @Override
    public void onCreate() {
        new CountDownTimer(2 * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                text = "" + String.format( "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours
                                (millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds
                        (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                SharedPreferences pref=getSharedPreferences("login_customer", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.putString("timer",text);
                editor.putLong("current_time",millisUntilFinished);
                editor.commit();
            }

            public void onFinish() {
                SharedPreferences pref=getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.remove("timer");
                editor.apply();
            }
        }.start();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
