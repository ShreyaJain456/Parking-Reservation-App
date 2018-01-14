package com.example.dellpc.parkingreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Register_customer extends AppCompatActivity {

    String first_name,last_name,gender,phone_number,email_id,password,confirm_password;
    int age;
    RadioGroup gender_radio;
    EditText first_name_edit,last_name_edit,phone_number_edit,email_id_edit,password_edit,confirm_pass_edit,age_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        first_name_edit=(EditText)findViewById(R.id.First_Name);
        last_name_edit=(EditText)findViewById(R.id.Last_Name);
        gender_radio=(RadioGroup)findViewById(R.id.gender);
        age_edit=(EditText)findViewById(R.id.Age);
        phone_number_edit=(EditText)findViewById(R.id.Phone_Number);
        email_id_edit=(EditText)findViewById(R.id.Email);
        password_edit=(EditText)findViewById(R.id.Password);
        confirm_pass_edit=(EditText)findViewById(R.id.Confirm_Password);
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
            customer Customer=new customer();
            Customer.setFirst_name(first_name);
            Customer.setLast_name(last_name);
            Customer.setGender(gender);
            Customer.setAge(age);
            Customer.setPhone_number(phone_number);
            Customer.setEmail_id(email_id);
            Customer.setPassword(password);

            Intent in=new Intent(Register_customer.this,Register_customer2.class);
            in.putExtra("Customer",Customer);
            startActivity(in);
        }


    }

    private void initialize() {

        first_name=first_name_edit.getText().toString().trim();
        last_name=last_name_edit.getText().toString().trim();
        phone_number=phone_number_edit.getText().toString().trim();
        email_id=email_id_edit.getText().toString().trim();
        password=password_edit.getText().toString().trim();
        confirm_password=confirm_pass_edit.getText().toString().trim();
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
        if(gender_radio.getCheckedRadioButtonId()==-1) {
            Toast.makeText(this,"Please choose gender",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else
        {
            int id=gender_radio.getCheckedRadioButtonId();
            RadioButton gender_button=(RadioButton)findViewById(id);
            gender=gender_button.getText().toString();
        }
        if(age_edit.getText().toString().isEmpty())
        {
            age_edit.setError("Enter age");
            valid=false;
        }
        else
        {
            age=Integer.parseInt(age_edit.getText().toString().trim());
            if(age<18)
            {
                age_edit.setError("You are too young to drive!");
                valid=false;
            }
        }
        if(phone_number.isEmpty())
        {
            phone_number_edit.setError("Enter phone number.");
            valid = false;
        }
        if(!Patterns.PHONE.matcher(phone_number).matches() || phone_number.length()!=10)
        {
            phone_number_edit.setError("Enter valid phone number");
            valid=false;
        }
        if(email_id.isEmpty())
        {
            email_id_edit.setError("Enter Email-ID");
            valid=false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_id).matches())
        {
            email_id_edit.setError("Enter valid email-ID");
            valid=false;
        }
        if(password.isEmpty())
        {
            password_edit.setError("Enter password");
            valid=false;
        }
        if(password.length()<6)
        {
            password_edit.setError("Too short password");
            valid=false;
        }
        if(!Pattern.matches(password,confirm_password) || confirm_password.isEmpty())
        {
            confirm_pass_edit.setError("Please confirm your password correctly.");
            valid=false;
        }
        return valid;
    }
}

