package com.example.a10_26_2019_loginpage.manager;

public class Balance {
    int door_number;
    String name;
    double balance_amount;
    int id;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoor_number() {
        return door_number;
    }

    public void setDoor_number(int door_number) {
        this.door_number = door_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(double balance_amount) {
        this.balance_amount = balance_amount;
    }


}
