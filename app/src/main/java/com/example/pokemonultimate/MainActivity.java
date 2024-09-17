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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pokemonultimate.api.API;
import com.example.pokemonultimate.dto.request.LoginDto;
import com.example.pokemonultimate.dto.response.ErrorDto;
import com.example.pokemonultimate.dto.response.RefreshTokenDto;
import com.example.pokemonultimate.dto.response.UserDto;
import com.example.pokemonultimate.prefs.AppPreferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    protected Button loginBtn, signUpBtn;
    protected EditText userName, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        userName = findViewById(R.id.usernameEditText);
        passWord = findViewById(R.id.passwordEditText);

        AppPreferences.initialize(this);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = userName.getText().toString().trim();
                String password = passWord.getText().toString().trim();

                login(username, password);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(String uname, String pass) {
        API.userApi()
                .login(new LoginDto(uname, pass))
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
                                    Toast.makeText(MainActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void me() {
        API.userApi().me().enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful()) {
                    UserDto userDto = response.body();
                    if (userDto != null) {
                        Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Hello, " + userDto.getUsername(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ResponseBody errorBody = null;
                    try {
                        errorBody = response.errorBody();
                        if (errorBody != null) {
                            String json = errorBody.string();
                            ErrorDto errorDto = API.gson.fromJson(json, ErrorDto.class);
                            Toast.makeText(MainActivity.this, errorDto.getDetail(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.e("DEBUG", "Failed to fetch user", t);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}