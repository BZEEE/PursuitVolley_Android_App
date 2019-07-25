package com.beazle.pursuitvolley.Coach;

public class Coach {

    private String uniqueId;
    private String name;
    private String age;
    private String locaton;
    private String bio;
    private int thumbnail;

    public Coach(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Coach(String uniqueId, String name, String age, String location, String bio) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.age = age;
        this.locaton = location;
        this.bio = bio;
    }

    public String GetUniqueId() { return this.uniqueId; }

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

    public void SetUniqueId(String id) { this.uniqueId = id; }

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
