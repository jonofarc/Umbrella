package com.foo.umbrella.ui.main_activity;

import com.foo.umbrella.entities.Weather;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public interface MainActivityPresenter {

    void getWeather();

    void showData(Weather weather);

}
