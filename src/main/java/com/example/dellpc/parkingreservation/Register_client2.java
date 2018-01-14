package com.example.dellpc.parkingreservation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.ClientInfoStatus;
import java.util.regex.Pattern;

public class Register_client2 extends AppCompatActivity {

    client Client;
    int no_staff,no_4wheeler,no_2wheeler,width_of_road,parking_charges_2,parking_charges_4,two_uncolor,two_color,four_uncolor,four_color,four_reserve,two_reserve;
    String tech_edu;
    EditText no_staff_edit,no_4wheeler_edit,no_2wheeler_edit,width_of_road_edit,parking_charges_2_edit,parking_charges_4_edit;
    RadioGroup tech_edu_button;
    private Firebase mRootRef;
    private FirebaseAuth mAuth;
    private final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client2);

        no_staff_edit=(EditText)findViewById(R.id.no_staff_client);
        no_4wheeler_edit=(EditText)findViewById(R.id.no_4wheeler_client);
        tech_edu_button=(RadioGroup)findViewById(R.id.tech_edu_client);
        no_2wheeler_edit=(EditText)findViewById(R.id.no_2wheeler_client);
        width_of_road_edit=(EditText)findViewById(R.id.width_road_client);
        parking_charges_2_edit=(EditText)findViewById(R.id.charge_2wheeler_client);
        parking_charges_4_edit=(EditText)findViewById(R.id.charge_4wheeler_client);
        tech_edu_button=(RadioGroup)findViewById(R.id.tech_edu_client);
        Button register=(Button)findViewById(R.id.register_client);

        Client = (client) getIntent().getSerializableExtra("client");
        mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Client");
        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
                startSignup();
            }
        });
    }
    private void register() {
        if (validate()) {
            Client.setNo_staff(no_staff);
            Client.setNo_4wheeler(no_4wheeler);
            Client.setNo_2wheeler(no_2wheeler);
            Client.setWidth_of_road(width_of_road);
            Client.setParking_charges_2(parking_charges_2);
            Client.setParking_charges_4(parking_charges_4);
            Client.setTwo_color(two_color);
            Client.setTwo_uncolor(two_uncolor);
            Client.setFour_color(four_color);
            Client.setFour_uncolor(four_uncolor);
            Client.setTwo_reserve(two_reserve);
            Client.setFour_reserve(four_reserve);
        }
    }

    private boolean validate() {
        boolean valid=true;
        if(no_staff_edit.getText().toString().isEmpty()) {
            no_staff_edit.setError("Enter total number of staff");
            valid = false;
        }
        else
        {
            no_staff=Integer.parseInt(no_staff_edit.getText().toString().trim());
        }
        if(no_2wheeler_edit.getText().toString().isEmpty()) {
            no_2wheeler_edit.setError("Enter total number of 2 wheeler parking space");
            valid = false;
        }
        else
        {
            no_2wheeler=Integer.parseInt(no_2wheeler_edit.getText().toString().trim());
            two_uncolor=no_2wheeler;
            two_color=0;
            two_reserve=0;
        }
        if(no_4wheeler_edit.getText().toString().isEmpty()) {
            no_4wheeler_edit.setError("Enter total number of 4 wheeler parking space");
            valid = false;
        }
        else
        {
            no_4wheeler=Integer.parseInt(no_4wheeler_edit.getText().toString().trim());
            four_uncolor=no_4wheeler;
            four_color=0;
            four_reserve=0;
        }
        if(width_of_road_edit.getText().toString().isEmpty()) {
            width_of_road_edit.setError("Enter total number of staff");
            valid = false;
        }
        else
        {
            width_of_road=Integer.parseInt(width_of_road_edit.getText().toString().trim());
        }
        if(parking_charges_2_edit.getText().toString().isEmpty()) {
            parking_charges_2_edit.setError("Enter total number of staff");
            valid = false;
        }
        else
        {
            parking_charges_2=Integer.parseInt(parking_charges_2_edit.getText().toString().trim());
        }
        if(parking_charges_4_edit.getText().toString().isEmpty()) {
            parking_charges_4_edit.setError("Enter total number of staff");
            valid = false;
        }
        else
        {
            parking_charges_4=Integer.parseInt(parking_charges_4_edit.getText().toString().trim());
        }
        if(tech_edu_button.getCheckedRadioButtonId()==-1) {
            Toast.makeText(this,"Please choose appropriate tech knowledge",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else
        {
            int id=tech_edu_button.getCheckedRadioButtonId();
            RadioButton tech_radio=(RadioButton)findViewById(id);
            tech_edu=tech_radio.getText().toString();
        }

        return valid;
    }
    private void startSignup() {
        if (!validate())
            return;
        mAuth.createUserWithEmailAndPassword(Client.getEmail(),Client.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                           Toast.makeText(context,"Login successfully",Toast.LENGTH_LONG).show();
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
                                    Intent in = new Intent(Register_client2.this,Register_client.class);
                                    startActivity(in);
                                }
                            });
                            box.create().show();
                            }

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
        mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Client");
        Firebase childRef=mRootRef.child(user.getUid());
        childRef.setValue(Client);
    }

    private void newActivity()
    {
        AlertDialog.Builder box = new AlertDialog.Builder(context);
        box.setTitle("Successfully registered.");
        box.setMessage("Please login to continue.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent in = new Intent(Register_client2.this,Login_Parking.class);
                startActivity(in);
            }
        });
        box.create().show();
    }
}
