package com.example.dellpc.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Parking_scenario_customer extends AppCompatActivity {

    String client_uid,customer_uid;
    public static final String format="%02d:%02d:%02d";
    TextView time,status;
    Button button;
    Boolean value=false;
    String block;
    int two_reserve,four_reserve,two_uncolor,four_uncolor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_scenario_customer);
        customer_uid=getIntent().getStringExtra("customer_uid");
        client_uid=getIntent().getStringExtra("client_uid");

        final TextView customer_name=(TextView)findViewById(R.id.customername);
        final TextView place_text=(TextView)findViewById(R.id.place);
        status=(TextView)findViewById(R.id.status);
        time=(TextView)findViewById(R.id.time);
        button=(Button)findViewById(R.id.Cancel);

        final Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
        final Firebase mRootRef2=new Firebase("https://parking-reservation-456.firebaseio.com/Client");
        mRootRef.child(customer_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Parking Reservation").child("status").getValue().toString()!=null && dataSnapshot.child("Parking Reservation").child("status").getValue().toString().equals("parked"))
                {
                    dataSnapshot.child("Parking Reservation").getRef().removeValue();
                    mRootRef2.child(client_uid).child("customers").child(customer_uid).removeValue();
                    SharedPreferences pref2 = getSharedPreferences("login_customer", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref2.edit();
                    editor.remove("timer");
                    editor.remove("client_uid");
                    editor.apply();
                    button.setText("Resume");
                    Intent in = new Intent(Parking_scenario_customer.this, MapsActivity.class);
                    in.putExtra("customer",customer_uid);
                    startActivity(in);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRootRef.child(customer_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Parking Reservation").child("status").getValue()!=null && dataSnapshot.child("Parking Reservation").child("status").getValue().toString().equals("reserved")) {
                    SharedPreferences pref = getSharedPreferences("login_customer", Context.MODE_PRIVATE);
                    startService(new Intent(Parking_scenario_customer.this, Timer.class));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    if (pref.contains("timer") && !time.equals("Time Left : 00:00:01") && !time.equals("Time Left : 00:00:00")) {
                        new CountDownTimer(pref.getLong("current_time", 0), 1000) {
                            public void onTick(long millisUntilFinished) {
                                time.setText("Time Left : " + String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours
                                                (millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds
                                                (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                            }

                            public void onFinish() {
                                update_record();
                            }
                        }.start();
                    } else {
                        update_record();
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRootRef.child(customer_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                customer_name.setText("Name : "+dataSnapshot.child("first_name").getValue()+" "+dataSnapshot.child("last_name").getValue());
                status.setText("Status : "+dataSnapshot.child("Parking Reservation").child("status").getValue());
                place_text.setText("Place : "+dataSnapshot.child("Parking Reservation").child("Place").getValue());

                if(dataSnapshot.child("Parking Reservation").child("status").equals("parked"))
                {
                    time.setText("Successfully parked");
                    button.setText("Resume");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dataSnapshot.child("Parking Reservation").getRef().removeValue();
                            Intent in = new Intent(Parking_scenario_customer.this, MapsActivity.class);
                            in.putExtra("customer",customer_uid);
                            startActivity(in);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Firebase mRootRef;
                mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
                SharedPreferences pref2 = getSharedPreferences("login_customer", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=pref2.edit();
                editor.remove("timer");
                editor.remove("client_uid");
                editor.apply();
                mRootRef.child(customer_uid).addValueEventListener(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                        mRootRef.child(customer_uid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child("Parking Reservation").child("status").getValue().equals("reserved")) {
                                    if(dataSnapshot.child("Parking Reservation").child("category").getValue().equals("2-Wheeler"))
                                    {
                                        mRootRef2.child(client_uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.getKey().equals("two_reserve"))
                                                {
                                                    two_reserve=Integer.parseInt(dataSnapshot.getValue().toString());
                                                }
                                                if(dataSnapshot.getKey().equals("two_uncolor"))
                                                {
                                                    two_uncolor=Integer.parseInt(dataSnapshot.getValue().toString());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                        });
                                        if(two_reserve!=0) {
                                            mRootRef2.child(client_uid).child("two_reserve").setValue(two_reserve-1);
                                            mRootRef2.child(client_uid).child("two_uncolor").setValue(two_uncolor+1);
                                        }
                                    }
                                    else if(dataSnapshot.child("Parking Reservation").child("category").getValue().equals("4-Wheeler"))
                                    {
                                        mRootRef2.child(client_uid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.getKey().equals("four_reserve"))
                                                {
                                                    four_reserve=Integer.parseInt(dataSnapshot.getValue().toString());
                                                }
                                                if(dataSnapshot.getKey().equals("four_uncolor"))
                                                {
                                                    four_uncolor=Integer.parseInt(dataSnapshot.getValue().toString());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                        });
                                        if(four_reserve!=0) {
                                            mRootRef2.child(client_uid).child("four_reserve").setValue(four_reserve-1);
                                            mRootRef2.child(client_uid).child("four_uncolor").setValue(four_uncolor+1);
                                        }
                                    }
                                    mRootRef.child(customer_uid).child("Parking Reservation").removeValue();
                                    mRootRef2.child(client_uid).child("customers").child(customer_uid).removeValue();
                                    Intent in = new Intent(Parking_scenario_customer.this, MapsActivity.class);
                                    in.putExtra("customer", customer_uid);
                                    startActivity(in);
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                Toast.makeText(Parking_scenario_customer.this,"Successfully removed",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Parking_scenario_customer.this,MapsActivity.class));

                }
        });

    }

    private void update_record()
    {

        final Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
        final Firebase mRootRef2=new Firebase("https://parking-reservation-456.firebaseio.com/Client");
        SharedPreferences pref2 = getSharedPreferences("login_customer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref2.edit();
        editor.remove("timer");
        editor.remove("client_uid");
        editor.apply();
        status.setText("Status : Cancelled");
        time.setText("Cancellation time is finished");
        button.setText("Resume");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRootRef.child(customer_uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("block").getValue()!=null)
                        block = dataSnapshot.child("block").getValue().toString();

                        Toast.makeText(getApplicationContext(),"1"+dataSnapshot.child("Parking Reservation").child("status").getValue(),Toast.LENGTH_LONG).show();
                        if (dataSnapshot.child("Parking Reservation").child("status").getValue().equals("reserved")) {
                            if(dataSnapshot.child("Parking Reservation").child("category").getValue().equals("2-Wheeler"))
                            {
                                Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                                mRootRef2.child(client_uid).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getKey().equals("two_reserve"))
                                        {
                                            two_reserve=Integer.parseInt(dataSnapshot.getValue().toString());
                                        }
                                        if(dataSnapshot.getKey().equals("two_uncolor"))
                                        {
                                            two_uncolor=Integer.parseInt(dataSnapshot.getValue().toString());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                                if(two_reserve!=0) {
                                    mRootRef2.child(client_uid).child("two_reserve").setValue(two_reserve-1);
                                    mRootRef2.child(client_uid).child("two_uncolor").setValue(two_uncolor+1);
                                }
                            }
                            else if(dataSnapshot.child("Parking Reservation").child("category").getValue().equals("4-Wheeler"))
                            {
                                mRootRef2.child(client_uid).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getKey().equals("four_reserve"))
                                        {
                                            four_reserve=Integer.parseInt(dataSnapshot.getValue().toString());
                                        }
                                        if(dataSnapshot.getKey().equals("four_uncolor"))
                                        {
                                            four_uncolor=Integer.parseInt(dataSnapshot.getValue().toString());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                                if(four_reserve!=0) {
                                    mRootRef2.child(client_uid).child("four_reserve").setValue(four_reserve-1);
                                    mRootRef2.child(client_uid).child("four_uncolor").setValue(four_uncolor+1);
                                }
                            }
                            if(block==null)
                                mRootRef.child(customer_uid).child("block").setValue("1");
                            else
                                mRootRef.child(customer_uid).child("block").setValue(Integer.parseInt(block) + 1);
                            mRootRef.child(customer_uid).child("Parking Reservation").removeValue();
                            mRootRef2.child(client_uid).child("customers").child(customer_uid).removeValue();
                            Intent in = new Intent(Parking_scenario_customer.this, MapsActivity.class);
                            in.putExtra("customer", customer_uid);
                            startActivity(in);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
    }


    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

}
