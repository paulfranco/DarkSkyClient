package co.paulfran.paulfranco.darkskyclient.services;

import android.util.Log;

import co.paulfran.paulfranco.darkskyclient.models.Currently;
import co.paulfran.paulfranco.darkskyclient.models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherServiceProvider {

    private static final String BASE_URL = "https://api.darksky.net/forecast/6a70dc406b820349630a41ac4ce68967/";
    private Retrofit retrofit;
    private static final String TAG = WeatherServiceProvider.class.getSimpleName();

    private Retrofit getRetrofit() {
        if (this.retrofit == null) {
            // Implement Retrofit
            this.retrofit = new Retrofit.Builder()
                    // 1. Add URL
                    .baseUrl(BASE_URL)
                    // 2. Add Converter Factory
                    .addConverterFactory(GsonConverterFactory.create())
                    // 3. Build
                    .build();
            // 4. Don't Forget to add the Internet Permissions on the Manifest
        }
        return this.retrofit;
    }

    public void getWeather(double lat, double lng) {
        // Create a Service
        WeatherService weatherService = getRetrofit().create(WeatherService.class);
        // call the method that returns a callback
        Call<Weather> weatherData = weatherService.getWeather(lat, lng);
        weatherData.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Currently currently = response.body().getCurrently();
                Log.e(TAG, "Temperature is " + currently.getTemperature());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "Failure to get Weather Data");
            }
        });
    }

}
