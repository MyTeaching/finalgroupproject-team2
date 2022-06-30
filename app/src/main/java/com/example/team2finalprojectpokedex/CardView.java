package com.example.team2finalprojectpokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Random;

public class CardView extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;
    ImageView pokemonView;
    Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_view);

        pokemonView = findViewById(R.id.pokemonId);
        Intent replyIntent = getIntent();
        Bundle bundle = replyIntent.getExtras();
        pokemonArrayList = new ArrayList<>();
        pokemonArrayList = bundle.getParcelableArrayList("POKEMONLIST");
        Log.d("CARDVIEW",bundle.getString("CURRPOKEMON"));
        Log.d("CARDVIEW",bundle.getParcelableArrayList("POKEMONLIST").toString());
        String currPokemon = bundle.getString("CURRPOKEMON");
        pokemon = retrievePokemonFromList(currPokemon);
        Log.d("CARDVIEW", "Pokemon is: " + pokemon.toString());
        Log.d("CARDVIEW", "POKEMON SPRITE: " + pokemon.getSpriteURL());
        String url = pokemon.getSpriteURL();
        Glide.with(CardView.this).load(url).override(128, 128).into(pokemonView);
        pokemonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pokemonArrayList.isEmpty()) {
                    Intent intent = new Intent(CardView.this, PokemonDetailsActivity.class);
                    intent.putExtra("POKEMON", pokemon);
                    startActivity(intent);
                }
            }
        });
    }

    public Pokemon retrievePokemonFromList(String name){
        Pokemon poke = new Pokemon();
        for(Pokemon pokemon : pokemonArrayList){
            if(pokemon.getName().equalsIgnoreCase(name)){
                poke = pokemon;
                return poke;
            }
        }
        return null;
    }
}