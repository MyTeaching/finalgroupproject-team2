package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class PokemonRetriever {

    public static final String API_V_2_POKEMON = "https://pokeapi.co/api/v2/pokemon/";
    private static final String TAG = "PokemoneRetriever";

    Context context;
    String pokeName;
    public PokemonRetriever(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(JSONObject response);
        void onResponse(String response);
    }

    public void getPokeName(String pokeId, VolleyResponseListener volleyResponseListener){
        Log.d("BUTTON"  , "Inside button onClick");
        String pokeApiUrl = API_V_2_POKEMON + pokeId;
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
                volleyResponseListener.onResponse(pokeName);
                volleyResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String pokeName = "Not found";
//                Toast.makeText(context, "Retriever: Pokemon Named --> " + pokeName, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something wrong");
            }
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
                ,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestSingleton.getInstance(context ).addToRequestQueue(request);
    }

    public void getPokeDesc(String url, VolleyResponseListener volleyResponseListener ) {
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET,url,null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                    volleyResponseListener.onResponse(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestSingleton.getInstance(context ).addToRequestQueue(request);
    }

    public void makePokemon(Pokemon poke, JSONObject response, List<Pokemon> pokemons, View v) throws JSONException {
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
                            String desc = flavor.getJSONObject(0).getString("flavor_text").toString();
                            poke.setDescription(response);
                            addPokeToList(pokemons, poke, v);
                            Log.d(TAG, "POKEMON: " + poke.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });


    }

    public void addPokeToList(List<Pokemon> pokemons,Pokemon poke, View v){
        pokemons.add(poke);
        TextView tv = (TextView)v;
        tv.setText(pokemons.get(pokemons.indexOf(poke)).getDescription());
    }

}
