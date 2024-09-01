package com.example.attendease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class customSpinnerAdapter extends ArrayAdapter<String>
{

    private boolean isFirstTime = true;

    public customSpinnerAdapter(Context context, List<String> items)
    {
        super(context, R.layout.custom_spinner_item, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_spinner_item, parent, false);
        }
        RelativeLayout relativeLayout = convertView.findViewById(R.id.spinner_layout);
        TextView textView = convertView.findViewById(R.id.spinner_text);
        ImageView imageView = convertView.findViewById(R.id.spinner_icon);
        textView.setText(getItem(position));
        if (isFirstTime)
        {
            relativeLayout.setBackgroundResource(R.drawable.spinner_outline);
            imageView.setVisibility(View.VISIBLE);
            isFirstTime = false;
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_spinner_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.spinner_text);
        ImageView imageView = convertView.findViewById(R.id.spinner_icon);
        textView.setText(getItem(position));
        imageView.setVisibility(View.GONE);
        return convertView;
    }
}