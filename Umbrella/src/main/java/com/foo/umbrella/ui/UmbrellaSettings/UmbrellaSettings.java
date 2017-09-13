package com.foo.umbrella.ui.UmbrellaSettings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.foo.umbrella.R;


public class UmbrellaSettings extends AppCompatActivity implements UmbrellaSettingsInteractor {

    private UmbrellaSettingsPresenter presenter;
    EditText zipCode_ET;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella_settings);

        presenter = new UmbrellaSettingsPresenterImpl(this);

        zipCode_ET= (EditText) findViewById(R.id.et_zipCode);


        dropdown = (Spinner)findViewById(R.id.spinner1);

        String[] items = new String[]{"Celcius", "Fahrenheit"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);


    }



    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadSettings();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                presenter.updateUnits(dropdown.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });

    }




    public void updateZipCode(View view) {
        presenter.updateZipCode(zipCode_ET.getText().toString());
    }


    public void setSettings(String zipCode, int unitSelection){
        zipCode_ET.setText(zipCode);
        dropdown.setSelection(unitSelection);
    }



}

