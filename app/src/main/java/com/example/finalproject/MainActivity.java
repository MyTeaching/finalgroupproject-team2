package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ImageView avatar;
    EditText username;
    String user;
    Button btnTrainerBag, btnDrawCards, btnPokedex, onClickId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Connecting items
        widgetConnect();
        avatar = findViewById(R.id.playerAvatarId);
        avatar.setImageResource(R.mipmap.ic_launcher_default);
        username = findViewById(R.id.playerUsernameId);

        // Perform click on enter button
        username.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClickId.performClick();
                return true;
            }
            return false;
        });

        user = username.getText().toString();

        // Connecting buttons to activities
        btnTrainerBag.setOnClickListener(v -> {
            Intent trainerIntent = new Intent(this, ItemBag.class);
            trainerIntent.putExtra("USERNAME", user);
            startActivity(trainerIntent);
        });

        btnDrawCards.setOnClickListener(v -> {
            Intent drawCardsIntent = new Intent(this, DrawCards.class);
            startActivity(drawCardsIntent);
        });
    }

    private void widgetConnect() {
        btnTrainerBag = findViewById(R.id.btn_trainer_bag);
        btnDrawCards = findViewById(R.id.btn_draw_cards);
        btnPokedex = findViewById(R.id.btn_pokedex);
        onClickId = findViewById(R.id.onClickId);
    }
}
