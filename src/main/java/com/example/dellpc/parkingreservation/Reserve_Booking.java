package com.example.dellpc.parkingreservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Reserve_Booking extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RadioGroup category;
    Button reserve_next;
    EditText vehicleno_edit,holdername_edit;
    String vehicleno,holdername,category_text;
    Firebase mRootRef;
    String customer_uid,place,client_uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve__booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        category=(RadioGroup)findViewById(R.id.category);
        vehicleno_edit=(EditText)findViewById(R.id.vehiclenumber);
        holdername_edit=(EditText)findViewById(R.id.holderName);
        reserve_next=(Button)findViewById(R.id.reserve_next);
        customer_uid=getIntent().getStringExtra("customer");
        client_uid=getIntent().getStringExtra("client_uid");
        place=getIntent().getStringExtra("place");
        reserve_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register(view);

            }
        });
    }

    private void register(View view) {
        initialize();
        if (validate()) {
            mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
            mRootRef.child(customer_uid).child("Parking Reservation").child("category").setValue(category_text);
            mRootRef.child(customer_uid).child("Parking Reservation").child("Vehicle Number").setValue(vehicleno);
            mRootRef.child(customer_uid).child("Parking Reservation").child("Place").setValue(place);
            mRootRef.child(customer_uid).child("Parking Reservation").child("Holder Name").setValue(holdername);

            mRootRef.child(customer_uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Parking Reservation")!=null)
                    {
                        Intent intent=new Intent(Reserve_Booking.this,Reserve_Booking2.class);
                        intent.putExtra("customer_uid",customer_uid);
                        intent.putExtra("client_uid",client_uid);
                        intent.putExtra("place",place);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }

    }
    private void initialize() {

        vehicleno=vehicleno_edit.getText().toString().trim();
        holdername=holdername_edit.getText().toString().trim();
    }

    private boolean validate() {
        boolean valid=true;
        if(category.getCheckedRadioButtonId()==-1) {
            Toast.makeText(this,"Please choose appropriate category of vehicle",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else
        {
            int id=category.getCheckedRadioButtonId();
            RadioButton gender_button=(RadioButton)findViewById(id);
            category_text=gender_button.getText().toString();
        }
        if(vehicleno.isEmpty()) {
            vehicleno_edit.setError("Enter vehicle number");
            valid = false;
        }
        if(holdername.isEmpty() || holdername.length()>32) {
            holdername_edit.setError("Enter vehicle holder name");
            valid = false;
        }
        return valid;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reserve__booking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.res_parking) {
            startActivity(new Intent(Reserve_Booking.this,MapsActivity.class));
        } else if (id == R.id.about) {
            Intent intent=new Intent(Reserve_Booking.this,Parking_scenario_customer.class);
            intent.putExtra("customer_uid",customer_uid);
            intent.putExtra("client_uid",client_uid);

            startActivity(intent);
        } else if (id == R.id.logOut) {

            SharedPreferences pref=getSharedPreferences("login_customer", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=pref.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(Reserve_Booking.this,Mainpage.class));
        } else if (id == R.id.nav_share) {

            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_SUBJECT,"Parking to JAVA link");
            i.putExtra(Intent.EXTRA_TEXT,"Parking to java app link");

            try
            {
                startActivity(Intent.createChooser(i,"Choose"));
            }catch(android.content.ActivityNotFoundException ex)
            {}
        } else if (id == R.id.nav_send) {
            Intent intent= new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","feedback@gmail.com",null));
            intent.putExtra(Intent.EXTRA_SUBJECT,"Parking Reservation app feedback");

            try
            {
                startActivity(Intent.createChooser(intent,"Send mail..."));
            }catch(android.content.ActivityNotFoundException ex)
            {}
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
