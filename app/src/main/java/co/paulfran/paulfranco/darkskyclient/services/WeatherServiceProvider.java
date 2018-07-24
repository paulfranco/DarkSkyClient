package co.paulfran.paulfranco.darkskyclient.services;

import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import co.paulfran.paulfranco.darkskyclient.events.ErrorEvent;
import co.paulfran.paulfranco.darkskyclient.events.WeatherEvent;
import co.paulfran.paulfranco.darkskyclient.models.Currently;
import co.paulfran.paulfranco.darkskyclient.models.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherServiceProvider {

    private static final String BASE_URL = "https://api.darksky.net/forecast/7e87d17a004526d5f1ff090ae5eb689e/";
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
                Weather weather = response.body();
                if (weather != null) {
                    Currently currently = weather.getCurrently();
                    Log.e(TAG, "Temperature is " + currently.getTemperature());
                    EventBus.getDefault().post(new WeatherEvent(weather));
                } else {
                    Log.e(TAG, "No Response: Check secret Key");
                    EventBus.getDefault().post(new ErrorEvent("No weather data available"));
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e(TAG, "Failure to get Weather Data");
                EventBus.getDefault().post(new ErrorEvent("Unable to connect to weather server"));

            }
        });
    }

}
