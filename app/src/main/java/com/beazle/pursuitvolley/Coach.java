package com.beazle.pursuitvolley;

public class Coach {

    private String name;
    private String age;
    private String locaton;
    private String bio;
    private int thumbnail;

    public Coach() {

    }

    public Coach(String name, String age, String location, String bio) {
        this.name = name;
        this.age = age;
        this.locaton = location;
        this.bio = bio;
    }

    public String GetName() {
        return this.name;
    }

    public String GetAge() {
        return this.age;
    }

    public String GetLocation() {
        return this.locaton;
    }

    public String GetBio() {
        return this.bio;
    }

    public int GetThumbnail() {
        return thumbnail;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public void SetAge(String age) {
        this.age = age;
    }

    public void SetLocaton(String locaton) {
        this.locaton = locaton;
    }

    public void SetBio(String bio) {
        this.bio = bio;
    }

    public void SetThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
