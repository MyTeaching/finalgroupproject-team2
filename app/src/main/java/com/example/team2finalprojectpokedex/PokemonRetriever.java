package com.example.team2finalprojectpokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PokemonRetriever {

    public static final String API_V_2_POKEMON = "https://pokeapi.co/api/v2/pokemon/";
    private static final String TAG = "PokemonRetriever";

    Context context;
    String pokeName;
    public PokemonRetriever(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(JSONObject response);
    }

    public void getPokeName(String pokeId, VolleyResponseListener volleyResponseListener){
        Log.d("BUTTON"  , "Inside button onClick");
        String pokeApiUrl = API_V_2_POKEMON + pokeId;
        Log.d("BUTTON", pokeApiUrl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, pokeApiUrl,
                null, response -> {
                    pokeName = "";
                    try {
                        pokeName = response.getString("name");
                        Log.d("BUTTON", pokeName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    volleyResponseListener.onResponse(response);
                }, error -> {
                    String pokeName = "Not found";
    //                Toast.makeText(context, "Retriever: Pokemon Named --> " + pokeName, Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onError("Something wrong");
                });
        RequestSingleton.getInstance(context ).addToRequestQueue(request);
    }

//    public List<Pokemon> getPokemonByName(String pokeName){
//
//    }
    public void  getPokemonById(String pokeID){
//        List<Pokemon> pokemonInfo = new ArrayList<>();

        String url = API_V_2_POKEMON + pokeID;
        Log.d("PokemonRetriever", "Insiside get Pokemon by ID");
        Log.d("PokemonRetriever", "url: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url
                ,null, response -> {

                }, error -> {

                });
        RequestSingleton.getInstance(context ).addToRequestQueue(request);
    }

    public void getPokeDesc(String url, VolleyResponseListener volleyResponseListener ) {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET,url,null, volleyResponseListener::onResponse, error -> {

                });
        RequestSingleton.getInstance(context ).addToRequestQueue(request);
    }

    public void makePokemon(Pokemon poke, JSONObject response, ArrayList<Pokemon> pokemons, View v, Trainer trainer, FirebaseUser user) throws JSONException {
        poke.setInfo(response);
        Log.d(TAG, "POKEMON AFTER SET INFO: " + poke);
        getPokeDesc(
                response.getJSONObject("species").getString("url"),
                new PokemonRetriever.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.d(TAG, "Error retrieving flavor text");
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray flavor = response.getJSONArray("flavor_text_entries");
                            String desc = flavor.getJSONObject(0).getString("flavor_text");
                            poke.setDescription(response);
                            addPokeToList(pokemons, poke, v);
                            trainer.updateTrainer(user, pokemons);
                            Log.d(TAG, "POKEMON: " + poke.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
    }
        public void getByID(int pokeId, VolleyResponseListener volleyResponseListener){
            Log.d("BUTTON"  , "Inside button onClick");
            String pokeApiUrl = API_V_2_POKEMON + String.valueOf(pokeId);
            Log.d("BUTTON", pokeApiUrl);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, pokeApiUrl,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pokeName = "";
                    try {
                        pokeName = response.getString("name");
                        Log.d("BUTTON", pokeName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    volleyResponseListener.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String pokeName = "Not found";
                    volleyResponseListener.onError("Something wrong");
                }
            });
            RequestSingleton.getInstance(context ).addToRequestQueue(request);
        }

        public void makePokemon( Pokemon poke,JSONObject response, ArrayList<Pokemon> pokemons) throws JSONException {
            poke.setInfo(response);
            Log.d(TAG, "POKEMON AFTER SET INFO: " + poke.toString());
            getPokeDesc(
                    response.getJSONObject("species").getString("url").toString(),
                    new PokemonRetriever.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Log.d(TAG, "Error retrieving flavor text");
                        }

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray flavor = response.getJSONArray("flavor_text_entries");
                                poke.setDescription(response);
                                addPokeToList(pokemons, poke);

                                Log.d(TAG, "POKEMON: " + poke.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
        }

    public void addPokeToList(List<Pokemon> pokemons,Pokemon poke, View v){
        pokemons.add(poke);
        TextView tv = (TextView)v;
        tv.setText(pokemons.get(pokemons.indexOf(poke)).getDescription());
    }
    public void addPokeToList(List<Pokemon> pokemons,Pokemon poke){
        boolean contains = false;
        for(Pokemon pokemon : pokemons){
            if(pokemon.getId() == poke.getId()){
                contains = true;
            }
        }
        if(!contains) {
            pokemons.add(poke);
        }
    }

//    //  Make pokemon from draw cards
    public void makePokemon(Pokemon poke, JSONObject response, ArrayList<Pokemon> pokeList, Context context, Trainer trainer, FirebaseUser user) throws JSONException {
        poke.setInfo(response);
        Log.d(TAG, "POKEMON AFTER SET INFO: " + poke);
        getPokeDesc(
                response.getJSONObject("species").getString("url"),
                new PokemonRetriever.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.d(TAG, "Error retrieving flavor text");
                    }
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray flavor = response.getJSONArray("flavor_text_entries");
                            String desc = flavor.getJSONObject(0).getString("flavor_text");
                            poke.setDescription(response);
                            addPokeToList(pokeList, poke);
                            Intent cardIntent = new Intent(context, CardView.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("POKEMONLIST",pokeList);
                            bundle.putString("CURRPOKEMON", poke.getName());
                            cardIntent.putExtras(bundle);
                            trainer.updateTrainer(user, pokeList);
                            context.startActivity(cardIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });


    }
}
