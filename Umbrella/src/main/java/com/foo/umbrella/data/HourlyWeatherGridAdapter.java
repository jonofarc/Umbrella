package com.foo.umbrella.data;
import com.foo.umbrella.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonathan Maldonado on 9/10/2017.
 */

public class HourlyWeatherGridAdapter extends BaseAdapter {

    private Context mContext;

    ArrayList hoursArray = new ArrayList<>();
    ArrayList conditionArray = new ArrayList<>();
    ArrayList tempArray = new ArrayList<>();

    public HourlyWeatherGridAdapter(Context context, ArrayList recivingHoursArray, ArrayList recivingTempArray, ArrayList recivingConditionArray) {
        mContext = context;
        hoursArray=recivingHoursArray;
        tempArray=recivingTempArray;
        conditionArray=recivingConditionArray;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.hourly_layout, null);

            // set value into textview
            TextView hour_tv = (TextView) gridView
                    .findViewById(R.id.tv_hour);
            hour_tv.setText(hoursArray.get(position).toString());

            // set value into textview
            TextView temp_TV = (TextView) gridView
                    .findViewById(R.id.tv_temp);
            temp_TV.setText(tempArray.get(position).toString());

            // set image based on selected text
            ImageView condition_IV = (ImageView) gridView
                    .findViewById(R.id.iv_condition);

            switch (conditionArray.get(position).toString()){
                case "Rain":
                    condition_IV.setImageResource(R.drawable.weather_rainy);
                    break;
                case "Clear":
                    condition_IV.setImageResource(R.drawable.weather_sunny);
                    break;
                case "Partly Cloudy":
                    condition_IV.setImageResource(R.drawable.weather_partlycloudy);
                    break;
                case "Mostly Cloudy":
                    condition_IV.setImageResource(R.drawable.weather_cloudy);
                    break;
                default:
                    condition_IV.setImageResource(R.drawable.weather_sunny);
                    break;
            }


        } else {
            gridView = convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return hoursArray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
