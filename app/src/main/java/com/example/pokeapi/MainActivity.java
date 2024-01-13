package com.example.pokeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokeapi.databinding.ActivityMainBinding;
import com.example.pokeapi.models.Pokemon;
import com.example.pokeapi.models.PokemonAll;
import com.example.pokeapi.models.Result;
import com.example.pokeapi.models.Type;
import com.example.pokeapi.models.Types;
import com.example.pokeapi.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.sql.Array;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setup the autocomplete text box to the view
        AutoCompleteTextView actv = findViewById(R.id.autoCompleteTextView);
        //set a hint for the input for the user
        actv.setHint("Enter a Pokémon...");

        //set the textviews to hidden when the app is opened
        binding.textViewHeight.setVisibility(View.INVISIBLE);
        binding.textViewTypes.setVisibility(View.INVISIBLE);
        binding.textViewPokemonName.setVisibility(View.INVISIBLE);
        binding.textViewWeight.setVisibility(View.INVISIBLE);
        binding.imageViewPokemon.setVisibility(View.INVISIBLE);
        binding.textViewCatch.setVisibility(View.INVISIBLE);

        //Retrofit Api to get all pokemon
        Call<PokemonAll> callAll = RetrofitClient.getInstance().getApi().getAllPokemon();

        callAll.enqueue(new Callback<PokemonAll>() {
            @Override
            public void onResponse(Call<PokemonAll> callAll, Response<PokemonAll> response) {
                PokemonAll pokemonAll = response.body();
                //create a new array to hold all the pokemon names
                String[] allNames = new String[pokemonAll.getResults().length];
                //create a new array of result objects for all pokemon
                Result[] allResults = pokemonAll.getResults();

                //for loop to iterate through each result object and get the names of each result
                //in each object and save it in a new array of all names
                for(int i = 0; i < allResults.length; i++){
                    Result result = allResults[i];
                    allNames[i] = result.getName();
                }

                //populate the autocompletetextview
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.select_dialog_item, allNames);
                //will start at the first character
                actv.setThreshold(1);
                actv.setAdapter(adapter);
            }
            //failure of the API call
            @Override
            public void onFailure(Call<PokemonAll> call, Throwable t) {
                //show a toast of the error
                Toast.makeText(MainActivity.this, "API failure.", Toast.LENGTH_SHORT).show();
            }
        });

        //setup for the button
        Button goButton;
        goButton = findViewById(R.id.goButton);

        //onclick listener for the button
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedPokemonName = actv.getText().toString();

                //if the text field is not empty
                if(!selectedPokemonName.isEmpty()){
                    //Retrofit call to the API to get the pokemon that was selected/typed in
                    Call<Pokemon> call = RetrofitClient.getInstance().getApi().getPokemon(selectedPokemonName);

                    call.enqueue(new Callback<Pokemon>() {
                        @Override
                        public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                            //if statement to check if the pokemon that the user input is a valid one
                            if (response.isSuccessful()) {
                                Pokemon pokemon = response.body();
                                //Display the slogan
                                TextView textviewCatch = binding.textViewCatch;
                                textviewCatch.setVisibility(View.VISIBLE);

                                //Display the name
                                TextView textViewPokemonName = binding.textViewPokemonName;
                                textViewPokemonName.setText(pokemon.getName());
                                textViewPokemonName.setVisibility(View.VISIBLE);

                                //Display the Height
                                TextView textViewHeight = binding.textViewHeight;
                                String heightVal = String.valueOf(pokemon.getHeight());
                                String heightText = "Height: " + heightVal + " ft";
                                textViewHeight.setText(heightText);
                                textViewHeight.setVisibility(View.VISIBLE);

                                //Display the Weight
                                TextView textViewWeight = binding.textViewWeight;
                                String weightVal = String.valueOf(pokemon.getWeight());
                                String weightText = "Weight: " + weightVal + " lbs";
                                textViewWeight.setText(weightText);
                                textViewWeight.setVisibility(View.VISIBLE);

                                //Display the image(sprite)
                                ImageView imageViewPokemon = binding.imageViewPokemon;
                                String imageUrl = pokemon.getSprites().getImgUrl();

                                //use Glide to load image from API
                                Glide.with(view).load(imageUrl).into(imageViewPokemon);
                                imageViewPokemon.setVisibility(View.VISIBLE);

                                //Display the Types
                                TextView textViewTypes = binding.textViewTypes;
                                Types[] types = pokemon.getTypes();
                                String pokeTypes = "";

                                //iterate through the Types array to get more than one type
                                //(if the pokemon has more than one)
                                for (int i = 0; i < types.length; i++) {
                                    Type type = types[i].getType();
                                    String typeName = type.getTypeName();

                                    pokeTypes += typeName;

                                    //if statement for if there is more than one type
                                    if (i < types.length - 1) {
                                        pokeTypes += " - ";
                                    }
                                }
                                String typesText = "Type(s): " + pokeTypes;
                                textViewTypes.setText(typesText);
                                textViewTypes.setVisibility(View.VISIBLE);
                            }
                            //if the pokemon is not valid it will show a toast
                            else {
                                Toast.makeText(MainActivity.this, "Pokémon not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //if there is an API failure.
                        @Override
                        public void onFailure(Call<Pokemon> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "API error, try again later.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                //if the input value is empty, show a toast
                else
                {
                    Toast.makeText(MainActivity.this, "Please enter a Pokémon.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}