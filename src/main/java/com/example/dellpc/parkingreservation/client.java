package com.example.dellpc.parkingreservation;

import java.io.Serializable;

public class client implements Serializable{
    String first_name,last_name,office_no,mobile_no,email,name,category,tech_edu,password,confirmpass;
    int no_staff,no_4wheeler,no_2wheeler,width_of_road,parking_charges_2,parking_charges_4,two_uncolor,two_color,four_uncolor,four_color,two_reserve,four_reserve;

    public int getTwo_reserve() {
        return two_reserve;
    }

    public void setTwo_reserve(int two_reserve) {
        this.two_reserve = two_reserve;
    }

    public int getFour_reserve() {
        return four_reserve;
    }

    public void setFour_reserve(int four_reserve) {
        this.four_reserve = four_reserve;
    }

    public int getTwo_uncolor() {
        return two_uncolor;
    }

    public void setTwo_uncolor(int two_uncolor) {
        this.two_uncolor = two_uncolor;
    }

    public int getTwo_color() {
        return two_color;
    }

    public void setTwo_color(int two_color) {
        this.two_color = two_color;
    }

    public int getFour_uncolor() {
        return four_uncolor;
    }

    public void setFour_uncolor(int four_uncolor) {
        this.four_uncolor = four_uncolor;
    }

    public int getFour_color() {
        return four_color;
    }

    public void setFour_color(int four_color) {
        this.four_color = four_color;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpass() {
        return confirmpass;
    }

    public void setConfirmpass(String confirmpass) {
        this.confirmpass = confirmpass;
    }

    public String getFirst_name() {
        return first_name;
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

    public String getOffice_no() {
        return office_no;
    }

    public void setOffice_no(String office_no) {
        this.office_no = office_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTech_edu() {
        return tech_edu;
    }

    public void setTech_edu(String tech_edu) {
        this.tech_edu = tech_edu;
    }

    public int getNo_staff() {
        return no_staff;
    }

    public void setNo_staff(int no_staff) {
        this.no_staff = no_staff;
    }

    public int getNo_4wheeler() {
        return no_4wheeler;
    }

    public void setNo_4wheeler(int no_4wheeler) {
        this.no_4wheeler = no_4wheeler;
    }

    public int getNo_2wheeler() {
        return no_2wheeler;
    }

    public void setNo_2wheeler(int no_2wheeler) {
        this.no_2wheeler = no_2wheeler;
    }

    public int getWidth_of_road() {
        return width_of_road;
    }

    public void setWidth_of_road(int width_of_road) {
        this.width_of_road = width_of_road;
    }

    public int getParking_charges_2() {
        return parking_charges_2;
    }

    public void setParking_charges_2(int parking_charges_2) {
        this.parking_charges_2 = parking_charges_2;
    }

    public int getParking_charges_4() {
        return parking_charges_4;
    }

    public void setParking_charges_4(int parking_charges_4) {
        this.parking_charges_4 = parking_charges_4;
    }
}
