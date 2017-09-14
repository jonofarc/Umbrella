package com.foo.umbrella.data;

import com.foo.umbrella.R;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jonathan Maldonado on  9/13/2017.
 */

public class HourlyWeatherGridAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList hoursArray = new ArrayList<>();
    ArrayList conditionArray = new ArrayList<>();
    ArrayList tempArray = new ArrayList<>();
    int minPosition = 0;
    int maxPosition = 0;

    public HourlyWeatherGridAdapter(Context context, ArrayList recivingHoursArray, ArrayList recivingTempArray, ArrayList recivingConditionArray, int recivingMinposition, int recivingMaxPosition) {
        mContext = context;
        hoursArray = recivingHoursArray;
        tempArray = recivingTempArray;
        conditionArray = recivingConditionArray;
        //position where the minimun and maximun temps are located
        minPosition = recivingMinposition;
        maxPosition = recivingMaxPosition;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.hourly_layout, null);
            TextView hour_tv = (TextView) gridView.findViewById(R.id.tv_hour);
            hour_tv.setText(hoursArray.get(position).toString());
            TextView temp_TV = (TextView) gridView.findViewById(R.id.tv_temp);
            temp_TV.setText(tempArray.get(position).toString());
            ImageView condition_IV = (ImageView) gridView.findViewById(R.id.iv_condition);
            switch (conditionArray.get(position).toString()) {
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
            Drawable drawable = condition_IV.getDrawable();
            drawable = DrawableCompat.wrap(drawable);
            if (position == minPosition && minPosition != maxPosition) {
                DrawableCompat.setTintList(drawable, mContext.getResources().getColorStateList(R.color.weather_cool));
                hour_tv.setTextColor(mContext.getResources().getColorStateList(R.color.weather_cool));
                temp_TV.setTextColor(mContext.getResources().getColorStateList(R.color.weather_cool));
            } else if (position == maxPosition && minPosition != maxPosition) {
                DrawableCompat.setTintList(drawable, mContext.getResources().getColorStateList(R.color.weather_warm));
                hour_tv.setTextColor(mContext.getResources().getColorStateList(R.color.weather_warm));
                temp_TV.setTextColor(mContext.getResources().getColorStateList(R.color.weather_warm));
            } else {
                DrawableCompat.setTintList(drawable, mContext.getResources().getColorStateList(R.color.text_color_primary));
                hour_tv.setTextColor(mContext.getResources().getColorStateList(R.color.text_color_primary));
                temp_TV.setTextColor(mContext.getResources().getColorStateList(R.color.text_color_primary));
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
