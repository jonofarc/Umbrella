package com.foo.umbrella.ui.MainActivity;

import com.foo.umbrella.Weather.Weather;

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
