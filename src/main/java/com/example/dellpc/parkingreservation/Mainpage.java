package com.example.dellpc.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Mainpage extends AppCompatActivity {

    Spinner register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        Button login=(Button)findViewById(R.id.login);
        Button register_text=(Button)findViewById(R.id.register_text);
        register=(Spinner)findViewById(R.id.register);


        register_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register.performClick();
            }
        });

        register.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str=(String)register.getSelectedItem();
                if(str.equals("Customer"))
                {
                    startActivity(new Intent(Mainpage.this,Register_customer.class));
                }
                else if(str.equals("Client"))
                {
                    startActivity(new Intent(Mainpage.this,Register_client.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(Mainpage.this,Login_Parking.class));
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        SharedPreferences pref=getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.commit();
        System.exit(1);
    }
}
