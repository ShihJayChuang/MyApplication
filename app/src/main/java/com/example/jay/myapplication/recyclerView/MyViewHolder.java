package com.example.jay.myapplication.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jay.myapplication.R;
import com.example.jay.myapplication.models.DatabaseGetSet;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewName;
    public ImageView imageView;

    public MyViewHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bindToShow(Context context, DatabaseGetSet databaseGetSet) {
        textViewName.setText(databaseGetSet.typeName);
        Glide.with(context).load(databaseGetSet.thumbPathUrl).into(imageView);
    }

//    public void setTextViewName(String textViewName) {
//        this.textViewName.setText(textViewName);
//    }
//
//    public void setImageView(String url, Context context) {
//        Glide.with(context).load(url).into(this.imageView);
//    }


}
