package com.foo.umbrella.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.foo.umbrella.R;
import java.util.ArrayList;
import com.foo.umbrella.Weather.Weather;
import com.foo.umbrella.data.DayWeatherAdapter;
import com.foo.umbrella.ui.UmbrellaSettings.UmbrellaSettings;


public class MainActivity extends AppCompatActivity implements MainActivityInteractor {

    private MainActivityPresenter presenter;

    final static private String TAG= MainActivity.class.getSimpleName()+"_TAG";


    private TextView temp_TV;
    private TextView location_TV;
    private TextView condition_TV;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp_TV= (TextView) findViewById(R.id.tv_temp);
        condition_TV= (TextView) findViewById(R.id.tv_condition);
        location_TV= (TextView) findViewById(R.id.tv_location);

        presenter = new MainActivityPresenterImpl(this);


     }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getWeather();


    }



    public void displayCurrentWeather (String temp, String condition, String currentZipcode){



        temp_TV.setText(temp);
        condition_TV.setText(condition);
        location_TV.setText(currentZipcode);

    }

    public void setColors(int temp,int tempLimit){

        LinearLayout linearLayout;
        linearLayout= (LinearLayout) findViewById(R.id.weather_background);
        if(temp> tempLimit){
            linearLayout.setBackgroundColor(getResources().getColor(R.color.weather_warm));

        }else{
            linearLayout.setBackgroundColor(getResources().getColor(R.color.weather_cool));

        }

    }

    public void setRecyclerView(Weather weather, ArrayList myDays){

        mRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DayWeatherAdapter(this,myDays,weather);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void startSettings() {

        Intent intent = new Intent(this, UmbrellaSettings.class);
        startActivity(intent);
    }

    public void btnSettingsOnClick(View view) {
        startSettings();
    }
}
