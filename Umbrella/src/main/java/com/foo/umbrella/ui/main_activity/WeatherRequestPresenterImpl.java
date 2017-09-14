package com.foo.umbrella.ui.main_activity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.foo.umbrella.weather.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jonathan Maldonado on 9/13/2017.
 */

public class WeatherRequestPresenterImpl implements WeatherRequestPresenter {

    private MainActivityPresenter presenter;
    Context mContext;
    OkHttpClient client;
    public Weather weather;
    public WeatherRequestPresenterImpl(MainActivityPresenterImpl mainActivityPresenter) {

        presenter= mainActivityPresenter;
        client = new OkHttpClient.Builder().build();
    }

    public void requestWeather(String BASE_URL) {
        Request request = new Request.Builder().url(BASE_URL).build();
        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.isSuccessful()) {
                            String resp = response.body().string();
                            try {
                                Gson gson = new Gson();
                                weather = gson.fromJson(resp, Weather.class);
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            }

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                   presenter.showData(weather);
                                }
                            });


                        } else {
                            //Log.d(TAG, "onResponse: Application Error");
                        }


                    }

                }
        );


    }


}
