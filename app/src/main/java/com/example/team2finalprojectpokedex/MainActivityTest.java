package com.example.team2finalprojectpokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivityTest extends AppCompatActivity {
    private static final int DONE = 1;
    private FirebaseAuth mAuth;
    TextView textView, tvUser, tvPass, userName, trainerType;
    EditText etDataInput, etUser, etPassword;
    ImageView trainer;
    View mParentLayout;
    PokemonRetriever pokemonRetriever;
    Button simpleRequestBtn, getPokemon, btCreate, btLogin;
//    String pokeQuery;
    List<Pokemon> pokemons;
    public static  final String TAG = "MainActivity";
    private Trainer pokeTrainer;

    /* TODO: connect the app to firebase to store favorites and
             trainer information. Create a list of favorties,
             list of owned, and list of "deck" pokemon
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main_test);
        mParentLayout = findViewById(android.R.id.content);
        pokemonRetriever = new PokemonRetriever(MainActivityTest.this);
        pokemons = new ArrayList<>();
        connectViews();
        simpleRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokemonRetriever.getPokeName(etDataInput.getText().toString().toLowerCase(), new PokemonRetriever.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivityTest.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Pokemon poke = new Pokemon();
                            pokemonRetriever.makePokemon(poke, response, pokemons, textView);
                            textView.setText(poke.getDescription());
                            List<Integer> updatedList = new ArrayList<>();
                            for(Pokemon pokemon: pokemons){
                                if(!pokeTrainer.getPokedex().contains(Integer.valueOf(pokemon.getId()))){
                                    updatedList.add(Integer.valueOf(pokemon.getId()));
                                }
                            }
                            pokeTrainer.setPokedex(updatedList);
                            updateTrainer();


                            Log.d("MainActivity", "Pokemon added: " + poke.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String pokeName) {
                        Toast.makeText(MainActivityTest.this, "Returned Pokemon Named: " + pokeName, Toast.LENGTH_SHORT).show();
                        textView.setText(pokeName );
                        Log.d(TAG, "Pokemon name from onClick(): " + pokeName);


                    }
                });

            }
        });
        getPokemon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!pokemons.isEmpty()) {
                    Intent intent = new Intent(MainActivityTest.this, PokemonDetailsActivity.class);
                    for (Pokemon poke : pokemons) {
                        if (poke.getName().toLowerCase().equals(etDataInput.getText().toString().toLowerCase())) {
                            intent.putExtra("POKEMON", pokemons.get(pokemons.indexOf(poke)));
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(etUser.getText().toString(), etPassword.getText().toString());
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    public void connectViews(){
        etDataInput = findViewById(R.id.et_dataInput);
        textView = (TextView) findViewById(R.id.textView);
        simpleRequestBtn = (Button) findViewById(R.id.button_test);
        getPokemon = (Button) findViewById(R.id.button_nextIntent);
        btCreate = findViewById(R.id.bt_create);
        btLogin  = findViewById(R.id.bt_log_in);
        etPassword = findViewById(R.id.et_user_password);
        etUser = findViewById(R.id.et_user_email);
        tvPass =  findViewById(R.id.tv_user_password);
        tvUser = findViewById(R.id.tv_user_email);
        trainerType = findViewById(R.id.tv_trainer_type);
        trainer = findViewById(R.id.iv_trainer);
        userName = findViewById(R.id.tv_username);

        textView.setVisibility(View.INVISIBLE);

        etDataInput.setVisibility(View.INVISIBLE);
        simpleRequestBtn.setVisibility(View.INVISIBLE);
        getPokemon.setVisibility(View.INVISIBLE);
        trainerType.setVisibility(View.INVISIBLE);
        trainer.setVisibility(View.INVISIBLE);
        userName.setVisibility(View.INVISIBLE);



    }

    public void reload() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "CURRENT USER: " + currentUser);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful() ){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        pokeTrainer = new Trainer();
                        pokeTrainer =  task.getResult().toObject(Trainer.class);
                        setUserUI();
                    }
                    else{
                        Snackbar.make(mParentLayout, "SOMETHING WENT WRONG DOCUMENT DOESNT EXIST", Snackbar.LENGTH_SHORT).show();
                    }
                }  else{
                        Snackbar.make(mParentLayout, "SOMETHING WENT WRONG TASK FAILED", Snackbar.LENGTH_SHORT).show();

                }

                }
            });
    }


    public void updateUI(String fName, String trainType){
        trainerType.setText(trainType);
        userName.setText(fName);
        trainer.setImageResource(R.drawable.eevee);
        etDataInput.setVisibility(View.VISIBLE);
        simpleRequestBtn.setVisibility(View.VISIBLE);
        getPokemon.setVisibility(View.VISIBLE);
        trainerType.setVisibility(View.VISIBLE);
        trainer.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        btCreate.setVisibility(View.INVISIBLE);
        btLogin.setVisibility(View.INVISIBLE);
        etUser.setVisibility(View.INVISIBLE);
        etPassword.setVisibility(View.INVISIBLE);
        tvUser.setVisibility(View.INVISIBLE);
        tvPass.setVisibility(View.INVISIBLE);

    }

    public void createUser(String email, String password, Dialog dialog, String fName,
                           String lName, int age, String trainType) {
//
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                setDocument(user,email, fName,
                                        lName,age,trainType);
                                dialog.dismiss();
                                updateUI(fName, trainType);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivityTest.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    public void logIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            reload();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivityTest.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }



    public void showDialog(View view) {
        Dialog dialog = new Dialog(this);

        EditText email, password, fName, lName, age;
        Spinner trainerType;
        dialog.setContentView(R.layout.sign_up);
        Button btCancel = dialog.findViewById(R.id.bt_cancel_signup);
        Button btCreate = dialog.findViewById(R.id.bt_create_signup);
        email = dialog.findViewById(R.id.et_email_signup);
        password = dialog.findViewById(R.id.et_password_signup);
        fName = dialog.findViewById(R.id.et_fname_signup);
        lName = dialog.findViewById(R.id.et_lname_signup);
        age = dialog.findViewById(R.id.et_age_signup);
        trainerType = dialog.findViewById(R.id.spinner);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
            public void onClick(View v) {
                String fn, ln, pw, em, tt;
                int ag;
                fn = fName.getText().toString();
                ln = lName.getText().toString();
                pw = password.getText().toString();
                em = email.getText().toString();
                Log.d(TAG, pw);
                Log.d(TAG, em);
                ag = Integer.parseInt(age.getText().toString());
                tt = trainerType.getSelectedItem().toString();
                if(!fn.equals("") && !ln.equals("") && !pw.equals("") && !em.equals("") && ag > 13) {
                    createUser( em, pw, dialog, fn, ln,  ag, tt);
                }
            }
        });

        if(!etUser.getText().toString().equals("")){
            email.setText(etUser.getText().toString());
        }
        if(!etPassword.getText().toString().equals("")){
            password.setText(etUser.getText().toString());
        }
        dialog.show();
    }

    public void setDocument(FirebaseUser user, String email, String fName,  String lName, int age, String trainerType){
        String userUid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Trainer trainer = new Trainer();
        trainer.setAge(age);
        trainer.setEmail(email);
        trainer.setUserID(userUid);
        trainer.setFirstName(fName);
        trainer.setLastName(lName);
        trainer.setTrainerType(trainerType);
        DocumentReference newUser = db.collection("users")
                .document(userUid);
        newUser.set(trainer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar.make(mParentLayout, "Catch em all trainer!", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Snackbar.make(mParentLayout, "Trainer registration failed!", Snackbar.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void setUserUI( ){
            trainer.setImageResource(R.drawable.eevee);
            userName.setText(pokeTrainer.getFirstName());
            trainerType.setText(pokeTrainer.getTrainerType());
            etDataInput.setVisibility(View.VISIBLE);
            simpleRequestBtn.setVisibility(View.VISIBLE);
            getPokemon.setVisibility(View.VISIBLE);
            trainerType.setVisibility(View.VISIBLE);
            trainer.setVisibility(View.VISIBLE);
            userName.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            btCreate.setVisibility(View.INVISIBLE);
            btLogin.setVisibility(View.INVISIBLE);
            etUser.setVisibility(View.INVISIBLE);
            etPassword.setVisibility(View.INVISIBLE);
            tvUser.setVisibility(View.INVISIBLE);
            tvPass.setVisibility(View.INVISIBLE);
            for(Integer i : pokeTrainer.getPokedex()){
                pokemonRetriever.getByID(i.intValue(), new PokemonRetriever.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivityTest.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Pokemon poke = new Pokemon();
                            pokemonRetriever.makePokemon(poke, response, pokemons);
                            Log.d("MainActivity", "Pokemon added: " + poke.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String pokeName) {
                        Toast.makeText(MainActivityTest.this, "Returned Pokemon Named: " + pokeName, Toast.LENGTH_SHORT).show();
                        textView.setText(pokeName );
                        Log.d(TAG, "Pokemon name from onClick(): " + pokeName);
                    }
                });
            }
            Log.d(TAG, pokeTrainer.toString());

    }

    public void updateTrainer() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "CURRENT USER: " + currentUser);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.update("pokedex", pokeTrainer.getPokedex()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("UPDATE", "PokeDex Updated in fireStore");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("UPDATE", "Failed to update pokedex");
            }
        });
    }
}

