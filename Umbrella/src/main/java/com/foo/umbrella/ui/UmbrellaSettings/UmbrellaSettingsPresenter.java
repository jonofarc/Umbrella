package com.foo.umbrella.ui.UmbrellaSettings;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public interface UmbrellaSettingsPresenter {
    void loadSettings();
    void updateUnits(String dropDownSelectedItem);
    void updateZipCode(String zipCode);

}
