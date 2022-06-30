package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DrawCards extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;
    Button returnButton;
    ImageButton drawCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_cards);

        // Creating buttons
        returnButton = findViewById(R.id.returnButtonId);
        drawCardButton = findViewById(R.id.drawCardId);

        // Setting on creates
        returnButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent(DrawCards.this, ItemBag.class);
            startActivity(returnIntent);
        });

        drawCardButton.setOnClickListener(v -> {
            Intent cardIntent = new Intent(DrawCards.this, CardView.class);
            startActivity(cardIntent);
        });
    }
}
