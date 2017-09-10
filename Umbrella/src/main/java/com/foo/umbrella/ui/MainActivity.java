package com.foo.umbrella.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.foo.umbrella.PreferencesManager;
import com.foo.umbrella.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.foo.umbrella.PreferencesManager.UmbrellaPreferences;
import com.foo.umbrella.Weather.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static private String TAG= MainActivity.class.getSimpleName()+"_TAG";
    private StringBuilder BASE_URL;
    OkHttpClient client;
    public Weather weather;
    public TextView tv_results;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient.Builder().build();
        tv_results= (TextView) findViewById(R.id.tv_result);


        UrlBuilder();

        requestWeather();

     }



    public void UrlBuilder(){
         SharedPreferences settings = getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
         if(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString() != null){

             BASE_URL=new StringBuilder();
             BASE_URL.append("http://api.wunderground.com/api/e1bef1584936ce74/features/hourly/q/");
             BASE_URL.append(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString());
             BASE_URL.append("/format.json");
         }else {
             Toast.makeText(this, "Zip Code is null", Toast.LENGTH_SHORT).show();
         }

     }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_settings:
                Toast.makeText(this, "Going to settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, UmbrellaSettings.class);
                startActivity(intent);
                break;

            
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

                                    //setRecyclerView();
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
        StringBuilder results = new StringBuilder();

        results.append("Current weather: \n");
        results.append(weather.getHourlyForecast().get(0).getTemp().getMetric().toString());
        results.append(" C \n");


        int currentDay=0;
        int currentYear=0;
        for(int i=0; i<weather.getHourlyForecast().size(); i++){


            if(currentDay <  Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYday()) || currentYear<Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYear())){

                currentDay=Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYday());
                currentYear=Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYear());

                results.append("\n\n");
                results.append(weather.getHourlyForecast().get(i).getFCTTIME().getMonthName()+" "+weather.getHourlyForecast().get(i).getFCTTIME().getMday() );
                results.append(" weather:");
                results.append("\n\n");
            }

            results.append(weather.getHourlyForecast().get(i).getFCTTIME().getCivil().toString()+" ");
            results.append(weather.getHourlyForecast().get(i).getTemp().getMetric().toString());
            results.append(" C \n");


        }
        tv_results.setText(results.toString());


    }



}
