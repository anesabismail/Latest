package com.apps.anesabml.latest;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by anesabismail on 08/08/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Activity context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /// Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        // Find the news at the given position on th list of newses
        News currentNews = (News) getItem(position);

        // Find the ImageView with ID image
        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        // Load the image and display it with Picasso Library
        Picasso.with(getContext()).load(currentNews.getImageLink()).into(image);

        // Find the TextView with ID title
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        // Display the title of the current news in that TextView
        title.setText(currentNews.getTitle());

        // Find the TextView with ID section
        TextView section = (TextView) listItemView.findViewById(R.id.section);
        // Display the section of the current news in that TextView
        section.setText(currentNews.getSection());

        // Find the TextView with ID date
        TextView date = (TextView) listItemView.findViewById(R.id.time);
        // Display the date of the current news in that TextView
        date.setText(currentNews.getDate());

        // Return the list list_item view that is now showing the appropriate data
        return listItemView;
    }
}

