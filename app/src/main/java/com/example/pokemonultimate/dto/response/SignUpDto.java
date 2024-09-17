package com.example.pokemonultimate.dto.response;

public class SignUpDto {

    private final String name;
    private final int age;
    private final String username;
    private final String password;
    private final String address;

    public SignUpDto(String name, int age, String username, String password, String address) {
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public String getName() {
        return name;
    }
}
