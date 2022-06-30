package com.example.finalproject;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Random;

public class CardView extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;
    ImageView pokemonView;
    int MAX_POKEDEX_ID = 899;
    Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_view);
        Random random = new Random();
        pokemonView = findViewById(R.id.pokemonViewId);

        // Create new pokemon
        pokemon = new Pokemon(random.nextInt(MAX_POKEDEX_ID));
        Glide.with(this).load(pokemon.getSpriteURL()).into(pokemonView);
        pokemonArrayList.add(pokemon);
    }
}
