package com.example.borjaweatherapp.models;

import org.json.JSONObject;

public class User {
    private String firstName;
    private String lastName;
    private String userId;
    private String MPin;

    public User(JSONObject object) {
        firstName = object.optString("firstName");
        lastName = object.optString("lastName");
        userId = object.optString("userId");
        MPin = object.optString("MPin");
    }

    public User(String firstName, String lastName, String userId, String MPin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.MPin = MPin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMPin() {
        return MPin;
    }

    public void setMPin(String MPin) {
        this.MPin = MPin;
    }
}
