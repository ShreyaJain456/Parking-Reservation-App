package com.example.dellpc.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp1=getSharedPreferences("login_customer", Context.MODE_PRIVATE);
        SharedPreferences sp2=getSharedPreferences("login_client", Context.MODE_PRIVATE);

        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean iswifi= manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        try {
            if(is3g || iswifi)
            Thread.sleep(500);
            else {

                Toast.makeText(getApplicationContext(),"Internet or wifi connection required",Toast.LENGTH_LONG).show();
                Thread.sleep(500);
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(sp1.contains("username") && sp1.contains("password")) {
            if (sp1.contains("client_uid")) {
                Intent in = new Intent(splashActivity.this, Parking_scenario_customer.class);
                in.putExtra("customer_uid", sp1.getString("uid", ""));
                in.putExtra("client_uid", sp1.getString("client_uid", ""));
                startActivity(in);
                finish();
            } else {
                Intent intent = new Intent(splashActivity.this, MapsActivity.class);
                intent.putExtra("customer", sp1.getString("uid", ""));
                startActivity(intent);
                finish();
            }
        }
        else if(sp2.contains("username") && sp2.contains("password"))
        {
                Intent intent=new Intent(splashActivity.this,Login_client.class);
                intent.putExtra("uid",sp2.getString("uid",""));
                startActivity(intent);
                finish();

        }

        else {
            startActivity(new Intent(splashActivity.this, Mainpage.class));
            finish();
        }
    }
}