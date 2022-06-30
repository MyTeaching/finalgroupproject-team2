package com.example.team2finalprojectpokedex;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@IgnoreExtraProperties
public class Trainer {
    private String userID;
    private String firstName;
    private String lastName;
    private int age;
    private String trainerType;
    private String email;
    private List<Integer> pokedex;
    private @ServerTimestamp
    Date timestamp;

    public Trainer(){

    }

    public Trainer(String userID , String firstName,
                   String lastName, int age, String trainerType, String email,
                   List<Integer> pokedex, Date timestamp) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.trainerType = trainerType;
        this.email = email;
        this.pokedex = pokedex;
        this.timestamp = timestamp;
    }

    public void setPokedex(List<Integer> pokedex) {
        this.pokedex = pokedex;
    }

    public List<Integer> getPokedex() {
        return pokedex;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    @Override
    public String toString() {
        return "Trainer{" +
                "userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", trainerType='" + trainerType + '\'' +
                ", email='" + email + '\'' +
                ", pokedex=" + pokedex +
                ", timestamp=" + timestamp +
                '}';
    }
    // Should hopefully update trainer each time a new pokemon is made, keeping track of whether they are in their integer array
    public void updateTrainer(FirebaseUser currentUser, List<Pokemon> pokemons) {
        //TODO: FIX LOGIC OF MAKING NEW LIST
        List<Integer> updatedList = new ArrayList<>();
        Log.d("UPDATE", pokemons.toString());
        for(Pokemon pokemon: pokemons){
                updatedList.add(Integer.valueOf(pokemon.getId()));
        }
        setPokedex(updatedList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.update("pokedex", getPokedex()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("UPDATE", "PokeDex Updated in fireStore");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("UPDATE", "Failed to update pokedex");
            }
        });
    }
}
