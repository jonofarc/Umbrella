package com.foo.umbrella.ui.main_activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.foo.umbrella.PreferencesManager;
import com.foo.umbrella.weather.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {

    MainActivityInteractor interactor;

    Context mContext;
    final static private String TAG= MainActivity.class.getSimpleName()+"_TAG";
    private StringBuilder BASE_URL;

    public Weather weather;
    String currentZipCode;
    OkHttpClient client;




    ArrayList myDays = new ArrayList<>();


    public MainActivityPresenterImpl(MainActivity mainActivity) {
        mContext = mainActivity;
        interactor= mainActivity;
        client = new OkHttpClient.Builder().build();
    }

    public void getWeather() {

        checkZip();
    }

    public void checkZip(){
        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        //check if ZipCode exist
        if(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString().isEmpty()){

            interactor.startSettings();
        }else {
            currentZipCode=settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString();
            UrlBuilder();

            requestWeather();
        }

    }
    public void UrlBuilder(){
        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        if(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString() != null){

            BASE_URL=new StringBuilder();
            BASE_URL.append("http://api.wunderground.com/api/e1bef1584936ce74/features/hourly10day/q/");
            BASE_URL.append(settings.getString(PreferencesManager.UmbrellaPreferences.zipCode, "").toString());
            BASE_URL.append("/format.json");
        }else {
            Toast.makeText(mContext, "Zip Code is null", Toast.LENGTH_SHORT).show();
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


                            new Handler(Looper.getMainLooper()).post(new Runnable() {
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

        if(weather.getHourlyForecast() != null){
            for(int i=0; i<weather.getHourlyForecast().size(); i++){


                if(currentDay <  Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYday()) || currentYear<Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYear())){

                    currentDay=Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYday());
                    currentYear=Integer.parseInt(weather.getHourlyForecast().get(i).getFCTTIME().getYear());
                    myDays.add(currentDay);


                }



            }


            displayCurrentWeather();
            interactor.setRecyclerView(weather, myDays);
        }
        else {
            interactor.startSettings();
            Toast.makeText(mContext, "Invalid Zip Code", Toast.LENGTH_SHORT).show();
        }


    }
    public void displayCurrentWeather (){
        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);
        StringBuilder temp = new StringBuilder();
        int currentTemp;
        int fTempLimit=60;
        int cTempLimit=15;

        if(settings.getString(PreferencesManager.UmbrellaPreferences.units, "").toString().equals("Celcius")){
            temp.append(weather.getHourlyForecast().get(0).getTemp().getMetric().toString());
            currentTemp=Integer.parseInt( weather.getHourlyForecast().get(0).getTemp().getMetric());
            interactor.setColors(currentTemp, cTempLimit);
        }else{
            temp.append(weather.getHourlyForecast().get(0).getTemp().getEnglish().toString());
            currentTemp=Integer.parseInt( weather.getHourlyForecast().get(0).getTemp().getEnglish());
            interactor.setColors(currentTemp, fTempLimit);
        }
        temp.append("º");

        StringBuilder condition = new StringBuilder();
        condition.append(weather.getHourlyForecast().get(0).getCondition().toString());

        StringBuilder currentZipcode = new StringBuilder();
        currentZipcode.append("Zip Code: ");
        currentZipcode.append(currentZipCode);

        interactor.displayCurrentWeather(temp.toString(),condition.toString(),currentZipcode.toString());

    }







}
