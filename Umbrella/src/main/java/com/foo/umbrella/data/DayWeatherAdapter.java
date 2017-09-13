package com.foo.umbrella.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {



        ArrayList hoursArray = new ArrayList<>();
        ArrayList conditionArray = new ArrayList<>();
        ArrayList tempArray = new ArrayList<>();
        StringBuilder currentDate = new StringBuilder();
        StringBuilder formattedTemp = new StringBuilder();

        SharedPreferences settings = mContext.getSharedPreferences(PreferencesManager.UmbrellaPreferences.umbrellaPrefsFile, 0);

        int hours=0;

        for(int i=0; i<weather.getHourlyForecast().size(); i++){
            String s1=mDataset.get(position).toString();
            String s2=weather.getHourlyForecast().get(i).getFCTTIME().getYday().toString();
            if(s1.equals(s2)){

                hours++;

                hoursArray.add(weather.getHourlyForecast().get(i).getFCTTIME().getCivil().toString());

                formattedTemp.setLength(0);

                if(settings.getString(PreferencesManager.UmbrellaPreferences.units, "").toString().equals("Celcius")){
                    formattedTemp.append(weather.getHourlyForecast().get(i).getTemp().getMetric().toString());
                }else{
                    formattedTemp.append(weather.getHourlyForecast().get(i).getTemp().getEnglish().toString());
                }

                formattedTemp.append("º");
                tempArray.add(formattedTemp.toString());
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


        ViewGroup.LayoutParams layoutParams2 = holder.myCardView.getLayoutParams();

        int rows=0;// at least 1 row should be visible
        rows+=(hours+3)/4;// add 1 row for each 4 elements plus 3 as we always want to show at least 1 row
        int rowHeight=90;
        int dateHeight=60;

        int cardDynamicSizeDP= convertDpToPixels((rows*rowHeight)+dateHeight,mContext);
        layoutParams2.height = cardDynamicSizeDP; //this is in pixels
        holder.myCardView.setLayoutParams(layoutParams2);






    }


    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {


        GridView gridView;
        TextView currentDate_tv;
        CardView myCardView;


        public ViewHolder(View v) {
            super(v);

            currentDate_tv= (TextView) v.findViewById(R.id.tv_date);
            gridView = (GridView) v.findViewById(R.id.gridview);
            myCardView= (CardView) v.findViewById(R.id.cardview);



        }

    }


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
