package com.example.educationproject2024.activities;

import static com.example.educationproject2024.Utils.APIKEY;
import static com.example.educationproject2024.Utils.BASE_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.educationproject2024.R;
import com.example.educationproject2024.Utils;
import com.example.educationproject2024.controller.API;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeUserActivity extends AppCompatActivity {

    TextInputEditText fullName, email, password, confirmPassword;
    MaterialButton button;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);

        fullName = findViewById(R.id.changeSurnameAndName);
        email = findViewById(R.id.changeEmailAddress);
        password = findViewById(R.id.changePassword);
        confirmPassword = findViewById(R.id.changePasswordConfirm);
        button = findViewById(R.id.button_change_user_data);

        fullName.setText(Utils.user.getFullName());
        email.setText(Utils.user.getEmail());
        password.setText(Utils.user.getPassword());

        String oldName = Utils.user.getFullName();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fn = String.valueOf(fullName.getText());
                String e = String.valueOf(email.getText());
                String p1 = String.valueOf(password.getText());
                String p2 = String.valueOf(confirmPassword.getText());

                if (isEmailValid(e) && p1.equals(p2) && e.equals(e.toLowerCase()) && !fn.equals("") && !e.equals("") && !p1.equals("") && !p2.equals("")) {
                    Utils.user.setFullName(fn);
                    Utils.user.setEmail(e);
                    Utils.user.setPassword(p1);

                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("full_name", Utils.user.getFullName());
                    parameters.put("email", Utils.user.getEmail());
                    parameters.put("password", Utils.user.getPassword());

                    Call<Void> call = api.updateAccount(APIKEY, parameters, "eq." + oldName);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(ChangeUserActivity.this, "Данные успешно изменены!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChangeUserActivity.this, ProfileActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ChangeUserActivity.this, "Произошла ошибка! Попробуйте ещё раз!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void ProfileFromChangeUser(View view) {
        startActivity(new Intent(ChangeUserActivity.this, ProfileActivity.class));
    }
}