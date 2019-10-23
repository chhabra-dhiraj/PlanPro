package com.example.dhirajchhabraeng.planpro.Pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String firstName, middleName, lastName, emailId, phoneNumber, pin, location, profilePicture;

    public User(){

    }

    public User(String firstName, String middleName, String lastName, String emailId, String phoneNumber, String pin, String location, String profilePicture) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
//        this.pin = pin;
        this.location = location;
        this.profilePicture = profilePicture;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        emailId = in.readString();
        phoneNumber = in.readString();
//        pin = in.readString();
        location = in.readString();
        profilePicture = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

//    public String getPin() {
////        return pin;
//    }

//    public void setPin(String pin) {
////        this.pin = pin;
//    }

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


    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(middleName);
        dest.writeString(lastName);
        dest.writeString(emailId);
        dest.writeString(phoneNumber);
//        dest.writeString(pin);
        dest.writeString(location);
        dest.writeString(profilePicture);
    }
}
