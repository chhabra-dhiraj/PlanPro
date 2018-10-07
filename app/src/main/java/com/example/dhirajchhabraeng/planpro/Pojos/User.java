package com.example.dhirajchhabraeng.planpro.Pojos;

public class User {

    private String firstName, lastName, emailId, phoneNumber, password, location, profilePicture;

    public User(){

    }

    public User(String firstName, String lastName, String emailId, String phoneNumber, String password, String location, String profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.location = location;
        this.profilePicture = profilePicture;
    }

    public User(String firstName, String emailId, String phoneNumber, String profilePicture) {
        this.firstName = firstName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
