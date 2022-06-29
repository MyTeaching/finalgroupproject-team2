package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalproject.databinding.ActivityPokemonDetailsBinding;

public class PokemonDetailsActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPokemonDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPokemonDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* TODO: Unhide the action bar and make it transparent. (Change the layout to make the tool
                 bar appear on top of the other layout. Relative layout instead of coordinate?)  */
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();
        Intent intent = getIntent();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pokemon_details);
        navController.setGraph(R.navigation.nav_graph, intent.getExtras());
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_pokemon_details);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}