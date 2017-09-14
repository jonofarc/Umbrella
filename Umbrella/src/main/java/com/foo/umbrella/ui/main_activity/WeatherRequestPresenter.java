package com.foo.umbrella.ui.main_activity;

import com.foo.umbrella.weather.Weather;

import java.net.URL;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public interface WeatherRequestPresenter {
    void requestWeather(String BASE_URL);
}
