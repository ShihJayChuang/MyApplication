package com.example.jay.myapplication.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jay.myapplication.R;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewName;
    public ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
