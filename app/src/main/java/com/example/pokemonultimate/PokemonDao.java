package com.example.pokemonultimate;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pokemonultimate.Table.PokemonTbl;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    List<PokemonTbl> getAll();
    @Query("SELECT * FROM pokemon WHERE id = :id")
    PokemonTbl getById(int id);
    @Insert
    void insert(PokemonTbl pokemon);
    @Update
    void update(PokemonTbl pokemon);
    @Query("DELETE FROM pokemon WHERE id = :id")
    void delete(int id);
}
