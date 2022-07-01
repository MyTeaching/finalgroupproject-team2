package com.example.team2finalprojectpokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ImageView avatar;
    Trainer trainer ;
    String user;
    TextView username;
    Context context;
    Button btnTrainerBag, btnDrawCards, btnPokedex, onClickId;

// TODO: find out where the pokedex is null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Intent intent = getIntent();
        Bundle trainerInfo = intent.getExtras();
        trainer = trainerInfo.getParcelable("TRAINER");
        Log.d("MainActivity", "Trainer in Main: " + trainer.getPokedex().toString());
        // Connecting items
        widgetConnect();
        avatar = findViewById(R.id.playerAvatarId);
        avatar.setImageResource(R.mipmap.ic_launcher_default);
        username = findViewById(R.id.playerUsernameId);

        // Perform click on enter button
//        username.setOnEditorActionListener((v, actionId, event) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                onClickId.performClick();
//                return true;
//            }
//            return false;
//        });
        username.setText(trainer.getTrainerType() + " " + trainer.getFirstName() );
        user = username.getText().toString();

        // Connecting buttons to activities
        btnTrainerBag.setOnClickListener(v -> {
            Intent trainerIntent = new Intent(this, ItemBag.class);
            Bundle trainerinfo= new Bundle();
            trainerinfo.putString("USERNAME", user);
            trainerinfo.putParcelable("TRAINER",trainer);
            trainerIntent.putExtras(trainerinfo);
            startActivity(trainerIntent);
        });

        btnDrawCards.setOnClickListener(v -> {
            Intent drawCardsIntent = new Intent(this, DrawCards.class);
            Bundle trainerinfo= new Bundle();
            trainerinfo.putString("USERNAME", user);
            trainerinfo.putParcelable("TRAINER",trainer);
            drawCardsIntent.putExtras(trainerinfo);
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
