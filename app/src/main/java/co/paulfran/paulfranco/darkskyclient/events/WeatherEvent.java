package co.paulfran.paulfranco.darkskyclient.events;

import co.paulfran.paulfranco.darkskyclient.models.Weather;

public class WeatherEvent {

    private final Weather weather;

    public WeatherEvent(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }
}
