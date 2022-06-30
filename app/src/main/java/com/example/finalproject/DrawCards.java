package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DrawCards extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;

//    public DrawCards(ArrayList<Pokemon> arrayList) {
//        this.pokemonArrayList = arrayList;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_bag);
    }
}
