package com.example.team2finalprojectpokedex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@IgnoreExtraProperties
public class Trainer {
    private String userID;
    private String trainerID;
    private String firstName;
    private String lastName;
    private int age;
    private String trainerType;
    private String email;
    private List<Pokemon> pokedex;
    private @ServerTimestamp Date timestamp;

    public Trainer(){
        this.pokedex = new ArrayList<Pokemon>();
    }

    public Trainer(String userID , String firstName,
                   String lastName, int age, String trainerType, String email,
                   List<Pokemon> pokedex, Date timestamp) {
        this.userID = userID;
//        this.trainerID = trainerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.trainerType = trainerType;
        this.email = email;
        this.pokedex = new ArrayList<Pokemon>();
        this.timestamp = timestamp;
    }


    public List<Pokemon> getPokedex() {
        return pokedex;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(String trainerID) {
        this.trainerID = trainerID;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(String trainerType) {
        this.trainerType = trainerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
