package com.example.pokemonultimate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pokemonultimate.Adapter.PokemonListAdapter;
import com.example.pokemonultimate.Model.Pokemon;
import com.example.pokemonultimate.Table.PokemonTbl;
import com.example.pokemonultimate.api.API;
import com.example.pokemonultimate.dto.response.ErrorDto;
import com.example.pokemonultimate.dto.response.PokemonDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageActivity extends AppCompatActivity {
    private RecyclerView pokemonListRecyclerView;
    DBHelper dbHelper;
    private PokemonDao pokemonDao;
    ArrayList<Pokemon> pokemonList = new ArrayList<>();
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        pokemonListRecyclerView = findViewById(R.id.pokemonListRecyclerView);


        pokemon();
        dbHelper = DBHelper.getInstance(getApplicationContext());

        pokemonDao = dbHelper.pokemonDao();





    }

    private void pokemon() {
        API.userApi().pokemon().enqueue(new Callback<PokemonDto>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDto> call, @NonNull Response<PokemonDto> response) {
                if (response.isSuccessful()) {
                    PokemonDto pokemonDto = response.body();
                    if (pokemonDto != null) {

                        PokemonTbl newPokemon = new PokemonTbl();
                        newPokemon.setName(pokemonDto.getName().getEnglish());
                        newPokemon.setDescription(pokemonDto.getDescription());
                        newPokemon.setImage(pokemonDto.getImage().getSprite());
                        newPokemon.setType(pokemonDto.getType().get(0));

                        new InsertPokemonTask().execute(newPokemon);


                    }
                    pokemonList();
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(HomePageActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (errorBody != null) {
                            errorBody.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonDto> call, Throwable t) {
                Log.e("DEBUG", "Failed to fetch user", t);
                Toast.makeText(HomePageActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class InsertPokemonTask extends AsyncTask<PokemonTbl, Void, Void> {
        @Override
        protected Void doInBackground(PokemonTbl... pokemons) {
            pokemonDao.insert(pokemons[0]);
            return null;
        }
    }

    private void pokemonList(){
        pokemonListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pokemonListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        PokemonListAdapter pokemonListAdapter = new PokemonListAdapter(this, pokemonList);
        pokemonListAdapter.notifyDataSetChanged();
        pokemonListRecyclerView.setAdapter(pokemonListAdapter);
    }
}
