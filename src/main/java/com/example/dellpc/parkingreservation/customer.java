package com.example.dellpc.parkingreservation;

import java.io.Serializable;

public class customer implements Serializable{

    String first_name,last_name,gender,phone_number,email_id,password,confirm_password,license;
    int age,two_wheeler,four_wheeler;

    public String getFirst_name() {
        return first_name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTwo_wheeler() {
        return two_wheeler;
    }

    public void setTwo_wheeler(int two_wheeler) {
        this.two_wheeler = two_wheeler;
    }

    public int getFour_wheeler() {
        return four_wheeler;
    }

    public void setFour_wheeler(int four_wheeler) {
        this.four_wheeler = four_wheeler;
    }

}
