package com.beazle.pursuitvolley.Coach.CoachSelection;

import android.graphics.Bitmap;

public class Coach {

    private String uniqueId;
    private String fullname;
    private String age;
    private String locaton;
    private String bio;
    private Bitmap thumbnail;

    public Coach() {
        // Default constructor required for calls to DataSnapshot.getValue(Coach.class)
    }

    public Coach(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Coach(String uniqueId, String name, String age, String location, String bio) {
        this.uniqueId = uniqueId;
        this.fullname = name;
        this.age = age;
        this.locaton = location;
        this.bio = bio;
    }

    public String GetUniqueId() { return this.uniqueId; }

    public String GetName() {
        return this.fullname;
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

    public Bitmap GetThumbnail() {
        return thumbnail;
    }

    public void SetUniqueId(String id) { this.uniqueId = id; }

    public void SetName(String name) {
        this.fullname = name;
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

    public void SetThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
