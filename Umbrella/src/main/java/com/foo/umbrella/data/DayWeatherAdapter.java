package com.foo.umbrella.data;

import android.content.Context;
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


import com.foo.umbrella.R;
import com.foo.umbrella.Weather.Weather;
import com.foo.umbrella.ui.MainActivity;

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

        for(int i=0; i<weather.getHourlyForecast().size(); i++){
            String s1=mDataset.get(position).toString();
            String s2=weather.getHourlyForecast().get(i).getFCTTIME().getYday().toString();
            if(s1.equals(s2)){
                hoursArray.add(weather.getHourlyForecast().get(i).getFCTTIME().getCivil().toString());
                tempArray.add(weather.getHourlyForecast().get(i).getTemp().getMetric().toString());
                conditionArray.add(weather.getHourlyForecast().get(i).getCondition().toString());

            }
        }




        HourlyWeatherGridAdapter hourlyWeatherGridAdapter = new HourlyWeatherGridAdapter(mContext, hoursArray,tempArray,conditionArray);
        holder.gridView.setAdapter(hourlyWeatherGridAdapter);







    }




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        GridView gridView;
        CardView cardView;

        public ViewHolder(View v) {
            super(v);


            gridView = (GridView) v.findViewById(R.id.gridview);
            cardView= (CardView) v.findViewById(R.id.cardview);



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
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
