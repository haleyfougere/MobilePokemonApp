package com.example.pokeapi.models;

public class Pokemon {
    private String name;
    private Types[] types;
    private Sprites sprites;
    private int height;
    private int weight;

    public Pokemon(String name, Types[] types, Sprites sprites, int height, int weight) {
        this.name = name;
        this.types = types;
        this.sprites = sprites;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Types[] getTypes() {
        return types;
    }

    public Sprites getSprites() { return sprites; }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }
}
