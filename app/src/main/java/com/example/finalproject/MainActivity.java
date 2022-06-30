package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    Button btnTrainerBag, btnDrawCards, btnPokedex;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        widgetConnect();
    }

    private void widgetConnect() {
        btnTrainerBag = findViewById(R.id.btn_trainer_bag);
        btnDrawCards = findViewById(R.id.btn_draw_cards);
        btnPokedex = findViewById(R.id.btn_pokedex);
    }

    public void goToTrainerBag(View view) {
        Intent intent = new Intent(this, ItemBag.class);
        startActivity(intent);
    }

    public void goToDrawCards(View view) {
        Intent intent = new Intent(this, DrawCards.class);
        startActivity(intent);
    }

    public void goToPokeDex(View view) {
        Intent intent = new Intent(this, Pokemon.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
