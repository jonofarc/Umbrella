package com.foo.umbrella.ui.umbrella_settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.foo.umbrella.PreferencesManager;
import com.foo.umbrella.R;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public class UmbrellaSettingsPresenterImpl implements UmbrellaSettingsPresenter {
    Context mContext;
    UmbrellaSettingsInteractor interactor;

    public UmbrellaSettingsPresenterImpl(UmbrellaSettings umbrellaSettings) {
        mContext=umbrellaSettings;
        interactor=umbrellaSettings;
    }


    public void loadSettings() {
        String zipCode="";
        int unitSelection=0;
        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        //load ZipCode
        if(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString().isEmpty()){
            Toast.makeText(mContext, R.string.toast_no_zipCode, Toast.LENGTH_SHORT).show();
        }else {

            zipCode=settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString();



        }
        //Load Units
        if(settings.getString(PreferencesManager.UmbrellaPreferences.units, "").toString() != null){



            if(settings.getString(PreferencesManager.UmbrellaPreferences.units, "").toString().equals("Celcius")){
                unitSelection=0;
            }else{
                unitSelection=1;
            }

        }else {
            Toast.makeText(mContext, "Units are null", Toast.LENGTH_SHORT).show();

            //updateUnits();
        }
        interactor.setSettings(zipCode,unitSelection);

    }
    public void updateUnits(String dropDownSelectedItem) {
        //Toast.makeText(this, "Temperature Units Updated ", Toast.LENGTH_SHORT).show();
        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PreferencesManager.UmbrellaPreferences.units,dropDownSelectedItem);
        editor.commit();

    }
    public void updateZipCode(String zipCode) {
        Toast.makeText(mContext, "Zip Code Updated", Toast.LENGTH_SHORT).show();
        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PreferencesManager.UmbrellaPreferences.zipCode,zipCode);
        editor.commit();
    }
}
