package com.example.team2finalprojectpokedex;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Objects;


public class ItemBag extends AppCompatActivity implements RecyclerViewInterface{
    Button drawCards;
    EditText searchBar;
    ImageView playerAvatar;
    TextView playerUsername;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ItemBagAdapter itemBagAdapter;
    ArrayList<Pokemon> availablePokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_bag);
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerView = findViewById(R.id.pokemonViewId);
        playerAvatar = findViewById(R.id.playerAvatarId);
        playerUsername = findViewById(R.id.usernameId);
        searchBar = findViewById(R.id.pokemonSearchId);
        drawCards = findViewById(R.id.drawButtonId);

        Intent mainActivity = getIntent();

        playerAvatar.setImageResource(R.mipmap.ic_launcher_default);
        playerUsername.setText(mainActivity.getStringExtra("USERNAME"));

        itemBagAdapter = new ItemBagAdapter(this, availablePokemon, this);
        layoutManager = new GridLayoutManager(this, 2);
        String pokemonSearchedName = searchBar.getText().toString();

        drawCards.setOnClickListener(v -> {
            Intent openCardViewer = new Intent(ItemBag.this, DrawCards.class);
            startActivity(openCardViewer);
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}