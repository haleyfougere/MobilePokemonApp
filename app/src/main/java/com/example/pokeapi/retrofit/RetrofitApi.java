package com.example.pokeapi.retrofit;

import com.example.pokeapi.models.Pokemon;
import com.example.pokeapi.models.PokemonAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {
    //base url for both the API calls
    String BASE_URL = "https://pokeapi.co";

    //get all pokemon
    @GET("api/v2/pokemon/?limit=1500&offset=0")
    Call<PokemonAll> getAllPokemon();

    //get one pokemon
    @GET("api/v2/pokemon/{onePokemon}")
    Call<Pokemon> getPokemon(@Path("onePokemon") String onePokemon);
}
