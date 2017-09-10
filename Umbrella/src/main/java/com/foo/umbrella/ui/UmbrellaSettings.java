package com.foo.umbrella.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.foo.umbrella.PreferencesManager.UmbrellaPreferences;

import com.foo.umbrella.R;


public class UmbrellaSettings extends AppCompatActivity {

    EditText zipCode_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella_settings);
        zipCode_ET= (EditText) findViewById(R.id.et_zipCode);


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSettings();
    }

    private void loadSettings() {
        SharedPreferences settings = getSharedPreferences(UmbrellaPreferences.umbrellaPrefsFile, 0);
        if(settings.getString(UmbrellaPreferences.zipCode, "").toString() != null){
            zipCode_ET.setText(settings.getString(UmbrellaPreferences.zipCode, "").toString());
        }else {
            Toast.makeText(this, "Zip Code is null", Toast.LENGTH_SHORT).show();
        }

    }


    public void updateZipCode(View view) {
        Toast.makeText(this, "Zip Code Updated", Toast.LENGTH_SHORT).show();
        SharedPreferences settings = getSharedPreferences(UmbrellaPreferences.umbrellaPrefsFile, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(UmbrellaPreferences.zipCode,zipCode_ET.getText().toString());
        editor.commit();
    }
}

