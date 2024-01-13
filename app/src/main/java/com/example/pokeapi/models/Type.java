package com.example.pokeapi.models;

import com.google.gson.annotations.SerializedName;

public class Type {
    @SerializedName("name")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }
}
