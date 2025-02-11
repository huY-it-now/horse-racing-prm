package com.theanimegroup.horse_racing_client.entity;

public class User {
    private int id;
    private String username;
    private String password;
    private double totalCash;

    public User(int id, String username, String password, double totalCash) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.totalCash = totalCash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }
}