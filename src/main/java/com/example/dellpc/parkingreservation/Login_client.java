package com.example.dellpc.parkingreservation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Login_client extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int i,j;    // i tells position of 2 wheeler and j tells position of 4 wheeler
    ImageView iv;
    client Client=null;
    String uid;
    int count=0,count_4=0;
    View prompt;
    boolean bool=false;
    GridLayout two_wheeler,four_wheeler;
    Button request;
    String customer_name;
    PendingIntent pIntent;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        request = (Button) findViewById(R.id.request);
        two_wheeler = (GridLayout) findViewById(R.id.two);
        four_wheeler = (GridLayout) findViewById(R.id.four);

        uid = getIntent().getStringExtra("uid");

            Firebase mRootRef;
            mRootRef = new Firebase("https://parking-reservation-456.firebaseio.com/Client");
            mRootRef.child(uid).addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                    Object array[] = new Object[21];
                    int i = 0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        array[i] = data.getValue();
                        i++;
                    }
                    Client = new client();
                    if (i == 20) {
                        Client.setCategory(array[0].toString());
                        Client.setEmail(array[1].toString());
                        Client.setFirst_name(array[2].toString());
                        Client.setFour_color(Integer.parseInt(array[3].toString()));
                        Client.setFour_reserve(Integer.parseInt(array[4].toString()));
                        Client.setFour_uncolor(Integer.parseInt(array[5].toString()));
                        Client.setLast_name(array[6].toString());
                        Client.setMobile_no(array[7].toString());
                        Client.setName(array[8].toString());
                        Client.setNo_2wheeler(Integer.parseInt(array[9].toString()));
                        Client.setNo_4wheeler(Integer.parseInt(array[10].toString()));
                        Client.setNo_staff(Integer.parseInt(array[11].toString()));
                        Client.setOffice_no(array[12].toString());
                        Client.setParking_charges_2((Integer.parseInt(array[13].toString())));
                        Client.setParking_charges_4(Integer.parseInt(array[14].toString()));
                        Client.setPassword(array[15].toString());
                        Client.setTwo_color(Integer.parseInt(array[16].toString()));
                        Client.setTwo_reserve(Integer.parseInt(array[17].toString()));
                        Client.setTwo_uncolor(Integer.parseInt(array[18].toString()));
                        Client.setWidth_of_road(Integer.parseInt(array[19].toString()));
                    } else if (i == 21) {
                        Client.setCategory(array[0].toString());
                        Client.setEmail(array[2].toString());
                        Client.setFirst_name(array[3].toString());
                        Client.setFour_color(Integer.parseInt(array[4].toString()));
                        Client.setFour_reserve(Integer.parseInt(array[5].toString()));
                        Client.setFour_uncolor(Integer.parseInt(array[6].toString()));
                        Client.setLast_name(array[7].toString());
                        Client.setMobile_no(array[8].toString());
                        Client.setName(array[9].toString());
                        Client.setNo_2wheeler(Integer.parseInt(array[10].toString()));
                        Client.setNo_4wheeler(Integer.parseInt(array[11].toString()));
                        Client.setNo_staff(Integer.parseInt(array[12].toString()));
                        Client.setOffice_no(array[13].toString());
                        Client.setParking_charges_2((Integer.parseInt(array[14].toString())));
                        Client.setParking_charges_4(Integer.parseInt(array[15].toString()));
                        Client.setPassword(array[16].toString());
                        Client.setTwo_color(Integer.parseInt(array[17].toString()));
                        Client.setTwo_reserve(Integer.parseInt(array[18].toString()));
                        Client.setTwo_uncolor(Integer.parseInt(array[19].toString()));
                        Client.setWidth_of_road(Integer.parseInt(array[20].toString()));
                    }

                    int two_uncolor=Client.two_uncolor,four_uncolor=Client.four_uncolor,two_color=Client.two_color,four_color=Client.four_color;
                    int four_reserve=Client.four_reserve,two_reserve=Client.two_reserve;
                    Button two_wheeler_clicked=(Button)findViewById(R.id.two_wheeler_clicked);
                    Button two_wheeler_unclicked=(Button)findViewById(R.id.two_wheeler_unclicked);
                    Button four_wheeler_clicked=(Button)findViewById(R.id.four_wheeler_clicked);
                    Button four_wheeler_unclicked=(Button)findViewById(R.id.four_wheeler_unclicked);

                    two_wheeler_clicked.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(Client.two_uncolor>0 || Client.two_reserve>0)
                            {
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login_client.this);
                                alertDialog.setTitle("Category");
                                alertDialog.setMessage("Choose your category");
                                alertDialog.setPositiveButton("Through app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        LayoutInflater li=LayoutInflater.from(Login_client.this);
                                        final View prompt=li.inflate(R.layout.prompt,null);

                                        AlertDialog.Builder alert=new AlertDialog.Builder(Login_client.this);
                                        alert.setView(prompt);

                                        alert.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog,int id)
                                            {

                                                EditText input=(EditText)prompt.findViewById(R.id.inputtext);
                                                final String unique_no= input.getText().toString().trim();
                                                final Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");

                                                final Firebase mRootRef2=new Firebase("https://parking-reservation-456.firebaseio.com");
                                                mRootRef2.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.getKey().equals("Total customers"))
                                                        {
                                                            count=Integer.parseInt(dataSnapshot.getValue().toString());
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {

                                                    }
                                                });
                                                mRootRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for(DataSnapshot data:dataSnapshot.getChildren())
                                                        {
                                                            if(data.child("Parking Reservation").child("Vehicle Number").getValue()!=null && data.child("Parking Reservation").child("Vehicle Number").getValue().toString().equals(unique_no))
                                                            {
                                                                if(Client.two_reserve>0)
                                                                {
                                                                    data.getRef().child("Parking Reservation").child("status").setValue("parked");
                                                                    Client.two_reserve--;
                                                                    Client.two_color++;
                                                                    update_client();
                                                                    Intent intent = new Intent(Login_client.this, Login_client.class);
                                                                    intent.putExtra("uid",uid);
                                                                    count++;
                                                                    mRootRef2.child("Total customers").setValue(count);
                                                                    startActivity(intent);
                                                                    break;
                                                                }

                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {

                                                    }
                                                });
                                            }
                                        });
                                        AlertDialog box=alert.create();
                                        box.show();
                                    }
                                });
                                alertDialog.setNegativeButton("Manual", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Client.two_uncolor--;
                                        Client.two_color++;
                                        update_client();
                                        Intent intent=new Intent(Login_client.this,Login_client.class);
                                        intent.putExtra("uid",uid);
                                        startActivity(intent);
                                    }
                                });
                                alertDialog.show();
                            }
                            else
                                Toast.makeText(Login_client.this,"Sorry no more parking available",Toast.LENGTH_LONG).show();

                        }
                    });
                    two_wheeler_unclicked.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Client.two_color>0)
                            {
                                Client.two_uncolor++;
                                Client.two_color--;
                                update_client();
                            }
                            else
                                Toast.makeText(Login_client.this,"Sorry no more parking is occupied.",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Login_client.this,Login_client.class);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                        }
                    });
                    four_wheeler_clicked.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Client.four_uncolor>0 || Client.four_reserve>0)
                            {
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login_client.this);
                                alertDialog.setTitle("Category");
                                alertDialog.setMessage("Choose your category");
                                alertDialog.setPositiveButton("Through app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        LayoutInflater li=LayoutInflater.from(Login_client.this);
                                        prompt=li.inflate(R.layout.prompt,null);

                                        AlertDialog.Builder alert=new AlertDialog.Builder(Login_client.this);
                                        alert.setView(prompt);
                                        alert.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog,int id)
                                            {

                                                EditText input=(EditText)prompt.findViewById(R.id.inputtext);
                                                final String unique_no= input.getText().toString().trim();
                                                Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");

                                                final Firebase mRootRef2=new Firebase("https://parking-reservation-456.firebaseio.com");
                                                mRootRef2.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.child("Total customers") != null)
                                                            count_4 = Integer.parseInt(dataSnapshot.child("Total customers").getValue().toString());
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {

                                                    }
                                                });

                                                mRootRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for(DataSnapshot data:dataSnapshot.getChildren())
                                                        {
                                                            if(data.child("Parking Reservation").child("Vehicle Number").getValue()!=null && data.child("Parking Reservation").child("Vehicle Number").getValue().toString().equals(unique_no))
                                                            {
                                                                if(Client.four_reserve>0) {

                                                                    data.getRef().child("Parking Reservation").child("status").setValue("parked");
                                                                    Client.four_reserve--;
                                                                    Client.four_color++;
                                                                    update_client();
                                                                    count_4++;
                                                                    mRootRef2.child("Total customers").setValue(count_4);
                                                                    Intent intent = new Intent(Login_client.this, Login_client.class);
                                                                    intent.putExtra("uid", uid);
                                                                    startActivity(intent);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {

                                                    }
                                                });
                                            }
                                        });
                                        AlertDialog box=alert.create();
                                        box.show();
                                    }
                                });
                                alertDialog.setNegativeButton("Manual", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Client.four_uncolor--;
                                        Client.four_color++;
                                        update_client();
                                        Intent intent=new Intent(Login_client.this,Login_client.class);
                                        intent.putExtra("uid",uid);
                                        startActivity(intent);
                                    }
                                });
                                alertDialog.show();
                            }
                            else
                                Toast.makeText(Login_client.this,"Sorry no more parking available.",Toast.LENGTH_LONG).show();

                        }
                    });
                    four_wheeler_unclicked.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Client.four_color>0)
                            {
                                Client.four_uncolor++;
                                Client.four_color--;
                                update_client();
                            }
                            else
                                Toast.makeText(Login_client.this,"Sorry no more parking is occupied.",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(Login_client.this,Login_client.class);
                            intent.putExtra("uid",uid);
                            startActivity(intent);
                        }
                    });
                    for(i=0;i<two_color;i++)
                    {
                        ImageView iv=new ImageView(Login_client.this);
                        iv.setImageDrawable(getDrawable(R.mipmap.twowheelerclicked));
                        two_wheeler.addView(iv);
                    }
                    for(i=0;i<two_reserve;i++)
                    {
                        ImageView iv=new ImageView(Login_client.this);
                        iv.setImageDrawable(getDrawable(R.mipmap.twowhheelerreserved));
                        two_wheeler.addView(iv);
                    }
                    for(i=0;i<two_uncolor;i++)
                    {
                        ImageView iv=new ImageView(Login_client.this);
                        iv.setImageDrawable(getDrawable(R.mipmap.twowheelerunclicked));
                        two_wheeler.addView(iv);
                    }

                    for(j=0;j<four_color;j++)
                    {
                        ImageView iv=new ImageView(Login_client.this);
                        iv.setImageDrawable(getDrawable(R.mipmap.fourwheelerclicked));
                        four_wheeler.addView(iv);
                    }
                    for(j=0;j<four_reserve;j++)
                    {
                        ImageView iv=new ImageView(Login_client.this);
                        iv.setImageDrawable(getDrawable(R.mipmap.fourwheelerreserved));
                        four_wheeler.addView(iv);
                    }
                    for(j=0;j<four_uncolor;j++)
                    {
                        ImageView iv=new ImageView(Login_client.this);
                        iv.setImageDrawable(getDrawable(R.mipmap.fourwheelerunclicked));
                        four_wheeler.addView(iv);
                    }

                    request.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checknotification();
                        }
                    });

                }
                private void checknotification()
                {
                    Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Client");

                    mRootRef.child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if (data.getKey().equals("customers")) {
                                    for(DataSnapshot data2:data.getChildren()) {
                                        String value=data2.child("status").getValue().toString();
                                        if (value != null && value.equals("pending")) {
                                            Intent intent = new Intent(Login_client.this,notification_clicked.class);
                                            intent.putExtra("customeruid",data2.getKey());
                                            intent.putExtra("clientuid",uid);
                                            intent.putExtra("client",Client);
                                            pIntent = PendingIntent.getActivity(Login_client.this,(int)System.currentTimeMillis(),intent,0);

                                            Firebase mRootRef2=new Firebase("https://parking-reservation-456.firebaseio.com/Customers");
                                            mRootRef2.child(data2.getKey()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    customer_name=dataSnapshot.child("first_name").getValue().toString()+" ";
                                                    customer_name+=dataSnapshot.child("last_name").getValue().toString();
                                                    Notification n = new Notification.Builder(Login_client.this).setContentTitle("Reservation Request").setContentText("Customer Name : "+customer_name).setTicker("Notifications!").setSmallIcon(R.mipmap.logo).setContentIntent(pIntent).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND).build();
                                                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                    manager.notify(0, n);

                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {

                                                }
                                            });
                                            break;

                                        }
                                    }
                                }
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



    }
    private void update_client()
    {
        Firebase mRootRef=new Firebase("https://parking-reservation-456.firebaseio.com/Client");
        mRootRef.child(uid).child("four_color").setValue(Client.four_color);
        mRootRef.child(uid).child("two_color").setValue(Client.two_color);
        mRootRef.child(uid).child("four_uncolor").setValue(Client.four_uncolor);
        mRootRef.child(uid).child("two_uncolor").setValue(Client.two_uncolor);
        mRootRef.child(uid).child("four_reserve").setValue(Client.four_reserve);
        mRootRef.child(uid).child("two_reserve").setValue(Client.two_reserve);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.login_client, menu);
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

        if (id == R.id.about_client) {

        } else if (id == R.id.logOut_client) {

            SharedPreferences pref=getSharedPreferences("login_client", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=pref.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(Login_client.this,Mainpage.class));
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
