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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    Button simpleRequestBtn, getPokemon, btCreate, btLogin;
//    String pokeQuery;
    List<Pokemon> pokemons;
    public static  final String TAG = "MainActivity";

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
        PokemonRetriever pokemonRetriever = new PokemonRetriever(MainActivityTest.this);
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
        trainerType = findViewById(R.id.tv_username);
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
        etDataInput.setVisibility(View.VISIBLE);
        simpleRequestBtn.setVisibility(View.VISIBLE);
        getPokemon.setVisibility(View.VISIBLE);
        trainerType.setVisibility(View.VISIBLE);
        trainer.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference trainers = db.collection("users");
        Trainer trainer = trainers.getId(currentUser.getUid());
        trainerType.setText(currentUser.get);

        btCreate.setVisibility(View.INVISIBLE);
        btLogin.setVisibility(View.INVISIBLE);
        etUser.setVisibility(View.INVISIBLE);
        etPassword.setVisibility(View.INVISIBLE);
        tvUser.setVisibility(View.INVISIBLE);
        tvPass.setVisibility(View.INVISIBLE);
    }

    public void updateUI(FirebaseUser user){
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
//            String uid = user.getUid();
            userName.setText(name);
            trainerType.setText(email);
        }
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
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivityTest.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
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

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivityTest.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
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
                .document();
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
}

