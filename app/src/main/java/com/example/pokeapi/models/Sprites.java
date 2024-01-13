package com.example.pokeapi.models;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("front_default")
    private String imgUrl;

    //constructor
    public Sprites(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    //getter
    public String getImgUrl() {
        return imgUrl;
    }
}
