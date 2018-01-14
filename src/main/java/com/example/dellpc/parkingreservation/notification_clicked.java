package com.example.dellpc.parkingreservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class notification_clicked extends AppCompatActivity {

    String customer_uid,client_uid,category;
    client Client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_clicked);

        customer_uid=getIntent().getStringExtra("customeruid");
        client_uid=getIntent().getStringExtra("clientuid");
        Client=(client)getIntent().getSerializableExtra("client");

        AlertDialog.Builder box = new AlertDialog.Builder(this);
        box.setTitle("Reservation Approval");
        box.setMessage("Approve reservation").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Client");
                Firebase mRootRef2=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");

                mRootRef.child(client_uid).child("customers").child(customer_uid).child("status").setValue("reserved");
                mRootRef2.child(customer_uid).child("Parking Reservation").child("status").setValue("reserved");

                mRootRef2.child(customer_uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Parking Reservation").child("category").getValue()!=null) {
                            category = dataSnapshot.child("Parking Reservation").child("category").getValue().toString();
                            if (category.equals("2-Wheeler")) {
                                Client.setTwo_reserve(Client.getTwo_reserve() + 1);
                                Client.setTwo_uncolor(Client.getTwo_uncolor() - 1);
                                mRootRef.child(client_uid).child("two_reserve").setValue(Client.getTwo_reserve());
                                mRootRef.child(client_uid).child("two_uncolor").setValue(Client.getTwo_uncolor());
                            } else {
                                Client.setFour_reserve(Client.getFour_reserve() + 1);
                                Client.setFour_uncolor(Client.getFour_uncolor() - 1);
                                mRootRef.child(client_uid).child("four_reserve").setValue(Client.getFour_reserve());
                                mRootRef.child(client_uid).child("four_uncolor").setValue(Client.getFour_uncolor());
                            }
                            Intent intent = new Intent(notification_clicked.this, Login_client.class);
                            intent.putExtra("client", Client);
                            intent.putExtra("uid", client_uid);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        box.create().show();
    }
}
