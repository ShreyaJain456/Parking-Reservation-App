package com.example.dellpc.parkingreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Register_client extends AppCompatActivity {

    String first_name,last_name,office_no,mobile_no,email,name,category,pass,confirm_pass;
    EditText first_name_edit,last_name_edit,office_no_edit,mobile_no_edit,email_edit,name_edit,pass_edit,confirm_pass_edit;
    Spinner category_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        first_name_edit=(EditText)findViewById(R.id.First_Name_client);
        last_name_edit=(EditText)findViewById(R.id.Last_Name_client);
        office_no_edit=(EditText)findViewById(R.id.OfficeTelephoneNumber_client);
        mobile_no_edit=(EditText)findViewById(R.id.Mobile_Number_client);
        email_edit=(EditText)findViewById(R.id.EmailID_client);
        name_edit=(EditText)findViewById(R.id.Org_Name_client);
        category_edit=(Spinner)findViewById(R.id.category_client);
        pass_edit=(EditText)findViewById(R.id.Password_client);
        confirm_pass_edit=(EditText)findViewById(R.id.Confirm_Password_client);
        Button next=(Button)findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        initialize();
        if(validate()) {
            client Client=new client();
            Client.setFirst_name(first_name);
            Client.setLast_name(last_name);
            Client.setOffice_no(office_no);
            Client.setMobile_no(mobile_no);
            Client.setEmail(email);
            Client.setName(name);
            Client.setCategory(category);
            Client.setPassword(pass);

            Intent in=new Intent(Register_client.this,Register_client2.class);
            in.putExtra("client",Client);
            startActivity(in);
        }
    }

    private void initialize() {

        first_name=first_name_edit.getText().toString().trim();
        last_name=last_name_edit.getText().toString().trim();
        office_no=office_no_edit.getText().toString().trim();
        mobile_no=mobile_no_edit.getText().toString().trim();
        email=email_edit.getText().toString().trim();
        email+="@client.com";
        name=name_edit.getText().toString().trim();
        category=category_edit.getSelectedItem().toString().trim();
        pass=pass_edit.getText().toString().trim();
        confirm_pass=confirm_pass_edit.getText().toString().trim();
    }

    private boolean validate() {
        boolean valid=true;
        if(first_name.isEmpty() || first_name.length()>32) {
            first_name_edit.setError("Enter First name");
            valid = false;
        }
        if(last_name.isEmpty() || last_name.length()>32) {
            last_name_edit.setError("Enter Last name");
            valid = false;
        }
        if(office_no.isEmpty())
        {
            office_no_edit.setError("Enter phone number.");
            valid = false;
        }
        if(!Patterns.PHONE.matcher(office_no).matches() || office_no.length()!=10)
        {
            office_no_edit.setError("Enter valid phone number");
            valid=false;
        }

        if(mobile_no.isEmpty())
        {
            mobile_no_edit.setError("Enter mobile number.");
            valid = false;
        }
        if(!Patterns.PHONE.matcher(mobile_no).matches() || mobile_no.length()!=10)
        {
            mobile_no_edit.setError("Enter valid phone number");
            valid=false;
        }
        if(email.isEmpty())
        {
            email_edit.setError("Enter Email-ID");
            valid=false;
        }
        if(pass.isEmpty())
        {
            pass_edit.setError("Enter password");
            valid=false;
        }
        if(pass.length()<6)
        {
            pass_edit.setError("Too short password");
            valid=false;
        }
        if(!Pattern.matches(pass,confirm_pass) || confirm_pass.isEmpty())
        {
            confirm_pass_edit.setError("Please confirm your password correctly.");
            valid=false;
        }
        if(name.isEmpty() || name.length()>32) {
            name_edit.setError("Enter First name");
            valid = false;
        }
        return valid;
    }
}