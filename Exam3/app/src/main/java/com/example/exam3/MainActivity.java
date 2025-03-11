package com.example.exam3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText cityInput;
    private TextView weatherText;
    private WeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.city_input);
        weatherText = findViewById(R.id.weather_text);
        Button fetchButton = findViewById(R.id.fetch_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherService = retrofit.create(WeatherService.class);

        fetchButton.setOnClickListener(v -> fetchWeather());
    }

    private void fetchWeather() {
        String city = cityInput.getText().toString();
        String apiKey = "c4a03af606dbf26978e07b31fb9d97cc";

        weatherService.getWeather(city, "metric", apiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherText.setText("Температура: " + response.body().getMain().getTemp() + "°C");
                } else {
                    Log.v("S", response.toString());
                    weatherText.setText("Ошибка загрузки данных");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherText.setText("Ошибка: " + t.getMessage());
            }
        });
    }
}
