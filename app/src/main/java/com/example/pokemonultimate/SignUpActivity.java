package com.example.pokemonultimate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokemonultimate.api.API;
import com.example.pokemonultimate.dto.response.SignUpDto;
import com.example.pokemonultimate.dto.response.ErrorDto;
import com.example.pokemonultimate.dto.response.RefreshTokenDto;
import com.example.pokemonultimate.prefs.AppPreferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    protected Button saveBtn;
    protected EditText nameTxt, ageTxt, userNameTxt, passWordTxt, addressTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveBtn = findViewById(R.id.signUpBtn);
        nameTxt = findViewById(R.id.nameEditText);
        ageTxt = findViewById(R.id.ageEditText);
        userNameTxt = findViewById(R.id.usernameEditText);
        passWordTxt = findViewById(R.id.passwordEditText);
        addressTxt = findViewById(R.id.addressEditText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userNameTxt.getText().toString().trim();
                String password = passWordTxt.getText().toString().trim();
                String name = nameTxt.getText().toString().trim();
                int age = Integer.parseInt(ageTxt.getText().toString());
                String address = addressTxt.getText().toString().trim();

                signup(username, password, age, name, address);
            }
        });


    }

    private void signup(String uname, String pass, int age, String name, String address) {
        API.userApi()
                .signUp(new SignUpDto(name,age,uname,pass,address))
                .enqueue(new Callback<RefreshTokenDto>() {
                    @Override
                    public void onResponse(@NonNull Call<RefreshTokenDto> call, @NonNull Response<RefreshTokenDto> response) {
                        if (response.isSuccessful()) {
                            RefreshTokenDto refreshTokenDto = response.body();
                            if (refreshTokenDto != null) {
                                Log.d("DEBUG", "Access Token: " + refreshTokenDto.getAccessToken());
                                Log.d("DEBUG", "Token Type: " + refreshTokenDto.getTokenType());
                                AppPreferences.getInstance().setAccessToken(refreshTokenDto.getAccessToken());
                                me();
                            }
                        } else {
                            ResponseBody errorBody = null;
                            try {
                                errorBody = response.errorBody();
                                if (errorBody != null) {
                                    String json = errorBody.string();
                                    ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                                    Toast.makeText(SignUpActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<RefreshTokenDto> call, Throwable t) {
                        Log.e("DEBUG", "Failed to login", t);
                        Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void me() {
        API.userApi().u().enqueue(new Callback<SignUpDto>() {
            @Override
            public void onResponse(@NonNull Call<SignUpDto> call, @NonNull Response<SignUpDto> response) {
                if (response.isSuccessful()) {
                    SignUpDto signUpDto = response.body();
                    if (signUpDto != null) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        Toast.makeText(SignUpActivity.this, "User Successfully Created: " + signUpDto.getName(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(SignUpActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<SignUpDto> call, Throwable t) {
                Log.e("DEBUG", "Failed to fetch user", t);
                Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
