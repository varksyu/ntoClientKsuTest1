package ru.myitschool.work.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myitschool.work.R;
import ru.myitschool.work.core.Constants;
import ru.myitschool.work.domain.IsExist;
import ru.myitschool.work.source.SAPI;

public class AuthorizationActivity extends AppCompatActivity {

    private EditText username;
    private Button loginButton;
    private TextView errorText;
    SAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_authorization);

        username = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);
        errorText = findViewById(R.id.error);
        loginButton.setEnabled(false);



        //TextChangedListener isValidateLogin = new TextChangedListener();
        //username.addTextChangedListener(isValidateLogin);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                validateLogin();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateLogin();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        loginButton.setOnClickListener(view -> login());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SAPI.class);



    }
    Boolean tt = false;
    public void login() {
        String login = username.getText().toString();
        Call<IsExist> call = service.isExist(login);

        call.enqueue(new Callback<IsExist>() {
            @Override
            public void onResponse(Call<IsExist> call, Response<IsExist> response) {
                if (response.code() == 200) {
                    errorText.setVisibility(View.INVISIBLE);
                    SharedPreferences prefs = getSharedPreferences("app", MODE_PRIVATE);
                    prefs.edit().putString("login", login).apply();
                    startProfileActivity();
                }
                else {
                    errorText.setText("Ошибка((");
                    errorText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<IsExist> call, Throwable t) {
                errorText.setText("Ошибка((");
                errorText.setVisibility(View.VISIBLE);
            }
        });
        /*if (login == "varksyu") startProfileActivity();
        else {
            errorText.setText("Ошибка((");
            errorText.setVisibility(View.VISIBLE);
        }*/


    }

    public void validateLogin() {
        String login = username.getText().toString();
        boolean isValid = login.length() >= 3 && !login.matches(".*\\W.*") && !Character.isDigit(login.charAt(0)) && login != "";
        loginButton.setEnabled(isValid);
    }
    private void startProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

}