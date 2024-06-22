package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.educationproject2024.Utils;
import com.example.educationproject2024.controller.API;
import com.example.educationproject2024.data.User;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educationproject2024.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    MaterialButton button;
    TextInputLayout email, password;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.button_login);
        email = findViewById(R.id.loginEmailAddress);
        password = findViewById(R.id.loginPassword);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = String.valueOf(email.getEditText().getText());
                String p = String.valueOf(password.getEditText().getText());

                if (e.equals("") || p.equals("")) {
                    Toast.makeText(LoginActivity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Call<List<User>> call = api.getAccount(APIKEY, "full_name,email,password");
                    call.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            List<User> data = response.body();
                            boolean isFind = false;
                            for (int i = 0; i < data.toArray().length; i++) {
                                if (data.get(i).getEmail().equals(e) && data.get(i).getPassword().equals(p)) {
                                    isFind = true;
                                    Utils.user.setFullName(data.get(i).getFullName());
                                    Utils.user.setEmail(data.get(i).getEmail());
                                    Utils.user.setPassword(data.get(i).getPassword());
                                    break;
                                }
                            }
                            if (isFind) {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Пользователь не найден! Проверьте корректность ввода почты и пароля!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Произошла ошибка! Попробуйте ещё раз!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void RegistrationFromLogin(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}