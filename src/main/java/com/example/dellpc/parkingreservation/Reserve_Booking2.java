package com.example.dellpc.parkingreservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

public class Reserve_Booking2 extends AppCompatActivity {

    String customer_uid, client_uid;
    Firebase mRootRef, mRootRef2;
    String vehicle_no, category, charges, charge, place;
    TextView vehicle_no_text, charges_text, place_text;
    Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve__booking2);

        vehicle_no_text = (TextView) findViewById(R.id.vehicle_no);
        charges_text = (TextView) findViewById(R.id.charges);
        place_text = (TextView) findViewById(R.id.place);
        reserve = (Button) findViewById(R.id.reservefinal);

        customer_uid = getIntent().getStringExtra("customer_uid");
        client_uid = getIntent().getStringExtra("client_uid");
        place = getIntent().getStringExtra("place");

        mRootRef = new Firebase("https://parking-reservation-456.firebaseio.com/Customers");

        mRootRef = mRootRef.child(customer_uid).child("Parking Reservation");

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals("Vehicle Number")) {
                        vehicle_no = data.getValue().toString();
                    }
                    if (data.getKey().equals("category")) {
                        category = data.getValue().toString();
                    }

                    if (category != null && vehicle_no != null) {
                        if (category.equals("2-Wheeler")) {
                            category = "two_uncolor";
                            charges = "parking_charges_2";
                        } else {
                            charges = "parking_charges_4";
                            category = "four_uncolor";
                        }
                        mRootRef2 = new Firebase("https://parking-reservation-456.firebaseio.com/Client");

                        mRootRef2 = mRootRef2.child(client_uid);

                        mRootRef2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if (data.getKey().equals(charges)) {
                                        charge = data.getValue().toString();
                                    }
                                    if (data.getKey().equals(category)) {
                                        if (Integer.parseInt(data.getValue().toString()) == 0) {
                                            AlertDialog.Builder box = new AlertDialog.Builder(Reserve_Booking2.this);
                                            box.setTitle("Reservation Status");
                                            box.setMessage("Sorry no space available").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent in = new Intent(Reserve_Booking2.this, MapsActivity.class);
                                                    startActivity(in);
                                                }
                                            });
                                            box.create().show();
                                        } else {

                                        }
                                    }
                                }
                                if (charge != null) {
                                    vehicle_no_text.setText("Vehicle Number : " + vehicle_no);
                                    charges_text.setText("Charges : " + charge);
                                    place_text.setText(place);
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRootRef = new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
                mRootRef.child(customer_uid).child("Parking Reservation").child("status").setValue("pending");
                mRootRef2.child("customers").child(customer_uid).child("status").setValue("pending");


                mRootRef.child(customer_uid).child("Parking Reservation").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("status").getValue() != null && dataSnapshot.child("status").getValue().toString().equals("pending") ) {
                            AlertDialog.Builder box = new AlertDialog.Builder(Reserve_Booking2.this);
                            box.setTitle("Reservation Status");
                            box.setMessage("Space is succesfully reserved.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent in = new Intent(Reserve_Booking2.this, Parking_scenario_customer.class);
                                    in.putExtra("customer_uid", customer_uid);
                                    in.putExtra("client_uid", client_uid);
                                    startActivity(in);

                                }
                            });
                            box.create().show();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }

                });

            }
        });
    }
}

