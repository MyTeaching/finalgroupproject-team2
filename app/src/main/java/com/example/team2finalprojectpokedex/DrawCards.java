package com.example.team2finalprojectpokedex;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class DrawCards extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;
    Button returnButton;
    ImageButton drawCardButton;
    PokemonRetriever pokemonRetriever;
    int MAX_POKEDEX_ID = 899;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_cards);
        pokemonRetriever = new PokemonRetriever(this);
        Random random = new Random();
        pokemonArrayList = new ArrayList<>();
        // Creating buttons
        returnButton = findViewById(R.id.returnButtonId);
        drawCardButton = findViewById(R.id.drawCardId);

        // Setting on creates
        returnButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent(DrawCards.this, ItemBag.class);
            startActivity(returnIntent);
        });

        drawCardButton.setOnClickListener(v -> {
            pokemonRetriever.getPokeName(String.valueOf(random.nextInt(MAX_POKEDEX_ID)), new PokemonRetriever.VolleyResponseListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(JSONObject response)  {
                    Pokemon poke = new Pokemon();
                    try {
                        pokemonRetriever.makePokemon(poke, response, pokemonArrayList, DrawCards.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            });

        });
    }


}