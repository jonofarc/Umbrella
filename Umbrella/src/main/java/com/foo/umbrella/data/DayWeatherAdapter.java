package com.foo.umbrella.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.foo.umbrella.PreferencesManager;
import com.foo.umbrella.R;
import com.foo.umbrella.Weather.Weather;
import com.foo.umbrella.ui.MainActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Jonathan Maldonado on 9/9/2017.
 */

public class DayWeatherAdapter extends RecyclerView.Adapter <DayWeatherAdapter.ViewHolder> {

    private ArrayList mDataset;
    private Context mContext;
    public Weather weather;




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

       // holder.TV_author.setText(mDataset[position].getAuthor());









        ArrayList hoursArray = new ArrayList<>();
        ArrayList conditionArray = new ArrayList<>();
        ArrayList tempArray = new ArrayList<>();
        StringBuilder currentDate = new StringBuilder();
        StringBuilder formatedTemp = new StringBuilder();

        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);


        for(int i=0; i<weather.getHourlyForecast().size(); i++){
            String s1=mDataset.get(position).toString();
            String s2=weather.getHourlyForecast().get(i).getFCTTIME().getYday().toString();
            if(s1.equals(s2)){
                hoursArray.add(weather.getHourlyForecast().get(i).getFCTTIME().getCivil().toString());

                formatedTemp.setLength(0);

                if(settings.getString(PreferencesManager.UmbrellaPreferences.units, "").toString().equals("Celcius")){
                    formatedTemp.append(weather.getHourlyForecast().get(i).getTemp().getMetric().toString());
                }else{
                    formatedTemp.append(weather.getHourlyForecast().get(i).getTemp().getEnglish().toString());
                }

                formatedTemp.append("º");
                //tempArray.add(weather.getHourlyForecast().get(i).getTemp().getMetric().toString()+"º");
                tempArray.add(formatedTemp.toString());
                conditionArray.add(weather.getHourlyForecast().get(i).getCondition().toString());

                currentDate.setLength(0);
                currentDate.append(weather.getHourlyForecast().get(i).getFCTTIME().getWeekdayName().toString());
                currentDate.append(" "+weather.getHourlyForecast().get(i).getFCTTIME().getMonthName().toString());
                currentDate.append(" "+ weather.getHourlyForecast().get(i).getFCTTIME().getMday().toString());
            }
        }

        String min = (String) Collections.min(tempArray);
        String max = (String) Collections.max(tempArray);
        int minPosition=0;
        int maxPosition=0;
        for(int i=tempArray.size()-1; i>=0; i--){
            if(tempArray.get(i).equals(min)){
                minPosition=i;
            }
            if(tempArray.get(i).equals(max)){
                maxPosition=i;
            }
        }



        //show current date on card
        holder.currentDate_tv.setText(currentDate.toString());

        HourlyWeatherGridAdapter hourlyWeatherGridAdapter = new HourlyWeatherGridAdapter(mContext, hoursArray,tempArray,conditionArray,minPosition,maxPosition);
        holder.gridView.setAdapter(hourlyWeatherGridAdapter);







    }




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {


        GridView gridView;
        TextView currentDate_tv;


        public ViewHolder(View v) {
            super(v);

            currentDate_tv= (TextView) v.findViewById(R.id.tv_date);
            gridView = (GridView) v.findViewById(R.id.gridview);




        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DayWeatherAdapter(Context context,ArrayList daysData, Weather recivingWeather) {

        mContext =context;
        weather=recivingWeather;
        mDataset = daysData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {


        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_weather, parent, false);



        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
