package com.eco.ecommerceapp.Model;

public class Users {

    private String Name,Phone,password;


    public Users(){}

    public Users(String name, String phone, String password) {
        Name = name;
        Phone = phone;
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
