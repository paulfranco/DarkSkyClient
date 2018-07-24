package co.paulfran.paulfranco.darkskyclient.services;

import co.paulfran.paulfranco.darkskyclient.models.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    @GET("{lat}, {lng}")
    Call<Weather> getWeather(@Path("lat") double lat, @Path("lng") double lng);

}
