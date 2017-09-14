package com.foo.umbrella.ui.main_activity;

import com.foo.umbrella.weather.Weather;

import java.util.ArrayList;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public interface MainActivityInteractor {
    void startSettings();

    void setRecyclerView(Weather weather, ArrayList myDays);

    void setColors(int currentTemp, int cTempLimit);

    void displayCurrentWeather(String temp, String condition, String currentZipcode);
}
