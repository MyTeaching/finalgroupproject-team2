package com.mastercoding.gpfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    Button btnTrainerBag, btnDrawCards, btnPokedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        widgetConnect();
    }
    
    private void widgetConnect() {
        btnTrainerBag = findViewById(R.id.btn_trainer_bag);
        btnDrawCards = findViewById(R.id.btn_draw_cards);
        btnPokedex = findViewById(R.id.btn_pokedex);
    }
}