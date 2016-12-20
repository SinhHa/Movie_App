package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.GridItemCallBack;
import com.example.dangtuanvn.movie_app.R;


import java.util.ArrayList;

/**
 * Created by sinhhx on 11/18/16.
 */
public class MovieScheduleAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<String> timeList;
    private ArrayList<String> dateList;
    GridItemCallBack callback;

    public MovieScheduleAdapter(Context context, ArrayList<String> dateList, ArrayList<String> timeList,GridItemCallBack callback) {
        this.dateList = dateList;
        this.context = context;
        this.timeList = timeList;
        this.callback= callback;
    }

    private static class ViewHolder {
        TextView date;
        TextView time;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.movie_date, parent, false);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.date.setText("Today");

        } else {
            holder.date.setText(timeList.get(position));
        }


        holder.time.setText(dateList.get(position));

        if(position==0){
            callback.onFirstItemCreate(convertView);
        }
        return convertView;

    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
