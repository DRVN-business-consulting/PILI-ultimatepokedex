package com.example.pokemonultimate.dto.response;

import com.example.pokemonultimate.Image;
import com.example.pokemonultimate.Name;

import java.util.List;

public class PokemonDto {
    private int id;
    private Name name;
    private List<String> type;
    private String description;
    private Image image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
