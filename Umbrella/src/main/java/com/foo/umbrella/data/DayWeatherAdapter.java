package com.foo.umbrella.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.foo.umbrella.R;
import com.foo.umbrella.Weather.Weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Jonathan Maldonado on 9/9/2017.
 */

public class DayWeatherAdapter extends RecyclerView.Adapter <DayWeatherAdapter.ViewHolder> {

    private ArrayList mDataset;
    private Context context;
    public Weather weather;


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

       // holder.TV_author.setText(mDataset[position].getAuthor());




        context=this.context;





        for(int i=0; i<weather.getHourlyForecast().size(); i++){
            String s1=mDataset.get(position).toString();
            String s2=weather.getHourlyForecast().get(i).getFCTTIME().getYday().toString();
            if(s1.equals(s2)){
                holder.hour_TV.setText(weather.getHourlyForecast().get(i).getFCTTIME().getCivil().toString());

                holder.temp_TV.setText(weather.getHourlyForecast().get(i).getTemp().getMetric().toString());
            }
        }
       // HourlyWeatherGridAdapter hourlyWeatherGridAdapter = new HourlyWeatherGridAdapter(context, mDataset);
        //holder.gridView.setAdapter(hourlyWeatherGridAdapter);






    }




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView hour_TV;
        public TextView temp_TV;
        GridView gridView;

        public ViewHolder(View v) {
            super(v);
            //gridView = (GridView) v.findViewById(R.id.gridview);
            hour_TV= (TextView) v.findViewById(R.id.tv_hour);
            temp_TV= (TextView) v.findViewById(R.id.tv_temp);



        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DayWeatherAdapter(ArrayList daysData, Weather recivingWeather) {


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
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
