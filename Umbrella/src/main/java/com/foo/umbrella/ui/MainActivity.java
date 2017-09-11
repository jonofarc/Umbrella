package com.foo.umbrella.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.foo.umbrella.PreferencesManager;
import com.foo.umbrella.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.foo.umbrella.PreferencesManager.UmbrellaPreferences;
import com.foo.umbrella.Weather.Weather;
import com.foo.umbrella.data.DayWeatherAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;


public class MainActivity extends AppCompatActivity {

    final static private String TAG= MainActivity.class.getSimpleName()+"_TAG";
    private StringBuilder BASE_URL;
    OkHttpClient client;
    public Weather weather;
    private TextView temp_TV;
    private TextView location_TV;
    private TextView condition_TV;
    String currentZipCode;




    ArrayList myDays = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient.Builder().build();
        temp_TV= (TextView) findViewById(R.id.tv_temp);
        condition_TV= (TextView) findViewById(R.id.tv_condition);
        location_TV= (TextView) findViewById(R.id.tv_location);



     }

    @Override
    protected void onStart() {
        super.onStart();

        checkZip();

    }

    public void checkZip(){
        SharedPreferences settings = getSharedPreferences(UmbrellaPreferences.umbrellaPrefsFile, 0);
        //check if ZipCode exist
        if(settings.getString(UmbrellaPreferences.zipCode, "").toString().isEmpty()){

            startSettings();
        }else {
            currentZipCode=settings.getString(UmbrellaPreferences.zipCode, "").toString();
            UrlBuilder();

            requestWeather();
        }

    }

    public void UrlBuilder(){
         SharedPreferences settings = getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
         if(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString() != null){

             BASE_URL=new StringBuilder();
             BASE_URL.append("http://api.wunderground.com/api/e1bef1584936ce74/features/hourly10day/q/");
             BASE_URL.append(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString());
             BASE_URL.append("/format.json");
         }else {
             Toast.makeText(this, "Zip Code is null", Toast.LENGTH_SHORT).show();
         }

     }








    public void requestWeather() {


        Request request = new Request.Builder().url(BASE_URL.toString()).build();


        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }



                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if(response.isSuccessful()){



                            String resp= response.body().string();
                            try {


                                Gson gson = new Gson();
                                weather =  gson.fromJson(resp, Weather.class);
                                Log.d(TAG, "onResponse: ");




                            }catch (JsonParseException e){
                                e.printStackTrace();
                            }



                            Log.d(TAG, "onResponse resp:  "+ resp);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    showData();
                                }
                            });

                        }else{
                            Log.d(TAG, "onResponse: Application Error");
                        }





                    }

                }
        );



    }

    public void showData(){


        int currentDay=0;
        int currentYear=0;



        for(int i=0; i<weather.getHourlyForecast().size(); i++){


            if(currentDay <  Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYday()) || currentYear<Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYear())){

                currentDay=Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYday());
                currentYear=Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYear());
                myDays.add(currentDay);


            }



        }


        SharedPreferences settings = getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        StringBuilder myString = new StringBuilder();
        if(settings.getString(PreferencesManager.UmbrellaPreferences.units, "").toString().equals("Celcius")){
            myString.append(weather.getHourlyForecast().get(0).getTemp().getMetric().toString());
        }else{
            myString.append(weather.getHourlyForecast().get(0).getTemp().getEnglish().toString());
        }
        myString.append("º");

        temp_TV.setText(myString.toString());
        condition_TV.setText(weather.getHourlyForecast().get(0).getCondition().toString());

        myString.setLength(0);
        myString.append("Zip Code: ");
        myString.append(currentZipCode);
        location_TV.setText(myString);



        setRecyclerView();
    }


    public void setRecyclerView(){

        mRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DayWeatherAdapter(this,myDays,weather);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void startSettings() {

        Intent intent = new Intent(MainActivity.this, UmbrellaSettings.class);
        startActivity(intent);
    }

    public void btnSettingsOnClick(View view) {
        startSettings();
    }
}
