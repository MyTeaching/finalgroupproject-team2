package com.example.team2finalprojectpokedex;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class DrawCards extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;
    Button returnButton;
    ImageButton drawCardButton;
    PokemonRetriever pokemonRetriever;
    Trainer trainer;
    int MAX_POKEDEX_ID = 899;
    Context context;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_cards);
        context = this;
        pokemonRetriever = new PokemonRetriever(this);
        Random random = new Random();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        trainer = bundle.getParcelable("TRAINER");
        mAuth = FirebaseAuth.getInstance();
        pokemonArrayList = new ArrayList<>();
        setTrainerInfo(trainer, context);
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
                        pokemonRetriever.makePokemon(poke, response, pokemonArrayList, DrawCards.this, trainer, mAuth.getCurrentUser());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            });

        });
    }
    public void setTrainerInfo(Trainer pokeTrainer, Context context){
        if(pokeTrainer.getPokedex()!= null) {
            Log.d("DrawCard", "Current pokedex: "+pokeTrainer.getPokedex().toString());
            for (Integer i : pokeTrainer.getPokedex()) {
                Log.d("DrawCard", "Inside for loop Intger "+ i.toString());
                pokemonRetriever.getByID(i.intValue(), new PokemonRetriever.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Pokemon poke = new Pokemon();
                            pokemonRetriever.makePokemon("DrawCard", poke, response, pokemonArrayList);
                            Log.d("MainActivity", "Pokemon added: " + poke.getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        }
        Log.d("DrawCards", pokeTrainer.toString());
    }

}