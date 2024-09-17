package com.example.pokemonultimate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokemonultimate.Table.PokemonTbl;

@Database(
        entities = {PokemonTbl.class},
        version = 1,
        exportSchema = true // Consider setting to true to export schema files
)
public abstract class DBHelper extends RoomDatabase {
    public abstract PokemonDao pokemonDao();
    public static DBHelper instance;
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DBHelper.class,
                            "ultimatepokedex.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
