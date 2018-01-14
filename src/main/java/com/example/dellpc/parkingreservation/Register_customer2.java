package com.example.dellpc.parkingreservation;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Context;
import android.widget.Toast;

import com.firebase.client.Firebase;
//import com.firebase.client.core.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_customer2 extends AppCompatActivity {

    customer Customer;
    EditText license_edit;
    String license;
    int four_wheeler, two_wheeler;
    private Firebase mRootRef;
    private FirebaseAuth mAuth;
    private final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer2);

        Customer = (customer) getIntent().getSerializableExtra("Customer");

        final Spinner four_wheeler_spinner = (Spinner) findViewById(R.id.four_wheeler);
        four_wheeler = Integer.parseInt(four_wheeler_spinner.getSelectedItem().toString());
        Spinner two_wheeler_spinner = (Spinner) findViewById(R.id.two_wheeler);
        two_wheeler = Integer.parseInt(two_wheeler_spinner.getSelectedItem().toString());
        license_edit=(EditText)findViewById(R.id.License_no);

        Customer.setFour_wheeler(four_wheeler);
        Customer.setTwo_wheeler(two_wheeler);

        Button submit=(Button)findViewById(R.id.submit);

        mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
        mAuth=FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                license=license_edit.getText().toString().trim();
                if(license.isEmpty())
                {
                    license_edit.setError("Please enter license number");
                }
                else {
                    Customer.setLicense(license);
                    startSignup();
                }
            }

        });
    }

    private void startSignup()
    {
        mAuth.createUserWithEmailAndPassword(Customer.email_id, Customer.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateDatabase(user);
                            newActivity();
                        } else {
                            // If sign in fails, display a message to the user.

                            AlertDialog.Builder box = new AlertDialog.Builder(context);
                            box.setTitle("Registration failed");
                            box.setMessage("Already a user.").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent in = new Intent(Register_customer2.this,Register_customer.class);
                                    startActivity(in);
                                }
                            });
                            box.create().show();
                            /*Toast.makeText(Register_customer2.this, "Authentication failed."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        */}

                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    private void updateDatabase(FirebaseUser user)
    {
        mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
        Firebase childRef=mRootRef.child(user.getUid());
        childRef.setValue(Customer);
    }

    private void newActivity()
    {
        AlertDialog.Builder box = new AlertDialog.Builder(context);
        box.setTitle("Successfully registered.");
        box.setMessage("Please login to continue.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent in = new Intent(Register_customer2.this,Login_Parking.class);
                startActivity(in);
            }
        });
        box.create().show();


        /*Toast.makeText(Register_customer2.this, "Account is successfully created. Please login to continue!!",
                Toast.LENGTH_LONG).show();
    */}
}
