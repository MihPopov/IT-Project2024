package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.educationproject2024.R;
import com.example.educationproject2024.Utils;
import com.example.educationproject2024.controller.API;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    MaterialButton button;
    AppCompatCheckBox checkBox;
    TextInputLayout fullName, email, password1, password2;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        button = findViewById(R.id.button_registration);
        checkBox = findViewById(R.id.privacyPolicyCheckBox);
        fullName = findViewById(R.id.registrationSurnameAndName);
        email = findViewById(R.id.registrationEmailAddress);
        password1 = findViewById(R.id.registrationPassword);
        password2 = findViewById(R.id.registrationPasswordConfirm);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = String.valueOf(fullName.getEditText().getText());
                String e = String.valueOf(email.getEditText().getText());
                String p1 = String.valueOf(password1.getEditText().getText());
                String p2 = String.valueOf(password2.getEditText().getText());

                if (checkBox.isChecked() && isEmailValid(e) && p1.equals(p2) && e.equals(e.toLowerCase())) {
                    Utils.user.setFullName(fn);
                    Utils.user.setEmail(e);
                    Utils.user.setPassword(p1);

                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("full_name", Utils.user.getFullName());
                    parameters.put("email", Utils.user.getEmail());
                    parameters.put("password", Utils.user.getPassword());
                    Call<Void> call = api.createAccount(APIKEY, parameters);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegistrationActivity.this, "Возникла ошибка! Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(RegistrationActivity.this, "Проверьте корректность заполнения полей!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void LoginFromRegistration(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    public void policyPrivacyOpen(View view) {
        startActivity(new Intent(RegistrationActivity.this, PrivacyPolicyActivity.class));
    }
}