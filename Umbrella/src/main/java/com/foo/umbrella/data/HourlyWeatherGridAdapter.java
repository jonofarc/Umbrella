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

    private Context context;

    private ArrayList mDataset;

    public HourlyWeatherGridAdapter(Context context, ArrayList daysData) {
        this.context = context;
        mDataset=daysData;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.hourly_layout, null);

            // set value into textview
            TextView hour_tv = (TextView) gridView
                    .findViewById(R.id.tv_hour);
            hour_tv.setText(mDataset.get(position).toString());

            // set value into textview
            TextView temp_TV = (TextView) gridView
                    .findViewById(R.id.tv_hour);
            temp_TV.setText(mDataset.get(position).toString());

            // set image based on selected text
            ImageView condition_IV = (ImageView) gridView
                    .findViewById(R.id.iv_condition);

            /*
            String mobile = mobileValues[position];

            if (mobile.equals("Windows")) {
                imageView.setImageResource(R.drawable.windows_logo);
            } else if (mobile.equals("iOS")) {
                imageView.setImageResource(R.drawable.ios_logo);
            } else if (mobile.equals("Blackberry")) {
                imageView.setImageResource(R.drawable.blackberry_logo);
            } else {
                imageView.setImageResource(R.drawable.android_logo);
            }
            */
            condition_IV.setImageResource(R.drawable.weather_sunny);

        } else {
            gridView = convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mDataset.size();
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
