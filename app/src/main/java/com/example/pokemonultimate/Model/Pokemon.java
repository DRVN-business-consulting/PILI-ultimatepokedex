package com.example.pokemonultimate.Model;

public class Pokemon {

    int id;
    String name;
    String description;
    int image;
    String Type;

    public Pokemon(int id, String name, String description, int image, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
