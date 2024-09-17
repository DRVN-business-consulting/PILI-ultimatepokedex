package com.example.pokemonultimate.api;

import com.example.pokemonultimate.dto.request.LoginDto;
import com.example.pokemonultimate.dto.response.PokemonDto;
import com.example.pokemonultimate.dto.response.SignUpDto;
import com.example.pokemonultimate.dto.response.RefreshTokenDto;
import com.example.pokemonultimate.dto.response.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserApi {

    @POST("login")
    Call<RefreshTokenDto> login(@Body LoginDto user);

    @POST("signup")
    Call<RefreshTokenDto> signUp(@Body SignUpDto user);

    @POST("refresh-token")
    Call<RefreshTokenDto> refreshToken();

    @GET("user/me")
    Call<UserDto> me();

    @GET("user/me")
    Call<SignUpDto> u();

    @GET("pokemon/5")
    Call<PokemonDto> pokemon();

    @PATCH("user/me")
    Call<UserDto> updateMe();

}
