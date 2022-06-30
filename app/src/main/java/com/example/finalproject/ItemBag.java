package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ItemBag extends AppCompatActivity implements RecyclerViewInterface{
    Button drawCards;
    EditText searchBar;
    ImageView playerAvatar, pokeLogo;
    TextView playerUsername;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ItemBagAdapter itemBagAdapter;
    ArrayList<Pokemon> availablePokemon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_bag);
        getSupportActionBar().setTitle("");

        recyclerView = findViewById(R.id.pokemonViewId);
        itemBagAdapter = new ItemBagAdapter(this, availablePokemon, this);
        layoutManager = new GridLayoutManager(this, 2);
        searchBar = findViewById(R.id.pokemonSearchId);
        String pokemonSearchedName = searchBar.getText().toString();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}