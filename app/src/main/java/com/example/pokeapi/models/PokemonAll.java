package com.example.pokeapi.models;

import com.google.gson.annotations.SerializedName;

public class PokemonAll {
    @SerializedName("results")
    private Result[] result;

    public PokemonAll(Result[] result) {
        this.result = result;
    }

    public Result[] getResults() {
        return result;
    }
}
