package com.beazle.pursuitvolley.Player;

public class Player {
    private String fullname;
    private String age;
    private String location;
    private String phoneNumber;
    private String email;

    public Player() {
        // bare constructor required for realtime database snapshot retrieval
    }

    public Player(String fullname,
                  String age,
                  String location,
                  String phoneNumber,
                  String email) {
        this.fullname = fullname;
        this.age = age;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String GetFullname() {
        return fullname;
    }

    public String GetAge() {
        return age;
    }

    public String GetLocation() {
        return location;
    }

    public String GetPhoneNumber() {
        return phoneNumber;
    }

    public String GetEmail() {
        return email;
    }

    public void SetFullname(String fullname) {
        this.fullname = fullname;
    }

    public void SetAge(String age) {
        this.age = age;
    }

    public void SetLocation(String location) {
        this.location = location;
    }

    public void SetPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void SetEmail(String email) {
        this.email = email;
    }
}
