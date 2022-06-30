package com.example.finalproject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CardView extends AppCompatActivity {
    ArrayList<Pokemon> pokemonArrayList;
    ImageView pokemonView;
    int MAX_POKEDEX_ID = 899;
    Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.pokemon_view);
        Random random = new Random();
        int randomPokeId = random.nextInt(MAX_POKEDEX_ID);
        pokemonView = findViewById(R.id.pokemonViewId);
        pokemonArrayList = new ArrayList<>();

        // Setting border sizes
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels * 0.4);
        int height = (int) (displayMetrics.heightPixels * 0.25);
        getWindow().setLayout(width, height);

        new DownloadImageTask((ImageView) findViewById(R.id.pokemonId)).execute(String.format("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/%s.png", randomPokeId));

//         Create new pokemon
        pokemon = new Pokemon(randomPokeId);
        pokemonArrayList.add(pokemon);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap res) {
            imageView.setImageBitmap(res);
        }
    }
}
