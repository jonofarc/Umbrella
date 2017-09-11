package com.foo.umbrella.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.foo.umbrella.PreferencesManager.UmbrellaPreferences;

import com.foo.umbrella.R;


public class UmbrellaSettings extends AppCompatActivity {

    EditText zipCode_ET;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella_settings);
        zipCode_ET= (EditText) findViewById(R.id.et_zipCode);


        dropdown = (Spinner)findViewById(R.id.spinner1);

        String[] items = new String[]{"Celcius", "Fahrenheit"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSettings();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                updateUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void loadSettings() {
        SharedPreferences settings = getSharedPreferences(UmbrellaPreferences.umbrellaPrefsFile, 0);
        //load ZipCode
        if(settings.getString(UmbrellaPreferences.zipCode, "").toString().isEmpty()){
            Toast.makeText(this, R.string.toast_no_zipCode, Toast.LENGTH_SHORT).show();
        }else {

            zipCode_ET.setText(settings.getString(UmbrellaPreferences.zipCode, "").toString());
        }
        //Load Units
        if(settings.getString(UmbrellaPreferences.units, "").toString() != null){



            if(settings.getString(UmbrellaPreferences.units, "").toString().equals("Celcius")){
                dropdown.setSelection(0);
            }else{
                dropdown.setSelection(1);
            }

        }else {
            Toast.makeText(this, "Units are null", Toast.LENGTH_SHORT).show();
            dropdown.setSelection(0);
            updateUnits();
        }

    }


    public void updateZipCode(View view) {
        Toast.makeText(this, "Zip Code Updated", Toast.LENGTH_SHORT).show();
        SharedPreferences settings = getSharedPreferences(UmbrellaPreferences.umbrellaPrefsFile, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(UmbrellaPreferences.zipCode,zipCode_ET.getText().toString());
        editor.commit();
    }
    public void updateUnits() {
        //Toast.makeText(this, "Temperature Units Updated ", Toast.LENGTH_SHORT).show();
        SharedPreferences settings = getSharedPreferences(UmbrellaPreferences.umbrellaPrefsFile, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(UmbrellaPreferences.units,dropdown.getSelectedItem().toString());
        editor.commit();

    }



}

