package com.example.pokemonultimate.dto.response;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenDto {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
