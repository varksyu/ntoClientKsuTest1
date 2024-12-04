package ru.myitschool.work.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myitschool.work.R;
import ru.myitschool.work.core.Constants;
import ru.myitschool.work.domain.IsExist;
import ru.myitschool.work.domain.User;
import ru.myitschool.work.source.SAPI;
import ru.myitschool.work.ui.qr.scan.QrScanFragment;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    TextView position;
    TextView lastEntry;
    Button logout;
    Button refresh;
    Button scan;
    ImageView photo;
    SAPI service;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        name = findViewById(R.id.fullname);
        position = findViewById(R.id.position);
        lastEntry = findViewById(R.id.lastEntry);
        logout = findViewById(R.id.logout);
        refresh = findViewById(R.id.refresh);
        scan = findViewById(R.id.scan);
        photo = findViewById(R.id.photo);
        error = findViewById(R.id.error);


        logout.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app", MODE_PRIVATE);
            prefs.edit().clear().apply();
            startAutorizationActivity();
        });

        refresh.setOnClickListener(v -> updateUserData());
        scan.setOnClickListener(v -> startQrScanActivity());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SAPI.class);

        updateUserData();
    }

    private void updateUserData() {
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        Call<User> call = service.getUser(login.toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (user == null) error();
                    name.setText(user.name);
                    position.setText(user.position);
                    Picasso.get().load(user.photo).into(photo);
                    lastEntry.setText(user.lastVisit);
                }
                else {
                    error();
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error();
            }

        });
    }
    private void error() {
        name.setVisibility(View.GONE);
        position.setVisibility(View.GONE);
        photo.setVisibility(View.GONE);
        lastEntry.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        scan.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);

    }
    private void startAutorizationActivity() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }
    private void startQrScanActivity() {
        Intent intent = new Intent(this, QrScanFragment.class);
        startActivity(intent);
    }
    private String timeFormat(String s) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter input = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(s, input);
            DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String result = dateTime.format(output);
            return result;
        }
        return s + "error";
    }
}