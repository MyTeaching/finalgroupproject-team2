package com.example.finalproject;

public class Pokemon {
    String name;
    int id, image;

    public Pokemon(String name, int id, int image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public Pokemon(int id, int image) {
        this.id = id;
        this.image = image;
        this.name = PokemonRetriever(this.id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
